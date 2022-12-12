package io.github.tranchitam.dropwizard.swagger.bundles;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.github.tranchitam.dropwizard.swagger.configurations.SwaggerBundleConfiguration;
import io.github.tranchitam.dropwizard.swagger.constants.Constants;
import io.github.tranchitam.dropwizard.swagger.helpers.ReflectionsHelper;
import io.github.tranchitam.dropwizard.swagger.resources.DropwizardVersion2xOpenApiResource;
import io.github.tranchitam.dropwizard.swagger.resources.SwaggerResource;
import io.github.tranchitam.dropwizard.swagger.services.ConfigurationService;
import io.github.tranchitam.dropwizard.swagger.services.DefaultConfigurationService;
import io.swagger.v3.jaxrs2.integration.resources.BaseOpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Path;

public abstract class SwaggerBundle<T extends Configuration> implements ConfiguredBundle<T> {

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
    bootstrap.addBundle(new ViewBundle<Configuration>());
  }

  @Override
  public void run(T configuration, Environment environment) {
    SwaggerBundleConfiguration swaggerBundleConfiguration = getSwaggerBundleConfiguration(configuration);
    if (!swaggerBundleConfiguration.isEnabled()) {
      return;
    }
    ConfigurationService configurationService = buildConfigurationService(configuration, swaggerBundleConfiguration);
    SwaggerConfiguration oasConfig = buildSwaggerConfiguration(swaggerBundleConfiguration, configurationService);

    environment.jersey().register(buildOpenApiResource(configurationService, oasConfig));

    if (swaggerBundleConfiguration.isIncludeSwaggerResource()) {
      new AssetsBundle(Constants.SWAGGER_UI, configurationService.getSwaggerUiUriPath(), null, Constants.SWAGGER_ASSETS)
          .run(configuration, environment);
      environment.jersey().register(buildSwaggerResource(swaggerBundleConfiguration, configurationService));
    }
  }

  protected DefaultConfigurationService buildConfigurationService(T configuration, SwaggerBundleConfiguration swaggerBundleConfiguration) {
    return new DefaultConfigurationService(configuration, swaggerBundleConfiguration);
  }

  protected BaseOpenApiResource buildOpenApiResource(ConfigurationService configurationHelper, SwaggerConfiguration oasConfig) {
    Annotation pathAnnotation = DropwizardVersion2xOpenApiResource.class.getAnnotation(Path.class);
    ReflectionsHelper.modifyAnnotation(pathAnnotation, Constants.PATH_VALUE_KEY, configurationHelper.getOpenApiResourceUriPath());
    BaseOpenApiResource openApiResource = new DropwizardVersion2xOpenApiResource().openApiConfiguration(oasConfig);
    return openApiResource;
  }

  protected SwaggerResource buildSwaggerResource(SwaggerBundleConfiguration swaggerBundleConfiguration,
      ConfigurationService configurationHelper) {
    Annotation pathAnnotation = SwaggerResource.class.getAnnotation(Path.class);
    ReflectionsHelper.modifyAnnotation(pathAnnotation, Constants.PATH_VALUE_KEY, configurationHelper.getSwaggerResourcePath());
    SwaggerResource swaggerResource = new SwaggerResource(
        configurationHelper.getSwaggerUiViewPath(),
        configurationHelper.getOpenApiJsonViewUriPath(),
        swaggerBundleConfiguration.getSwaggerViewConfiguration());
    return swaggerResource;
  }

  protected SwaggerConfiguration buildSwaggerConfiguration(
      SwaggerBundleConfiguration swaggerBundleConfiguration,
      ConfigurationService configurationHelper) {
    Info info = new Info()
        .title(swaggerBundleConfiguration.getTitle())
        .description(swaggerBundleConfiguration.getDescription())
        .termsOfService(swaggerBundleConfiguration.getTermsOfServiceUrl())
        .contact(new Contact()
            .email(swaggerBundleConfiguration.getContactEmail())
            .url(swaggerBundleConfiguration.getContactUrl()));

    OpenAPI oas = new OpenAPI();
    oas.info(info);
    List<Server> servers = new ArrayList<>();
    servers.add(new Server().url(configurationHelper.getUrlPattern()));
    oas.servers(servers);

    SwaggerConfiguration oasConfig = new SwaggerConfiguration()
        .openAPI(oas)
        .prettyPrint(swaggerBundleConfiguration.isPrettyPrint())
        .resourcePackages(swaggerBundleConfiguration.getResourcePackages());
    return oasConfig;
  }

  protected abstract SwaggerBundleConfiguration getSwaggerBundleConfiguration(T configuration);
}
