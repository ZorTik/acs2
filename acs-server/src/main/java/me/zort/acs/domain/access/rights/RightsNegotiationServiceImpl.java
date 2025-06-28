package me.zort.acs.domain.access.rights;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.SubjectRepository;
import me.zort.acs.api.domain.access.rights.RightsNegotiationService;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.grant.RightsHolderTypeRegistry;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.api.domain.service.DefinitionsService;
import me.zort.acs.api.domain.grant.GrantService;
import me.zort.acs.api.domain.group.GroupService;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.model.*;
import me.zort.acs.domain.util.PageUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class RightsNegotiationServiceImpl implements RightsNegotiationService {
    private final GrantService grantService;
    private final DefinitionsService definitionsService;
    private final GroupService groupService;
    private final SubjectRepository subjectRepository;
    private final DomainModelMapper<Subject, SubjectEntity> subjectMapper;
    private final DomainModelMapper<Grant, GrantEntity> grantMapper;
    private final RightsHolderTypeRegistry rightsHolderTypeRegistry;

    /**
     * @see RightsNegotiationService#getRightsHolders(SubjectLike, SubjectLike)
     */
    @Override
    public List<RightsHolder> getRightsHolders(SubjectLike accessorLike, SubjectLike accessedLike) {
        SubjectType accessorType = accessorLike.getSubjectType();
        SubjectType accessedType = accessedLike.getSubjectType();

        List<RightsHolder> holders = new ArrayList<>();

        // Default nodes and groups granted by definition source
        holders.add(NodesBulk.of(definitionsService
                .getDefaultGrantedNodes(accessorType, accessedType)));
        holders.addAll(definitionsService
                .getDefaultGrantedGroups(accessorType, accessedType));

        // Holders applicable only between existing subjects
        if (accessorLike instanceof Subject accessor && accessedLike instanceof Subject accessed) {
            // Nodes granted by external requests
            List<RightsHolder> holdersOfGrants = grantService.getGrants(accessor, accessed)
                    .stream()
                    .map(Grant::getRightsHolder).toList();

            // Groups the subject is member of
            List<Group> groups = groupService.getGroupMemberships(accessor, accessed);

            holders.addAll(holdersOfGrants);
            holders.addAll(groups);
        }

        return holders;
    }

    /**
     * Checks if the accessor has default access to the accessed type.
     * This state is hereby checked against the default granted nodes and groups defined in the
     * DefinitionsService.
     *
     * @param accessor the subject attempting to access resources
     * @param accessedType the type of the subject being accessed
     * @param anyOf the list of rights holders to check against
     * @return true if the accessor has default access, false otherwise
     */
    private boolean hasDefaultAccess(SubjectLike accessor, SubjectType accessedType, List<RightsHolder> anyOf) {
        Stream<RightsHolder> streamOfDefaultRightsHolders = Stream.concat(
                definitionsService.getDefaultGrantedGroups(accessor.getSubjectType(), accessedType).stream(),
                definitionsService.getDefaultGrantedNodes(accessor.getSubjectType(), accessedType).stream());

        // Is any of the default rights holders present in the anyOf list?
        return streamOfDefaultRightsHolders
                .anyMatch(rightsHolder -> anyOf
                        .stream()
                        .anyMatch(anyHolder -> anyHolder.equals(rightsHolder)));
    }

    /**
     * Groups the rights holders by their type.
     *
     * @param anyOf the list of rights holders to group
     * @return a map where the key is the class of the rights holder
     */
    private @NotNull Map<Class<?>, List<RightsHolder>> groupHoldersByType(List<RightsHolder> anyOf) {
        Map<Class<?>, List<RightsHolder>> rightsHoldersByType = new HashMap<>();
        anyOf.forEach(rightsHolder -> {
            Class<?> typeOfRightsHolder = rightsHolderTypeRegistry.castAndCallAdapter(
                    rightsHolder, (holder, type) -> type.getHolderType());

            rightsHoldersByType.computeIfAbsent(typeOfRightsHolder, k -> new ArrayList<>()).add(rightsHolder);
        });
        return rightsHoldersByType;
    }

    /**
     * Retrieves candidate subjects for the accessed type based on the grants
     * from the rights holders.
     *
     * @param accessor the subject attempting to access resources
     * @param accessedType the type of the subject being accessed
     * @param anyOf the list of rights holders to check against
     * @param pageable the pagination information
     * @return a page of candidate subjects that the accessor can access
     */
    private @NotNull Page<Subject> getCandidateSubjectsFromGrants(
            Subject accessor, SubjectType accessedType, List<RightsHolder> anyOf, Pageable pageable) {
        Map<Class<?>, List<RightsHolder>> rightsHoldersByType = groupHoldersByType(anyOf);

        SubjectId accessorSubjectId = subjectMapper.toPersistence(accessor).getId();
        List<Subject> candidateSubjectsByGrants = rightsHoldersByType.values()
                .stream()
                .flatMap(rightsHolders -> rightsHolderTypeRegistry.castAndCallAdapter(
                        rightsHolders.get(0),
                        (holder, type) ->
                                type.getGrantEntitiesForHolders(rightsHolders, accessorSubjectId, accessedType).stream()))
                .map(grantMapper::toDomain)
                .filter(Grant::isValid)
                .map(Grant::getAccessed).toList();

        return PageUtils.toPage(candidateSubjectsByGrants, pageable);
    }

    /**
     * @see RightsNegotiationService#getCandidateSubjects(SubjectLike, SubjectType, List, Pageable)
     */
    @Override
    public Page<Subject> getCandidateSubjects(
            SubjectLike accessor, SubjectType accessedType, List<RightsHolder> anyOf, Pageable pageable) {
        if (hasDefaultAccess(accessor, accessedType, anyOf)) {
            // If there is any default access, the subject has access to all subjects of the accessed type
            // since we check against the default granted nodes or groups.
            return subjectRepository.findAll(pageable).map(subjectMapper::toDomain);
        } else if (accessor instanceof Subject accessorRealSubject) {
            return getCandidateSubjectsFromGrants(accessorRealSubject, accessedType, anyOf, pageable);
        } else {
            // If the accessor is not a real subject, there are definitely no grants,
            // so we return an empty page.
            return Page.empty(pageable);
        }
    }
}
