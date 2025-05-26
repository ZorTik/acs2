package me.zort.acs.api.domain.provider;

import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.provider.options.SubjectOptions;

public interface SubjectProvider {

    Subject getSubject(SubjectOptions options);
}
