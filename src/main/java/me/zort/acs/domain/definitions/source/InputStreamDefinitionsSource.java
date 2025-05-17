package me.zort.acs.domain.definitions.source;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.definitions.DefinitionsFormat;
import me.zort.acs.domain.definitions.model.DefinitionsModel;
import me.zort.acs.domain.definitions.model.YamlDefinitionsModel;
import org.springframework.core.io.InputStreamSource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class InputStreamDefinitionsSource implements DefinitionsSource {
    private final InputStreamSource inputStreamSource;
    private final DefinitionsFormat format;

    @Override
    public DefinitionsModel getModel() throws IOException {
        return format.parseModel(inputStreamSource.getInputStream());
    }
}
