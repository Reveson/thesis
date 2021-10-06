package siemieniuk.thesis.searchservice.controller;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.searchservice.data.User;
import siemieniuk.thesis.searchservice.service.UserService;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public User addUser(@RequestBody User user) {
    return userService.addUser(user);
  }

  @GetMapping("/find")
  public List<User> findAllByNameAnnotations(@RequestParam("query") String query) {
    return userService.getUsers(query);
  }
}
