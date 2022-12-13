package io.github.tranchitam.dropwizard.swagger.tests.applications;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.github.tranchitam.dropwizard.swagger.bundles.SwaggerBundle;
import io.github.tranchitam.dropwizard.swagger.configurations.SwaggerBundleConfiguration;
import io.github.tranchitam.dropwizard.swagger.tests.configurations.DefaultConfiguration;
import io.github.tranchitam.dropwizard.swagger.tests.resources.TestUserResource;

public class DefaultApplication extends Application<DefaultConfiguration> {

  public static void main(final String[] args) throws Exception {
    new DefaultApplication().run(args);
  }

  @Override
  public String getName() {
    return "default-application";
  }

  @Override
  public void initialize(Bootstrap<DefaultConfiguration> bootstrap) {
    bootstrap.addBundle(
        new SwaggerBundle<DefaultConfiguration>() {
          @Override
          protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
              DefaultConfiguration configuration) {
            return configuration.getSwaggerBundleConfiguration();
          }
        });
  }

  @Override
  public void run(DefaultConfiguration configuration, Environment environment) throws Exception {
    environment.jersey().register(new TestUserResource());
  }
}
