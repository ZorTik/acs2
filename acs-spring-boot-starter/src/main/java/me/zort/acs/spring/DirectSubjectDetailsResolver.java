package me.zort.acs.spring;

import me.zort.acs.client.AcsSubjectResolvable;
import org.jetbrains.annotations.Nullable;

/**
 * Resolver that directly returns the target domain object if it implements {@link AcsSubjectResolvable}.
 * <p>
 * This resolver only supports objects that are already instances of {@code AcsSubjectResolvable}.
 * If the target object does not implement this interface, {@code null} is returned.
 * </p>
 */
public class DirectSubjectDetailsResolver implements AcsSubjectDetailsResolver {

    /**
     * Resolver that directly returns the target domain object if it implements {@link AcsSubjectResolvable}.
     * <p>
     * This resolver only supports objects that are already instances of {@code AcsSubjectResolvable}.
     * If the target object does not implement this interface, {@code null} is returned.
     * </p>
     */
    @Override
    public @Nullable AcsSubjectResolvable resolveSubjectDetails(Object targetDomainObject) {
        if (targetDomainObject instanceof AcsSubjectResolvable resolvable) {
            return resolvable;
        }

        // This resolver supports only direct resolvable subtypes
        return null;
    }
}
