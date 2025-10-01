package me.zort.acs.plane.domain.ruleset;

import me.zort.acs.plane.api.domain.ruleset.RuleSet;
import me.zort.acs.plane.api.domain.ruleset.RuleSetParser;
import me.zort.acs.plane.api.domain.ruleset.exception.MalformedRuleSetDataException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

@Service
public class DefaultRuleSetParser implements RuleSetParser {
    private static final String CONFIG_ENTRY_NAME = "ruleset.yml";

    private final Yaml yaml;

    public DefaultRuleSetParser() {
        this(new Yaml());
    }

    public DefaultRuleSetParser(Yaml yaml) {
        this.yaml = yaml;
    }

    @Override
    public RuleSet parseRuleSet(byte[] data) throws MalformedRuleSetDataException {
        validateRuleSetData(data);

        return parseRuleSetConfig(getRuleSetConfig(data));
    }

    private static void validateRuleSetData(byte[] data) {
        // TODO: Validate if the file contains meta-inf services the right imports
    }

    /**
     * Read ruleset config entry data.
     *
     * @param file The file holding the config
     * @return The config data
     */
    private static byte[] getRuleSetConfig(byte[] file) {
        try (JarInputStream jar = new JarInputStream(new ByteArrayInputStream(file))) {
            JarEntry entry;
            while ((entry = jar.getNextJarEntry()) != null) {
                if (!CONFIG_ENTRY_NAME.equals(entry.getName())) {
                    continue;
                }

                return IOUtils.toByteArray(jar);
            }

            throw new MalformedRuleSetDataException("No ruleset.yml entry found in the file");
        } catch (IOException e) {
            throw new MalformedRuleSetDataException("Failed to load jar file", e);
        }
    }

    private RuleSet parseRuleSetConfig(byte[] ruleSetData) {
        return this.yaml.loadAs(new ByteArrayInputStream(ruleSetData), RuleSetImpl.class);
    }
}
