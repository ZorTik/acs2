package me.zort.acs.spring;

import jakarta.servlet.ServletRequest;
import me.zort.acs.client.AcsNodeResolvable;
import me.zort.acs.client.AcsSubjectResolvable;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface AccessTargetResolver {

    AcsSubjectResolvable resolveAccessedSubject(final @NotNull ServletRequest request);

    Set<AcsNodeResolvable> resolveNodesToCheck(
            final @NotNull ServletRequest request,
            final @NotNull AcsSubjectResolvable accessor, final @NotNull AcsSubjectResolvable accessed);
}
