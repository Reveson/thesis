package siemieniuk.thesis.feedservice.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.feedservice.dto.ReactionResponse;
import siemieniuk.thesis.feedservice.service.ReactionService;

@RestController
@RequestMapping("/feed/{feedId}/reaction")
@AllArgsConstructor
public class ReactionController {

	private final ReactionService reactionService;

	@GetMapping("/{userId}")
	public ResponseEntity<ReactionResponse> getReactions(
			@PathVariable("feedId") String feedId,
			@PathVariable("userId") long userId) {
		UUID postId = getUUID(feedId);
		int count = reactionService.count(postId);
		boolean alreadyReacted = reactionService.reactionAlreadyAdded(postId, userId);
		return ResponseEntity.ok(new ReactionResponse(count, alreadyReacted));
	}

	@PostMapping("/{userId}/add")
	public ResponseEntity<ReactionResponse> addReaction(
			@PathVariable("feedId") String feedId,
			@PathVariable("userId") long userId) {
		UUID postId = getUUID(feedId);
		reactionService.addReaction(postId, userId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{userId}/remove")
	public ResponseEntity<ReactionResponse> removeReaction(
			@PathVariable("feedId") String feedId,
			@PathVariable("userId") long userId) {
		UUID postId = getUUID(feedId);
		reactionService.removeReaction(postId, userId);
		return ResponseEntity.noContent().build();
	}

	private UUID getUUID(String id) {
		//TODO format validation
		return UUID.fromString(id);
	}
}
