package io.github.tranchitam.dropwizard.swagger.helpers;

import static io.github.tranchitam.dropwizard.swagger.contansts.Constants.EMPTY;
import static io.github.tranchitam.dropwizard.swagger.contansts.Constants.SLASH;
import static io.github.tranchitam.dropwizard.swagger.contansts.Constants.SLASH_STAR;

import io.dropwizard.Configuration;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.server.SimpleServerFactory;
import io.github.tranchitam.dropwizard.swagger.configurations.SwaggerBundleConfiguration;
import io.github.tranchitam.dropwizard.swagger.contansts.Constants;
import java.util.Optional;

public class ConfigurationHelper {

  private final Configuration configuration;
  private final SwaggerBundleConfiguration swaggerBundleConfiguration;

  public ConfigurationHelper(Configuration configuration,
      SwaggerBundleConfiguration swaggerBundleConfiguration) {
    this.configuration = configuration;
    this.swaggerBundleConfiguration = swaggerBundleConfiguration;
  }

  public String getJerseyRootPath() {
    if (swaggerBundleConfiguration.getUriPrefix() != null) {
      return swaggerBundleConfiguration.getUriPrefix();
    }

    final ServerFactory serverFactory = configuration.getServerFactory();

    final Optional<String> rootPath;
    if (serverFactory instanceof SimpleServerFactory) {
      rootPath = ((SimpleServerFactory) serverFactory).getJerseyRootPath();
    } else {
      rootPath = ((DefaultServerFactory) serverFactory).getJerseyRootPath();
    }

    return stripUrlSlashes(rootPath.orElse(SLASH));
  }

  public String getUrlPattern() {
    if (swaggerBundleConfiguration.getUriPrefix() != null) {
      return swaggerBundleConfiguration.getUriPrefix();
    }

    final String applicationContextPath = getApplicationContextPath();
    final String rootPath = getJerseyRootPath();

    final String urlPattern;
    if (SLASH.equals(rootPath) && SLASH.equals(applicationContextPath)) {
      urlPattern = SLASH;
    } else if (SLASH.equals(rootPath) && !SLASH.equals(applicationContextPath)) {
      urlPattern = applicationContextPath;
    } else if (!SLASH.equals(rootPath) && SLASH.equals(applicationContextPath)) {
      urlPattern = rootPath;
    } else {
      urlPattern = applicationContextPath + rootPath;
    }

    return urlPattern;
  }

  public String getSwaggerUriPath() {
    final String jerseyRootPath = getJerseyRootPath();
    final String uriPathPrefix = jerseyRootPath.equals(SLASH) ? EMPTY : jerseyRootPath;
    return uriPathPrefix + Constants.SWAGGER_UI;
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
