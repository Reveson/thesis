package siemieniuk.thesis.userservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import siemieniuk.thesis.userservice.dto.ChangeInfoRequest;
import siemieniuk.thesis.userservice.dto.UserIdList;
import siemieniuk.thesis.userservice.dto.UserLoginList;
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

	@PostMapping("/many/byLogin")
	public ResponseEntity<List<UserResponse>> getUsersByLogin(@RequestBody UserLoginList userIds) {
		return ResponseEntity.ok(userService.findByLogins(userIds.getUserLogins()).stream()
				.map(UserResponse::fromUser)
				.collect(Collectors.toList()));
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserResponse> changeInfo(
			@PathVariable("userId") long userId,
			@RequestBody ChangeInfoRequest request) {

		return ResponseEntity.ok(UserResponse.fromUser(userService.changeInfo(userId, request)));
	}

	@PostMapping("{userId}/keepAlive")
	public ResponseEntity<?> setUserActive(@RequestHeader("Authorization") String bearerToken, @PathVariable("userId") long userId) {
		userActivityService.setUserActive(userId, bearerToken);

		return ResponseEntity.noContent().build();
	}

	@PostMapping("/block/{userId}")
	public ResponseEntity<?> blockUser(@PathVariable("userId") long userId) {
		userService.block(userId);

		return ResponseEntity.ok().build();
	}
}
