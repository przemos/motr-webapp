package uk.gov.dvsa.motr.web.system.binder;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import uk.gov.dvsa.motr.web.config.Config;

import javax.inject.Inject;

import static uk.gov.dvsa.motr.web.system.SystemVariable.STATIC_ASSETS_HASH;

public class TemplateEngineBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindFactory(TemplateEngineFactory.class).to(Handlebars.class);
    }

    static class TemplateEngineFactory implements Factory<Handlebars> {

        private static final String ASSETS_FORMAT = "assets/%s?v=%s";
        private static final String ASSETS_HELPER_NAME = "asset";

        private Config config;

        @Inject
        public TemplateEngineFactory(Config config) {
            this.config = config;
        }

        @Override
        public Handlebars provide() {

            String staticAssetsHash = config.getValue(STATIC_ASSETS_HASH).orElse("");

            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/template");
            loader.setSuffix(".hbs");
            Handlebars handlebars = new Handlebars(loader);
            handlebars.registerHelper(ASSETS_HELPER_NAME, (context, options) ->
                    String.format(ASSETS_FORMAT, options.param(0), staticAssetsHash)
            );

            return handlebars;
        }

        @Override
        public void dispose(Handlebars instance) {

        }
    }

    public void dispose(Handlebars instance) {

    }

}
