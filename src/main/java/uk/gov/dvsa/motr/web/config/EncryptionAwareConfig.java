package uk.gov.dvsa.motr.web.config;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Function.identity;

public class EncryptionAwareConfig implements Config {

    private final Set<ConfigKey> encryptedVariables;

    private final AWSKMS decryptApi;

    private final Config wrappedConfig;

    public EncryptionAwareConfig(Config wrappedConfig, Set<ConfigKey> encryptedVariables, AWSKMS decryptApi) {
        this.encryptedVariables = encryptedVariables;
        this.decryptApi = decryptApi;
        this.wrappedConfig = wrappedConfig;
    }

    @Override
    public Optional<String> getValue(ConfigKey var) {

        return wrappedConfig.getValue(var)
                .map(encryptedVariables.contains(var) ? this::decrypt : identity());

    }

    private String decrypt(String encryptedValue) {

        ByteBuffer encryptedBlob = ByteBuffer.wrap(Base64.getDecoder().decode(encryptedValue));
        DecryptResult result = decryptApi.decrypt(new DecryptRequest().withCiphertextBlob(encryptedBlob));

        return new String(result.getPlaintext().array());
    }
}
