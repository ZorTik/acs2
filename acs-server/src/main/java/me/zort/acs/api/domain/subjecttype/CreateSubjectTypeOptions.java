package me.zort.acs.api.domain.subjecttype;

import lombok.Builder;
import lombok.Getter;
import me.zort.acs.core.model.Node;

import java.util.ArrayList;
import java.util.Collection;

@Builder
@Getter
public class CreateSubjectTypeOptions {
    @Builder.Default
    private final Collection<Node> nodes = new ArrayList<>();

}
