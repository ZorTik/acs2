package me.zort.acs.domain.access.rights;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.grant.RightsHolderTypeRegistry;
import me.zort.acs.domain.access.rights.type.RightsHolderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class RightsHolderTypeRegistryImpl implements RightsHolderTypeRegistry {
    private final List<RightsHolderType<?>> holderTypes;

    @Override
    public <T extends RightsHolder, R> R castAndCallAdapter(
            RightsHolder holder, BiFunction<T, RightsHolderType<T>, R> callFunc) {
        for (RightsHolderType<?> type : holderTypes) {
            if (!type.getHolderType().isInstance(holder)) {
                continue;
            }

            @SuppressWarnings("unchecked")
            T castedHolder = (T) holder;

            @SuppressWarnings("unchecked")
            RightsHolderType<T> castedType = (RightsHolderType<T>) type;

            return callFunc.apply(castedHolder, castedType);
        }

        throw new IllegalArgumentException("Unsupported RightsHolder type: " + holder.getClass().getName());
    }

    @Override
    public Set<Class<?>> getSupportedHolderTypes() {
        return holderTypes.stream()
                .map(RightsHolderType::getHolderType).collect(Collectors.toSet());
    }
}
