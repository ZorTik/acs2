package me.zort.acs.client.http.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.client.AcsNodeResolvable;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class AcsNode implements AcsNodeResolvable {
    private final String value;

    public static @NotNull AcsNode node(final @NotNull String value) {
        return new AcsNode(value);
    }

    public static @NotNull Set<AcsNodeResolvable> nodes(final @NotNull String... values) {
        return Set.of(values).stream().map(AcsNode::node).collect(Collectors.toSet());
    }
}