package me.zort.acs.domain.definitions.yaml;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.definitions.DefinitionsModel;
import me.zort.acs.domain.definitions.DefinitionsSource;
import org.springframework.core.io.InputStreamSource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class YamlDefinitionsSource implements DefinitionsSource {
    private final InputStreamSource inputStreamSource;

    @Override
    public DefinitionsModel getModel() throws IOException {
        Yaml yaml = new Yaml();

        Map<String, Object> data = yaml.load(inputStreamSource.getInputStream());

        return new YamlDefinitionsModel(data);
    }
}
