package io.github.tranchitam.dropwizard.swagger.views;

import static io.github.tranchitam.dropwizard.swagger.contansts.Constants.EMPTY;
import static io.github.tranchitam.dropwizard.swagger.contansts.Constants.SLASH;

import io.dropwizard.views.View;
import io.github.tranchitam.dropwizard.swagger.configurations.SwaggerViewConfiguration;
import io.github.tranchitam.dropwizard.swagger.contansts.Constants;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;

public class SwaggerView extends View {

  private static final String SWAGGER_URI_PATH = Constants.SWAGGER_UI;

  private final String swaggerAssetsPath;

  private final String contextPath;

  private final SwaggerViewConfiguration viewConfiguration;

  public SwaggerView(String contextRoot, String urlPattern, SwaggerViewConfiguration viewConfiguration) {
    super(viewConfiguration.getTemplateUrl(), StandardCharsets.UTF_8);

    String contextRootPrefix = SLASH.equals(contextRoot) ? EMPTY : contextRoot;

    // swagger-static should be found on the root context
    if (!contextRootPrefix.isEmpty()) {
      swaggerAssetsPath = contextRootPrefix + SWAGGER_URI_PATH;
    } else {
      swaggerAssetsPath = (urlPattern.equals(SLASH) ? SWAGGER_URI_PATH : (urlPattern + SWAGGER_URI_PATH));
    }

    contextPath = urlPattern.equals(SLASH) ? contextRootPrefix : (contextRootPrefix + urlPattern);

    this.viewConfiguration = viewConfiguration;
  }

  @Nullable
  public String getTitle() {
    return viewConfiguration.getPageTitle();
  }

  public String getSwaggerAssetsPath() {
    return swaggerAssetsPath;
  }

  public String getContextPath() {
    return contextPath;
  }
}
