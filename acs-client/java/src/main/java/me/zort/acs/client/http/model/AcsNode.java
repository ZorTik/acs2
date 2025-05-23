package me.zort.acs.client.http.model;

import com.google.common.base.Preconditions;
import lombok.Getter;
import me.zort.acs.client.AcsNodeResolvable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class AcsNode implements AcsNodeResolvable {
    private final String value;

    public AcsNode(String value) {
        this.value = Objects.requireNonNull(value, "value can't be null");
    }

    public static @NotNull AcsNode of(final @NotNull String value) {
        return new AcsNode(value);
    }

    public static @NotNull Set<AcsNodeResolvable> ofMany(final @NotNull String... values) {
        Preconditions.checkArgument(values.length > 0, "values can't be empty");

        return Set.of(values).stream().map(AcsNode::of).collect(Collectors.toSet());
    }
}