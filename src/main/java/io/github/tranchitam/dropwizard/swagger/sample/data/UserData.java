package io.github.tranchitam.dropwizard.swagger.sample.data;

import io.github.tranchitam.dropwizard.swagger.sample.model.User;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class UserData {

  static Map<String, User> usernameToUsers = new ConcurrentHashMap<>();
  static Map<Long, User> idToUsers = new ConcurrentHashMap<>();

  static {
    User user1 = createUser(1, "username1", "firstname 1", "lastname1", "username1@test.com", "0123-456-789", 1);
    usernameToUsers.put(user1.getUsername(), user1);
    idToUsers.put(user1.getId(), user1);

    User user2 = createUser(2, "username2", "firstname 2", "lastname2", "username2@test.com", "0123-456-789", 1);
    usernameToUsers.put(user2.getUsername(), user2);
    idToUsers.put(user2.getId(), user2);
  }

  public User findUserByName(String username) {
    return usernameToUsers.get(username);
  }

  public User findUserById(Long id) {
    return idToUsers.get(id);
  }

  public void addUser(User user) {
    if (usernameToUsers.containsKey(user.getUsername()) || idToUsers.containsKey(user.getId())) {
      return;
    }
    usernameToUsers.put(user.getUsername(), user);
    idToUsers.put(user.getId(), user);
  }

  public User updateUser(User user) {
    User existingUser = findUserById(user.getId());
    if (Objects.isNull(existingUser)) {
      return null;
    }
    idToUsers.put(user.getId(), user);
    usernameToUsers.put(user.getUsername(), user);
    return user;
  }

  public boolean removeUser(String username) {
    if (!usernameToUsers.containsKey(username)) {
      return false;
    }
    User user = usernameToUsers.remove(username);
    idToUsers.remove(user.getId());
    return false;
  }

  private static User createUser(long id, String username, String firstName,
      String lastName, String email, String phone, int userStatus) {
    User user = new User();
    user.setId(id);
    user.setUsername(username);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setPassword("********");
    user.setPhone(phone);
    user.setUserStatus(userStatus);
    user.setCreatedAt(new Date());
    return user;
  }
}