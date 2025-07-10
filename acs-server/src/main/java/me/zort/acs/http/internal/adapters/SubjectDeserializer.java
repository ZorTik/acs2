package me.zort.acs.http.internal.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.http.dto.model.subject.SubjectDto;
import me.zort.acs.http.mapper.HttpSubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Set;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class SubjectDeserializer implements AcsHttpDeserializer<Subject> {
    private final HttpSubjectMapper subjectMapper;
    private final Validator validator;

    @Override
    public Subject deserialize(
            JsonElement jsonElement,
            Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        SubjectDto dto = jsonDeserializationContext.deserialize(jsonElement, SubjectDto.class);

        Set<ConstraintViolation<SubjectDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return subjectMapper.toDomain(dto, false);
    }

    @Override
    public Class<Subject> getType() {
        return Subject.class;
    }
}
