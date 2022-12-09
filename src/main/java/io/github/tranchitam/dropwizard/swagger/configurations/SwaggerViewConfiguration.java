package io.github.tranchitam.dropwizard.swagger.configurations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class SwaggerViewConfiguration {

  private static final String DEFAULT_TITLE = "Swagger UI";

  private static final String DEFAULT_TEMPLATE = "index.ftl";

  @JsonProperty
  @Nullable
  private String pageTitle;

  @JsonProperty
  @Nullable
  private String templateUrl;

  public SwaggerViewConfiguration() {
    this.pageTitle = DEFAULT_TITLE;
    this.templateUrl = DEFAULT_TEMPLATE;
  }
}
