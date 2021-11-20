package siemieniuk.thesis.userservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import siemieniuk.thesis.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByLogin(String login);
	Optional<User> getByLogin(String login);

	@Query("select u from User u where u.id in (:ids)")
	List<User> findAllByIds(List<Long> ids);

	@Query("select u from User u where u.login in (:logins)")
	List<User> findAllByLogins(List<String> logins);
}
