package io.github.tranchitam.dropwizard.swagger.sample;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.github.tranchitam.dropwizard.swagger.configurations.SwaggerBundleConfiguration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SampleConfiguration extends Configuration {

  @Valid @NotNull
  private final SwaggerBundleConfiguration swaggerBundleConfiguration = new SwaggerBundleConfiguration();

  @JsonProperty("swagger")
  public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
    return swaggerBundleConfiguration;
  }
}
