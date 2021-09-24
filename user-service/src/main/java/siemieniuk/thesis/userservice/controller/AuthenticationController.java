package siemieniuk.thesis.userservice.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.userservice.dto.LoginRequest;
import siemieniuk.thesis.userservice.model.User;
import siemieniuk.thesis.userservice.service.AuthenticationService;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.status(OK).
				body(authenticationService.loginAndGetJwtToken(loginRequest));
	}

	@PostMapping("/register")
	public ResponseEntity<User> addNewUser(@RequestBody LoginRequest request) {
		return ResponseEntity.status(CREATED).
				body(authenticationService.createNew(request));
	}
}
