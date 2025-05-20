package me.zort.acs.client.http.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.client.AcsSubjectResolvable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AcsSubject implements AcsSubjectResolvable {
    private final String group;
    private final Object id;

    public static @NotNull AcsSubject of(String group, Object id) {
        Objects.requireNonNull(group, "subject group can't be null");
        Objects.requireNonNull(id, "subject id can't be null");

        return new AcsSubject(group, id);
    }

    public static @NotNull AcsSubject from(AcsSubjectResolvable resolvable) {
        return of(resolvable.getGroup(), resolvable.getId());
    }
}
