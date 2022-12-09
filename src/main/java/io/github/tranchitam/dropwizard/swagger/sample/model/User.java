package io.github.tranchitam.dropwizard.swagger.sample.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

  private long id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String phone;

  @Schema(title = "User Status", allowableValues = "1-registered,2-active,3-closed")
  private int userStatus;
  private Date createdAt;
}