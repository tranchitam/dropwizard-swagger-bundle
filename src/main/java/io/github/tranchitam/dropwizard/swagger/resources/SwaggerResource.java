package io.github.tranchitam.dropwizard.swagger.resources;

import io.github.tranchitam.dropwizard.swagger.views.SwaggerView;
import io.github.tranchitam.dropwizard.swagger.configurations.SwaggerViewConfiguration;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/swagger")
@Produces(MediaType.TEXT_HTML)
public class SwaggerResource {

  private final SwaggerViewConfiguration viewConfiguration;
  private final String contextRoot;
  private final String urlPattern;

  public SwaggerResource(String urlPattern, SwaggerViewConfiguration viewConfiguration) {
    this.urlPattern = urlPattern;
    this.viewConfiguration = viewConfiguration;
    this.contextRoot = "/";
  }

  public SwaggerResource(String urlPattern, SwaggerViewConfiguration viewConfiguration, String contextRoot) {
    this.viewConfiguration = viewConfiguration;
    this.urlPattern = urlPattern;
    this.contextRoot = contextRoot;
  }

  @GET
  public SwaggerView get() {
    return new SwaggerView(contextRoot, urlPattern, viewConfiguration);
  }
}
