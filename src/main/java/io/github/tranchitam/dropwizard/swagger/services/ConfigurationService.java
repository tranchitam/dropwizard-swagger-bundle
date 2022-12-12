package io.github.tranchitam.dropwizard.swagger.services;

public interface ConfigurationService {

  String getJerseyRootPath();

  String getUrlPattern();

  String getSwaggerUiUriPath();

  String getSwaggerResourcePath();

  String getSwaggerUiViewPath();

  String getOpenApiResourceUriPath();

  String getOpenApiJsonViewUriPath();
}
