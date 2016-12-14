package uk.gov.dvsa.motr.web.config;

import java.util.Optional;

public class EnvironmentVariableConfig implements Config {

    public EnvironmentVariableConfig() {

    }
    @Override
    public Optional<String> getValue(ConfigKey var) {
        return Optional.ofNullable(System.getenv(var.getName()));
    }
}
