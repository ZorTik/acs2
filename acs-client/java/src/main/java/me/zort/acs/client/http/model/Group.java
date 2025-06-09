package me.zort.acs.client.http.model;

import com.google.common.base.Preconditions;
import lombok.Getter;
import me.zort.acs.client.AcsGroupResolvable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Group implements AcsGroupResolvable {
    private final String name;

    public Group(@NotNull String name) {
        this.name = Objects.requireNonNull(name, "Group name cannot be null");
    }

    public static @NotNull Group of(@NotNull String name) {
        return new Group(name);
    }

    public static @NotNull Set<AcsGroupResolvable> many(String... names) {
        Preconditions.checkArgument(names.length > 0, "Names cannot be empty");

        return Arrays.stream(names)
                .map(Group::of).collect(Collectors.toSet());
    }
}
