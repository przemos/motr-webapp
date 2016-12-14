package uk.gov.dvsa.motr.web.resource;


import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import java.util.HashMap;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class HomepageResource {

    private Handlebars handlebars;

    @Inject
    public HomepageResource(Handlebars handlebars) {
        this.handlebars = handlebars;
    }

    @GET
    @Produces("text/html")
    public String homePage() throws Exception {
        Template t = handlebars.compile("home");
        return t.apply(new HashMap<>());
    }
}
