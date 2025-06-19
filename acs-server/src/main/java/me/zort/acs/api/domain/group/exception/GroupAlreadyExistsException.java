package me.zort.acs.api.domain.group.exception;

import lombok.Getter;
import me.zort.acs.domain.group.Group;
import org.jetbrains.annotations.NotNull;

@Getter
public class GroupAlreadyExistsException extends RuntimeException {
    @NotNull
    private final Group existing;

    public GroupAlreadyExistsException(Group existing) {
        super(String.format("Group %s already exists.", existing.getName()));

        this.existing = existing;
    }
}
