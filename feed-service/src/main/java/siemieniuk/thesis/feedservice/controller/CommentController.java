package siemieniuk.thesis.feedservice.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.feedservice.dto.CommentResponse;
import siemieniuk.thesis.feedservice.dto.NewCommentRequest;
import siemieniuk.thesis.feedservice.service.CommentService;
import siemieniuk.thesis.feedservice.service.UserService;

@RestController
@RequestMapping("/feed/{feedId}/comment")
@AllArgsConstructor
public class CommentController {

	private final CommentService commentService;
	private final UserService userService;

	//TODO pagination
	@GetMapping
	public ResponseEntity<List<CommentResponse>> getComments(
			@PathVariable("feedId") String feedId) {
		UUID postId = getUUID(feedId);
		return ResponseEntity.ok(commentService.getComments(postId).stream()
		.map(CommentResponse::asCommentResponse).collect(Collectors.toList()));
	}

	@GetMapping("/count")
	public ResponseEntity<Integer> count(
			@PathVariable("feedId") String feedId) {
		UUID postId = getUUID(feedId);
		return ResponseEntity.ok(commentService.count(postId));
	}

	@PostMapping("/new")
	public ResponseEntity<CommentResponse> publishComment(@PathVariable("feedId") String feedId,
			@RequestBody NewCommentRequest request) {
		if (!userService.canPublish(request.getAuthorId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is blocked.");
		UUID postId = getUUID(feedId);
		return ResponseEntity.ok(CommentResponse.asCommentResponse(commentService.publishComment(postId, request)));
	}

	private UUID getUUID(String id) {
		//TODO format validation
		return UUID.fromString(id);
	}
}
