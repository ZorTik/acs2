package me.zort.acs.client.http.model;

import com.google.common.base.Preconditions;
import lombok.Getter;
import me.zort.acs.client.AcsNodeResolvable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Node implements AcsNodeResolvable {
    private final String value;

    public Node(String value) {
        this.value = Objects.requireNonNull(value, "value can't be null");
    }

    public static @NotNull Node of(final @NotNull String value) {
        return new Node(value);
    }

    public static @NotNull Set<AcsNodeResolvable> many(final @NotNull String... values) {
        Preconditions.checkArgument(values.length > 0, "values can't be empty");

        return Arrays.stream(values)
                .map(Node::of).collect(Collectors.toSet());
    }
}