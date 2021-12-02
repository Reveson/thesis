package siemieniuk.thesis.userservice.service;

import java.util.Base64;

import javax.transaction.Transactional;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;
import siemieniuk.thesis.userservice.dto.LoginRequest;
import siemieniuk.thesis.userservice.dto.LoginResponse;
import siemieniuk.thesis.userservice.dto.RegisterKeycloakRequest;
import siemieniuk.thesis.userservice.dto.RegisterRequest;
import siemieniuk.thesis.userservice.dto.RegistrationBearerToken;
import siemieniuk.thesis.userservice.dto.SelfUserResponse;
import siemieniuk.thesis.userservice.dto.UserResponse;
import siemieniuk.thesis.userservice.exception.EntityAlreadyExistsException;
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

	public LoginResponse loginAndGetJwtToken(LoginRequest request) {
		ResponseEntity<String> keycloakLoginResponse = keycloakFeignClient.getToken(mapLoginRequestToKeycloakBody(request));

		var user = userRepository.getByLogin(request.getLogin()).orElseGet(() -> createNew(request.getLogin()));
		LoginResponse response = LoginResponse.fromJson(keycloakLoginResponse.getBody());
		boolean isAdmin = isAdmin(response.getAccessToken());
		response.setUser(SelfUserResponse.fromUser(user, isAdmin));

		return response;
	}

	public void registerNewAccount(RegisterRequest request) {
		ResponseEntity<String> response = keycloakFeignClient.getTokenForRegistration(getRegistrationTokenRequestBody());

		RegistrationBearerToken token = RegistrationBearerToken.fromJson(response.getBody());

		keycloakFeignClient.registerAccount(RegisterKeycloakRequest.fromRequest(request).asJson(), token.getAccessToken());
		createNew(request.getUsername(), request.getFirstName(), request.getLastName());
	}

	private User createNew(String login) {
		return createNew(login, null, null);
	}

	private User createNew(String login, String name, String surname) {
		if (userRepository.existsByLogin(login))
			throw new EntityAlreadyExistsException(String.format("User with name %s already exists.", login));

		User user = new User();
		user.setLogin(login);
		user.setName(name);
		user.setSurname(surname);

		return userRepository.save(user);
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

	private MultiValueMap<String, String> getRegistrationTokenRequestBody() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "password");
		map.add("client_id", environment.getProperty("registration-api.client-id"));
		map.add("username", environment.getProperty("registration-api.username"));
		map.add("password", environment.getProperty("registration-api.password"));

		return map;
	}

	private boolean isAdmin(String accessToken) {
		try {
			var chunks = accessToken.split("\\.");

			Base64.Decoder decoder = Base64.getDecoder();

			String payload = new String(decoder.decode(chunks[1]));
			JsonObject element = JsonParser.parseString(payload).getAsJsonObject();
			var iter = element.get("realm_access").getAsJsonObject().get("roles").getAsJsonArray().iterator();
			while (iter.hasNext()) {
				var role = iter.next().getAsString();
				if ("portal_admin".equals(role))
					return true;
			}
			return false;
		} catch (Exception ignored) {
			return false;
		}
	}
}
