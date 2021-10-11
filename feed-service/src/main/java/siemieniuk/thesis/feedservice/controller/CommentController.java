package siemieniuk.thesis.feedservice.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.feedservice.dto.CommentResponse;
import siemieniuk.thesis.feedservice.dto.NewCommentRequest;
import siemieniuk.thesis.feedservice.service.CommentService;

@RestController
@RequestMapping("/feed/{feedId}")
@AllArgsConstructor
public class CommentController {

	private final CommentService commentService;

	//TODO pagination
	@GetMapping("/comment")
	public ResponseEntity<List<CommentResponse>> getComments(
			@PathVariable("feedId") String feedId) {
		UUID postId = getUUID(feedId);
		return ResponseEntity.ok(commentService.getComments(postId).stream()
		.map(CommentResponse::asCommentResponse).collect(Collectors.toList()));
	}

	@PostMapping("/comment/new")
	public ResponseEntity<CommentResponse> publishComment(@PathVariable("feedId") String feedId,
			@RequestBody NewCommentRequest request) {
		UUID postId = getUUID(feedId);
		return ResponseEntity.ok(CommentResponse.asCommentResponse(commentService.publishComment(postId, request)));
	}

	private UUID getUUID(String id) {
		//TODO format validation
		return UUID.fromString(id);
	}
}
