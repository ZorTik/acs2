package me.zort.acs.api.http.facade;

import me.zort.acs.api.domain.subject.SubjectLike;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.http.dto.model.group.BriefGroupDto;
import me.zort.acs.http.dto.model.group.GroupDto;
import me.zort.acs.http.dto.model.subject.SubjectDto;

import java.util.List;

public interface HttpGroupsFacade {

    List<GroupDto> listGroups(String subjectTypeId);

    List<GroupDto> listGroups(SubjectLike subject);

    /**
     * Adds a list of groups to a subject.
     *
     * @param subjectDto the subject to which the groups will be added
     * @param groups the list of groups to add
     * @throws RuntimeException if an error occurs while adding the groups
     */
    void addGroups(SubjectDto subjectDto, List<BriefGroupDto> groups);

    /**
     * Removes a list of groups from a subject.
     *
     * @param subject the subject from which the groups will be removed
     * @param groupNames the list of group names to remove
     */
    void removeGroups(Subject subject, List<String> groupNames);
}
