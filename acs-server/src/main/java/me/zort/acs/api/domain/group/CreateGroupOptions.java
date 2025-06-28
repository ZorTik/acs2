package me.zort.acs.api.domain.group;

import lombok.Builder;
import lombok.Getter;
import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.model.Node;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class CreateGroupOptions {
    @Builder.Default
    private final Group parentGroup = null;
    @Builder.Default
    private final List<Node> nodes = new ArrayList<>();

    public static @NotNull CreateGroupOptions defaultOptions() {
        return CreateGroupOptions.builder().build();
    }
}
