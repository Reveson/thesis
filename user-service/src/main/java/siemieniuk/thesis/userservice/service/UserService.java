package siemieniuk.thesis.userservice.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import siemieniuk.thesis.userservice.dto.ChangeInfoRequest;
import siemieniuk.thesis.userservice.exception.EntityAlreadyExistsException;
import siemieniuk.thesis.userservice.exception.NotFoundException;
import siemieniuk.thesis.userservice.model.User;
import siemieniuk.thesis.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final UserRepository userRepository;

	public User findById(long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Could not find user with id " + id));
	}

	public void changePassword(String newPassword) {
		//TODO to implement
	}

	public User changeInfo(long userId, ChangeInfoRequest request) {
		var user = findById(userId);

		user.setName(request.getName());
		user.setSurname(request.getSurname());
		user.setCity(request.getCity());
		user.setBirthDate(request.getBirthDate());
		user.setPrivate(request.isPrivate());

		return userRepository.save(user);
	}

	public void changePrivileges(int privileges) {
		//TODO to implement
	}

	public User createNew(String login, String password) {
		if (userRepository.existsByLogin(login))
			throw new EntityAlreadyExistsException("User with login " + login + " already exists.");

		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setPrivileges(2); //TODO hardcode

		return userRepository.save(user); //TODO race condition handling
	}
}
