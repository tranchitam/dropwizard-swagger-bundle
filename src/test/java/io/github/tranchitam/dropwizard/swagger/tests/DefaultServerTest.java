package io.github.tranchitam.dropwizard.swagger.tests;

import static org.assertj.core.api.Assertions.assertThat;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.github.tranchitam.dropwizard.swagger.services.ConfigurationService;
import io.github.tranchitam.dropwizard.swagger.services.DefaultConfigurationService;
import io.github.tranchitam.dropwizard.swagger.tests.applications.DefaultApplication;
import io.github.tranchitam.dropwizard.swagger.tests.configurations.DefaultConfiguration;
import io.github.tranchitam.dropwizard.swagger.tests.resources.TestUserResource;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
public class DefaultServerTest {

  private static DropwizardAppExtension<DefaultConfiguration> RULE =
      new DropwizardAppExtension<>(
          DefaultApplication.class, ResourceHelpers.resourceFilePath("dropwizard.yml"));

  private ConfigurationService configurationService;

  @BeforeEach
  public void init() {
    configurationService = new DefaultConfigurationService(RULE.getConfiguration(),
        RULE.getConfiguration()
            .getSwaggerBundleConfiguration());
  }

  @Test
  public void testOpenapiJsonAvailable() {
    String openapiJsonUri = String.format("http://localhost:%d%s",
        RULE.getLocalPort(),
        configurationService.getOpenApiJsonViewUriPath());
    Response response = RULE.client()
        .target(openapiJsonUri)
        .request()
        .get();
    assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
    String responseBody = response.readEntity(String.class);
    assertThat(responseBody).contains(TestUserResource.OPERATION_DESCRIPTION);
  }

  @Test
  public void testSwaggerResourceAvailable() {
    String swaggerResourceUri = String.format("http://localhost:%d%s",
        RULE.getLocalPort(),
        new StringBuilder()
            .append(configurationService.getUrlPattern())
            .append(configurationService.getSwaggerResourcePath()));
    Response response = RULE.client()
        .target(swaggerResourceUri)
        .request()
        .get();
    assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
  }
}
