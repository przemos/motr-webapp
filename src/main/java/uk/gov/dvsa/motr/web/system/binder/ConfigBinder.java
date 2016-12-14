package uk.gov.dvsa.motr.web.system.binder;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClient;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import uk.gov.dvsa.motr.web.config.CachedConfig;
import uk.gov.dvsa.motr.web.config.Config;
import uk.gov.dvsa.motr.web.config.ConfigKey;
import uk.gov.dvsa.motr.web.config.EncryptionAwareConfig;
import uk.gov.dvsa.motr.web.config.EnvironmentVariableConfig;

import java.util.HashSet;
import java.util.Set;

import static uk.gov.dvsa.motr.web.system.SystemVariable.GOV_NOTIFY_API_TOKEN;
import static uk.gov.dvsa.motr.web.system.SystemVariable.MOT_TEST_REMINDER_INFO_API_TOKEN;

import static java.util.Arrays.asList;

public class ConfigBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindFactory(MotrConfigFactory.class).to(Config.class);
    }

    static class MotrConfigFactory implements Factory<Config> {


        @Override
        public Config provide() {
            AWSKMS kms = new AWSKMSClient();

            return new CachedConfig(
                    new EncryptionAwareConfig(
                            new EnvironmentVariableConfig(), secretVariables(),  kms
                    )
            );
        }

        private static Set<ConfigKey> secretVariables() {

            Set<ConfigKey> secretVariables = new HashSet<>();
            secretVariables.addAll(asList(
                    GOV_NOTIFY_API_TOKEN,
                    MOT_TEST_REMINDER_INFO_API_TOKEN
            ));

            return secretVariables;
        }

        @Override
        public void dispose(Config instance) {

        }
    }
}