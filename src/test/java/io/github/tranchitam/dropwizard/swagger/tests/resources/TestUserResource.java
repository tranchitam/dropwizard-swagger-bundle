package io.github.tranchitam.dropwizard.swagger.tests.resources;

import io.github.tranchitam.dropwizard.swagger.sample.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces({"application/json", "application/xml"})
public class TestUserResource {

  public static final String OPERATION_DESCRIPTION = "This is user endpoint for test";

  public static final String OPERATION_SUMMARY = "This is user endpoint for test";

  @GET
  @Path("/{userId}")
  @Operation(
      summary = OPERATION_SUMMARY,
      tags = {"users"},
      description = OPERATION_DESCRIPTION,
      responses = {
          @ApiResponse(
              description = "The user",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = User.class)
              )),
          @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "404", description = "User not found")
      })
  public Response findUserById(@QueryParam("userId") final String userId) {
    return Response.ok(new User()).build();
  }
}
