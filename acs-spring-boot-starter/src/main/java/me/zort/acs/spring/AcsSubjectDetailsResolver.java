package me.zort.acs.spring;

import me.zort.acs.client.AcsSubjectResolvable;
import org.jetbrains.annotations.Nullable;

public interface AcsSubjectDetailsResolver {

    @Nullable
    AcsSubjectResolvable resolveSubjectDetails(Object targetDomainObject);
}
