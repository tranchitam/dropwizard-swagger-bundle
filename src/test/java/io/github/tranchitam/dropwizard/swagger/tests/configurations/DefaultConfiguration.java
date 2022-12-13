package io.github.tranchitam.dropwizard.swagger.tests.configurations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.github.tranchitam.dropwizard.swagger.configurations.SwaggerBundleConfiguration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultConfiguration extends Configuration {

  @Valid
  @NotNull
  private final SwaggerBundleConfiguration swaggerBundleConfiguration = new SwaggerBundleConfiguration();

  @JsonProperty("swagger")
  public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
    return swaggerBundleConfiguration;
  }
}
