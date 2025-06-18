package me.zort.acs.api.domain.subjecttype;

import lombok.Builder;
import lombok.Getter;
import me.zort.acs.domain.model.Node;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

@Builder
@Getter
public class CreateSubjectTypeOptions {
    @Builder.Default
    private final Collection<Node> nodes = new ArrayList<>();

    public static @NotNull CreateSubjectTypeOptions defaultOptions() {
        return CreateSubjectTypeOptions.builder().build();
    }
}
