package siemieniuk.thesis.feedservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.feedservice.dto.FollowRequest;
import siemieniuk.thesis.feedservice.dto.NewFeedRequest;
import siemieniuk.thesis.feedservice.model.FeedAuthor;
import siemieniuk.thesis.feedservice.model.FeedSubscriber;
import siemieniuk.thesis.feedservice.service.FeedService;
import siemieniuk.thesis.feedservice.service.SubscriptionService;

@RestController
@RequestMapping("/feed")
@AllArgsConstructor
public class FeedController {

	private final FeedService feedService;
	private final SubscriptionService subscriptionService;

	//TODO pagination
	@GetMapping("/subscriber/{subscriberId}")
	public ResponseEntity<List<FeedSubscriber>> getFeedsBySubscriber(
			@PathVariable("subscriberId") long subscriberId) {
		return ResponseEntity.ok(feedService.getFeedsBySubscriber(subscriberId));
	}

	//TODO pagination
	@GetMapping("/author/{authorId}")
	public ResponseEntity<List<FeedAuthor>> getFeedsByAuthor(
			@PathVariable("authorId") long authorId) {
		return ResponseEntity.ok(feedService.getFeedsByAuthor(authorId));
	}

	@PostMapping("/new")
	public ResponseEntity<FeedAuthor> publishFeed(@RequestBody NewFeedRequest request) {
		return ResponseEntity.ok(feedService.publishFeed(request));
	}

	@GetMapping("/followers/{userId}")
	public ResponseEntity<Long> getNumberOfFollowers(
			@PathVariable("userId") long userId) {
		return ResponseEntity.ok(subscriptionService.getNumberOfFollowers(userId));
	}

	@GetMapping("/followed/{userId}")
	public ResponseEntity<Long> getNumberOfUsersFollowed(
			@PathVariable("userId") long userId) {
		return ResponseEntity.ok(subscriptionService.getNumberOfUsersFollowed(userId));
	}

	@GetMapping("/isFollowed/{userId}")
	public ResponseEntity<Boolean> isFollowed(
			@PathVariable("userId") long userId,
			@RequestParam("followedId") long followedId) {
		return ResponseEntity.ok(subscriptionService.isFollowed(userId, followedId));
	}

	@PostMapping("/follow")
	public ResponseEntity<?> follow(
			@RequestBody FollowRequest request) {
		subscriptionService.followUser(request);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/follow")
	public ResponseEntity<?> unfollow(
			@RequestBody FollowRequest request) {
		subscriptionService.unfollowUser(request);
		return ResponseEntity.noContent().build();
	}
}
