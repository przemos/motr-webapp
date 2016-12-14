package uk.gov.dvsa.motr.web.system;

import org.glassfish.jersey.server.ResourceConfig;

import uk.gov.dvsa.motr.web.system.binder.ConfigBinder;
import uk.gov.dvsa.motr.web.system.binder.TemplateEngineBinder;

public class MotrApplication extends ResourceConfig {

    public MotrApplication() {

        packages("uk.gov.dvsa.motr.web.resource");

        register(new ConfigBinder());
        register(new TemplateEngineBinder());
    }
}
