package io.github.tranchitam.dropwizard.swagger.configurations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.tranchitam.dropwizard.swagger.constants.Constants;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class SwaggerBundleConfiguration {

  @JsonProperty
  @NotEmpty
  private Set<String> resourcePackages = new HashSet<>();

  @JsonProperty
  @Nullable
  private String title;

  @JsonProperty
  @Nullable
  private String description;

  @JsonProperty
  @Nullable
  private String termsOfServiceUrl;

  @JsonProperty
  @Nullable
  private String contactEmail;

  @JsonProperty
  @Nullable
  private String contactUrl;

  @JsonProperty("swaggerView")
  private SwaggerViewConfiguration swaggerViewConfiguration = new SwaggerViewConfiguration();

  @JsonProperty
  private boolean prettyPrint = true;

  @JsonProperty
  private String contextRoot = Constants.SLASH;

  @JsonProperty
  private boolean enabled = true;

  @JsonProperty
  private boolean includeSwaggerResource = true;

  @JsonProperty
  @Nullable
  private String swaggerUriPrefix;

  @JsonProperty
  @Nullable
  private String openApiUriPrefix;
}
