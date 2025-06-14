package me.zort.acs.core.domain.definitions.source;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import org.springframework.core.io.InputStreamSource;

import java.io.IOException;

@RequiredArgsConstructor
public class InputStreamDefinitionsSource implements DefinitionsSource {
    private final InputStreamSource inputStreamSource;
    private final DefinitionsFormat format;

    @Override
    public DefinitionsModel getModel() throws IOException {
        return format.parseModel(inputStreamSource.getInputStream());
    }
}
