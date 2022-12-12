package io.github.tranchitam.dropwizard.swagger.services;

import static io.github.tranchitam.dropwizard.swagger.constants.Constants.EMPTY;
import static io.github.tranchitam.dropwizard.swagger.constants.Constants.SLASH;
import static io.github.tranchitam.dropwizard.swagger.constants.Constants.SLASH_STAR;

import io.dropwizard.Configuration;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.server.SimpleServerFactory;
import io.github.tranchitam.dropwizard.swagger.configurations.SwaggerBundleConfiguration;
import io.github.tranchitam.dropwizard.swagger.constants.Constants;
import java.util.Objects;
import java.util.Optional;

public class DefaultConfigurationService implements ConfigurationService {

  private final Configuration configuration;
  private final SwaggerBundleConfiguration swaggerBundleConfiguration;

  public DefaultConfigurationService(Configuration configuration, SwaggerBundleConfiguration swaggerBundleConfiguration) {
    this.configuration = configuration;
    this.swaggerBundleConfiguration = swaggerBundleConfiguration;
  }

  @Override
  public String getJerseyRootPath() {
    final ServerFactory serverFactory = configuration.getServerFactory();

    final Optional<String> rootPath;
    if (serverFactory instanceof SimpleServerFactory) {
      rootPath = ((SimpleServerFactory) serverFactory).getJerseyRootPath();
    } else {
      rootPath = ((DefaultServerFactory) serverFactory).getJerseyRootPath();
    }

    return stripUrlSlashes(rootPath.orElse(SLASH));
  }

  @Override
  public String getUrlPattern() {
    final String applicationContextPath = getApplicationContextPath();
    final String jerseyRootPath = getJerseyRootPath();

    if (SLASH.equals(jerseyRootPath) && SLASH.equals(applicationContextPath)) {
      return SLASH;
    }

    if (SLASH.equals(jerseyRootPath) && !SLASH.equals(applicationContextPath)) {
      return applicationContextPath;
    }

    if (!SLASH.equals(jerseyRootPath) && SLASH.equals(applicationContextPath)) {
      return jerseyRootPath;
    }

    return applicationContextPath + jerseyRootPath;
  }

  private String getSwaggerUriPrefix() {
    return Objects.nonNull(swaggerBundleConfiguration.getSwaggerUriPrefix())
        ? swaggerBundleConfiguration.getSwaggerUriPrefix() : EMPTY;
  }

  @Override
  public String getSwaggerUiUriPath() {
    final String jerseyRootPath = getJerseyRootPath();
    final String swaggerUriPrefix = getSwaggerUriPrefix();
    final String uriPathPrefix = jerseyRootPath.equals(SLASH) ? EMPTY : jerseyRootPath;
    return uriPathPrefix + swaggerUriPrefix + Constants.SWAGGER_UI;
  }

  @Override
  public String getSwaggerResourcePath() {
    final String swaggerUriPrefix = getSwaggerUriPrefix();
    return swaggerUriPrefix + Constants.SWAGGER;
  }

  @Override
  public String getSwaggerUiViewPath() {
    final String swaggerUriPrefix = getSwaggerUriPrefix();
    return getUrlPattern() + swaggerUriPrefix + Constants.SWAGGER_UI;
  }

  private String getOpenApiUriPrefix() {
    return Objects.nonNull(swaggerBundleConfiguration.getOpenApiUriPrefix())
        ? swaggerBundleConfiguration.getOpenApiUriPrefix() : EMPTY;
  }

  @Override
  public String getOpenApiResourceUriPath() {
    final String openApiUriPrefix = getOpenApiUriPrefix();
    return openApiUriPrefix + Constants.OPENAPI_JSON_YAML;
  }

  @Override
  public String getOpenApiJsonViewUriPath() {
    final String openApiUriPrefix = getOpenApiUriPrefix();
    return getUrlPattern() + openApiUriPrefix + Constants.OPENAPI;
  }

  private String getApplicationContextPath() {
    final ServerFactory serverFactory = configuration.getServerFactory();

    final String applicationContextPath;
    if (serverFactory instanceof SimpleServerFactory) {
      applicationContextPath = ((SimpleServerFactory) serverFactory).getApplicationContextPath();
    } else {
      applicationContextPath = ((DefaultServerFactory) serverFactory).getApplicationContextPath();
    }

    return stripUrlSlashes(applicationContextPath);
  }

  private String stripUrlSlashes(String urlToStrip) {
    if (urlToStrip.endsWith(SLASH_STAR)) {
      urlToStrip = urlToStrip.substring(0, urlToStrip.length() - 1);
    }

    if (!urlToStrip.isEmpty() && urlToStrip.endsWith(SLASH)) {
      urlToStrip = urlToStrip.substring(0, urlToStrip.length() - 1);
    }

    return urlToStrip;
  }
}
