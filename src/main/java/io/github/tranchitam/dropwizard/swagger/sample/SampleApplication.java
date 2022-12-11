package io.github.tranchitam.dropwizard.swagger.sample;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.github.tranchitam.dropwizard.swagger.bundles.SwaggerBundle;
import io.github.tranchitam.dropwizard.swagger.configurations.SwaggerBundleConfiguration;
import io.github.tranchitam.dropwizard.swagger.sample.resources.UserResource;

public class SampleApplication extends Application<SampleConfiguration> {

  public static void main(final String[] args) throws Exception {
    new SampleApplication().run(args);
  }

  @Override
  public String getName() {
    return "sample";
  }

  @Override
  public void initialize(Bootstrap<SampleConfiguration> bootstrap) {
    bootstrap.addBundle(
        new SwaggerBundle<SampleConfiguration>() {
          @Override
          protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
              SampleConfiguration configuration) {
            return configuration.getSwaggerBundleConfiguration();
          }
        });
  }

  @Override
  public void run(SampleConfiguration configuration, Environment environment) throws Exception {
    environment.jersey().register(new UserResource());
  }
}
