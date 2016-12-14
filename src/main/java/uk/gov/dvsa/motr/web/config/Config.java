package uk.gov.dvsa.motr.web.config;


import java.util.Optional;

public interface Config {

    Optional<String> getValue(ConfigKey key);
}
