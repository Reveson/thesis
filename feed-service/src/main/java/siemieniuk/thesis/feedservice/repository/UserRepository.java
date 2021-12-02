package siemieniuk.thesis.feedservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import siemieniuk.thesis.feedservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
