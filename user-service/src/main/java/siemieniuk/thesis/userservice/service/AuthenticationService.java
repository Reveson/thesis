package siemieniuk.thesis.userservice.service;

import javax.transaction.Transactional;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import siemieniuk.thesis.userservice.dto.LoginRequest;
import siemieniuk.thesis.userservice.exception.EntityAlreadyExistsException;
import siemieniuk.thesis.userservice.exception.NotFoundException;
import siemieniuk.thesis.userservice.model.User;
import siemieniuk.thesis.userservice.repository.UserRepository;
import siemieniuk.thesis.userservice.service.client.KeycloakFeignClient;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
	private final UserRepository userRepository;
	private final KeycloakFeignClient keycloakFeignClient;
	private final Environment environment;

	public String loginAndGetJwtToken(LoginRequest request) {
		return keycloakFeignClient.getToken(mapLoginRequestToKeycloakBody(request)).getBody();
	}

	public User createNew(LoginRequest request) {
		String login = request.getLogin();
		if (userRepository.existsByLogin(login))
			throw new EntityAlreadyExistsException(String.format("User with name %s already exists.", login));

		User user = new User();
		user.setLogin(login);
		user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
		user.setPrivileges(2); //TODO hardcode

		return userRepository.save(user); //TODO race condition handling
	}

	private void validatePassword(String provided, String encrypted) {
		if (!BCrypt.checkpw(provided, encrypted))
			throw new NotFoundException("Username or password is not correct.");
	}

	private MultiValueMap<String, String> mapLoginRequestToKeycloakBody(LoginRequest loginRequest) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "password");
		map.add("client_id", environment.getProperty("keycloak.resource"));
		map.add("client_secret", environment.getProperty("keycloak.credentials.secret"));
		map.add("username", loginRequest.getLogin());
		map.add("password", loginRequest.getPassword());

		return map;
	}
}
