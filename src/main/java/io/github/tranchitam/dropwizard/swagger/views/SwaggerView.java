package io.github.tranchitam.dropwizard.swagger.views;

import io.dropwizard.views.View;
import io.github.tranchitam.dropwizard.swagger.configurations.SwaggerViewConfiguration;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;

public class SwaggerView extends View {

  private final String swaggerUiViewPath;

  private final String openApiJsonViewPath;

  private final SwaggerViewConfiguration viewConfiguration;

  public SwaggerView(String swaggerUiViewPath, String openApiJsonViewPath,
      SwaggerViewConfiguration viewConfiguration) {
    super(viewConfiguration.getTemplateUrl(), StandardCharsets.UTF_8);
    this.swaggerUiViewPath = swaggerUiViewPath;
    this.openApiJsonViewPath = openApiJsonViewPath;
    this.viewConfiguration = viewConfiguration;
  }

  @Nullable
  public String getTitle() {
    return viewConfiguration.getPageTitle();
  }

  public String getSwaggerUiViewPath() {
    return swaggerUiViewPath;
  }

  public String getOpenApiJsonViewPath() {
    return openApiJsonViewPath;
  }
}
