package siemieniuk.thesis.userservice.dto;

import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RegisterKeycloakRequest {
	private String username;
	private String firstName;
	private String lastName;
	private boolean enabled = true;
	private List<Credentials> credentials;

	@Data
	@AllArgsConstructor
	static class Credentials {
		private String type;
		private String value;
	}

	public static RegisterKeycloakRequest fromRequest(RegisterRequest request) {
		RegisterKeycloakRequest keycloakRequest = new RegisterKeycloakRequest();
		keycloakRequest.setUsername(request.getUsername());
		keycloakRequest.setFirstName(request.getFirstName());
		keycloakRequest.setLastName(request.getLastName());
		keycloakRequest.setCredentials(Collections.singletonList(new Credentials("password", request.getPassword())));

		return keycloakRequest;
	}

	public String asJson() {
		return new Gson().toJson(this);
	}
}
