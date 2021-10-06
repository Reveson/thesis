package siemieniuk.thesis.searchservice.service;

import siemieniuk.thesis.searchservice.data.User;
import siemieniuk.thesis.searchservice.repository.UserRepository;
import java.util.List;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User addUser(User user) {
    return userRepository.save(user);
  }

  public List<User> getUsers(String name) {
    return userRepository.findAllByQuery(name);
  }

}
