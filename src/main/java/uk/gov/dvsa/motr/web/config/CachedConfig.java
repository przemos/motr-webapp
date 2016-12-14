package uk.gov.dvsa.motr.web.config;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CachedConfig implements Config {

    private final Config wrappedConfig;

    private final Map<ConfigKey, Optional<String>> cache = new ConcurrentHashMap<>();

    public CachedConfig(Config wrappedConfig) {
        this.wrappedConfig = wrappedConfig;
    }

    @Override
    public Optional<String> getValue(ConfigKey key) {
        return cache.computeIfAbsent(key, wrappedConfig::getValue);
    }
}
