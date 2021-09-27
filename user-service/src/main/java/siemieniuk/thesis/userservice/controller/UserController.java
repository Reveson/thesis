package siemieniuk.thesis.userservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import siemieniuk.thesis.userservice.dto.ChangeInfoRequest;
import siemieniuk.thesis.userservice.dto.UserIdList;
import siemieniuk.thesis.userservice.dto.UserResponse;
import siemieniuk.thesis.userservice.service.UserActivityService;
import siemieniuk.thesis.userservice.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserActivityService userActivityService;

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> getUser(@PathVariable("userId") long userId) {
		return ResponseEntity.ok(UserResponse.fromUser(userService.findById(userId)));
	}

	@PostMapping("/many")
	public ResponseEntity<List<UserResponse>> getUsers(@RequestBody UserIdList userIds) {
		return ResponseEntity.ok(userService.findByIds(userIds.getUserIds()).stream()
				.map(UserResponse::fromUser)
				.collect(Collectors.toList()));
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserResponse> changeInfo(
			@PathVariable("userId") long userId,
			@RequestBody ChangeInfoRequest request) {

		return ResponseEntity.ok(UserResponse.fromUser(userService.changeInfo(userId, request)));
	}

	//TODO not to be visible in public API
	@PostMapping("{userId}/keepAlive")
	public ResponseEntity<?> setUserActive(@PathVariable("userId") long userId) {
		userActivityService.setUserActive(userId);

		return ResponseEntity.noContent().build();
	}
}
