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
  private final String swaggerUiViewPath;
  private final String openApiJsonViewPath;

  public SwaggerResource(String swaggerUiViewPath, String openApiJsonViewPath,
      SwaggerViewConfiguration viewConfiguration) {
    this.viewConfiguration = viewConfiguration;
    this.swaggerUiViewPath = swaggerUiViewPath;
    this.openApiJsonViewPath = openApiJsonViewPath;
  }

  @GET
  public SwaggerView get() {
    return new SwaggerView(swaggerUiViewPath, openApiJsonViewPath, viewConfiguration);
  }
}
