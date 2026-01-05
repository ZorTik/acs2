package me.zort.acs.core.domain.ruleset;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import me.zort.acs.core.domain.ruleset.exception.InvalidRuleSetConfigException;
import me.zort.acs.core.domain.ruleset.exception.InvalidRuleSetException;
import org.apache.commons.io.IOUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Default implementation of RuleSetParser that parses rule set from JAR file containing ruleset.yml.
 *
 * @author ZorTik
 */
public class DefaultRuleSetParser implements RuleSetParser {
    private static final String CONFIG_ENTRY_REGEX = "^ruleset\\.ya?ml$";

    private final Yaml yaml;
    private final Validator validator;

    public DefaultRuleSetParser(Validator validator) {
        this(new Yaml(), validator);
    }

    public DefaultRuleSetParser(Yaml yaml, Validator validator) {
        this.yaml = yaml;
        this.validator = validator;
    }

    @Override
    public RuleSet parseRuleSet(byte[] data) throws InvalidRuleSetException {
        validateRuleSetData(data);

        RuleSet ruleSet = parseRuleSetConfig(getRuleSetConfig(data));

        Set<ConstraintViolation<RuleSet>> violations = validator.validate(ruleSet);
        if (!violations.isEmpty()) {
            throw new InvalidRuleSetConfigException(violations);
        }
        return ruleSet;
    }

    @Override
    public void integrateRuleSet(RuleSetIntegrationVisitor integrationVisitor) {
        // TODO: Implement integration logic
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
                if (!entry.getName().matches(CONFIG_ENTRY_REGEX)) {
                    continue;
                }

                return IOUtils.toByteArray(jar);
            }

            throw new InvalidRuleSetException("No ruleset.yml entry found in the file");
        } catch (IOException e) {
            throw new InvalidRuleSetException("Failed to load jar file", e);
        }
    }

    private RuleSet parseRuleSetConfig(byte[] ruleSetData) {
        return this.yaml.loadAs(new ByteArrayInputStream(ruleSetData), RuleSetImpl.class);
    }
}
