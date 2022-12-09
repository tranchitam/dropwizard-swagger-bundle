package io.github.tranchitam.dropwizard.swagger.sample.resources;

import io.github.tranchitam.dropwizard.swagger.sample.data.UserData;
import io.github.tranchitam.dropwizard.swagger.sample.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Objects;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces({"application/json", "application/xml"})
public class UserResource {

  static UserData userData = new UserData();

  @GET
  @Path("/{userId}")
  @Operation(summary = "Find a user by ID",
      tags = {"users"},
      description = "Returns a user when 1 <= ID <= 2.  ID > 2 or nonintegers will simulate API error conditions",
      responses = {
          @ApiResponse(description = "The user", content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = User.class)
          )),
          @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "404", description = "User not found")
      })
  public Response getUserById(
      @Parameter(
          description = "ID of user that needs to be fetched",
          schema = @Schema(
              type = "integer",
              format = "int64",
              description = "Param ID of User that needs to be fetched",
              allowableValues = {"1", "2", "3"}
          ),
          required = true)
      @PathParam("userId") Long userId) throws NotFoundException {
    User user = userData.findUserById(userId);
    if (Objects.nonNull(user)) {
      return Response.ok().entity(user).build();
    } else {
      throw new NotFoundException("User not found");
    }
  }

  @POST
  @Consumes("application/json")
  @Operation(summary = "Add a new user",
      tags = {"users"},
      responses = {
          @ApiResponse(responseCode = "405", description = "Invalid input")
      })
  public Response addUser(
      @Parameter(description = "User object that needs to be added", required = true) User user) {
    userData.addUser(user);
    return Response.ok().entity("SUCCESS").build();
  }

  @PUT
  @Operation(summary = "Update an existing user",
      tags = {"users"},
      responses = {
          @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "404", description = "User not found"),
          @ApiResponse(responseCode = "405", description = "Validation exception")})
  public Response updateUser(
      @Parameter(description = "User object that needs to be added", required = true) User user)
      throws NotFoundException {
    User updatedUser = userData.updateUser(user);
    if (Objects.nonNull(updatedUser)) {
      return Response.ok().entity("SUCCESS").build();
    } else {
      throw new NotFoundException("User not found");
    }
  }
}
