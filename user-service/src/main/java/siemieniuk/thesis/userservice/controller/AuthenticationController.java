package siemieniuk.thesis.userservice.controller;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.userservice.dto.LoginRequest;
import siemieniuk.thesis.userservice.dto.LoginResponse;
import siemieniuk.thesis.userservice.dto.RegisterRequest;
import siemieniuk.thesis.userservice.service.AuthenticationService;
import siemieniuk.thesis.userservice.service.UserActivityService;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	private final UserActivityService activityService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		var response = authenticationService.loginAndGetJwtToken(loginRequest);
		activityService.setUserActive(response.getUser().getId(), "Bearer " + response.getAccessToken());
		return ResponseEntity.status(OK).body(response);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {

		authenticationService.registerNewAccount(registerRequest);
		return ResponseEntity.noContent().build();

	}
}
