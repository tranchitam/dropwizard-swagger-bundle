package io.github.tranchitam.dropwizard.swagger.bundles;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.github.tranchitam.dropwizard.swagger.configurations.SwaggerBundleConfiguration;
import io.github.tranchitam.dropwizard.swagger.contansts.Constants;
import io.github.tranchitam.dropwizard.swagger.helpers.ConfigurationHelper;
import io.github.tranchitam.dropwizard.swagger.resources.DropwizardVersion2xOpenApiResource;
import io.github.tranchitam.dropwizard.swagger.resources.SwaggerResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.ArrayList;
import java.util.List;

public abstract class SwaggerBundle<T extends Configuration> implements ConfiguredBundle<T> {

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
    bootstrap.addBundle(new ViewBundle<Configuration>());
  }

  @Override
  public void run(T configuration, Environment environment) {
    SwaggerBundleConfiguration swaggerBundleConfiguration = getSwaggerBundleConfiguration(
        configuration);
    if (!swaggerBundleConfiguration.isEnabled()) {
      return;
    }
    ConfigurationHelper configurationHelper = new ConfigurationHelper(configuration,
        swaggerBundleConfiguration);
    SwaggerConfiguration oasConfig = buildSwaggerConfiguration(swaggerBundleConfiguration,
        configurationHelper);

    environment.jersey().register(new DropwizardVersion2xOpenApiResource().openApiConfiguration(oasConfig));

    if (swaggerBundleConfiguration.isIncludeSwaggerResource()) {
      new AssetsBundle(Constants.SWAGGER_UI, configurationHelper.getSwaggerUriPath(), null, Constants.SWAGGER_ASSETS)
          .run(configuration, environment);
      environment
          .jersey()
          .register(
              new SwaggerResource(
                  configurationHelper.getUrlPattern(),
                  swaggerBundleConfiguration.getSwaggerViewConfiguration(),
                  swaggerBundleConfiguration.getContextRoot()));
    }
  }

  private SwaggerConfiguration buildSwaggerConfiguration(
      SwaggerBundleConfiguration swaggerBundleConfiguration,
      ConfigurationHelper configurationHelper) {
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
    servers.add(new Server().url(configurationHelper.getJerseyRootPath()));
    oas.servers(servers);

    SwaggerConfiguration oasConfig = new SwaggerConfiguration()
        .openAPI(oas)
        .prettyPrint(swaggerBundleConfiguration.isPrettyPrint())
        .resourcePackages(swaggerBundleConfiguration.getResourcePackages());
    return oasConfig;
  }

  protected abstract SwaggerBundleConfiguration getSwaggerBundleConfiguration(T configuration);
}
