package io.github.tranchitam.dropwizard.swagger.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

  public static final String SWAGGER = "/swagger";

  public static final String SWAGGER_UI = "/swagger-ui";

  public static final String SWAGGER_ASSETS = "swagger-assets";

  public static final String SLASH = "/";

  public static final String SLASH_STAR = "/*";

  public static final String EMPTY = "";

  public static final String OPENAPI = "/openapi.json";

  public static final String OPENAPI_JSON_YAML = "/openapi.{type:json|yaml}";

  public static final String PATH_VALUE_KEY = "value";
}
