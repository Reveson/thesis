package siemieniuk.thesis.userservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import siemieniuk.thesis.userservice.dto.ChangeInfoRequest;
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
				.orElseThrow(() -> new NotFoundException("Could not find user with id " + id));
	}

	public List<User> findByIds(List<Long> ids) {
		return userRepository.findAllByIds(ids);
	}

	public List<User> findByLogins(List<String> logins) {
		return userRepository.findAllByLogins(logins);
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
}
