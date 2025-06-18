package me.zort.acs.api.domain.subjecttype.exception;

import lombok.Getter;

@Getter
public class SubjectTypeAlreadyExistsException extends RuntimeException {
    private final String existingId;

    public SubjectTypeAlreadyExistsException(String existingId) {
        super(String.format("Subject type %s already exists.", existingId));
        this.existingId = existingId;
    }
}
