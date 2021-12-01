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
@RequestMapping("/admin")
@RequiredArgsConstructor
public class TestController {

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("Test here");
	}

}
