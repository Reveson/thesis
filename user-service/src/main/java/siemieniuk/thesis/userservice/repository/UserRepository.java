package siemieniuk.thesis.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import siemieniuk.thesis.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByLogin(String login);
}
