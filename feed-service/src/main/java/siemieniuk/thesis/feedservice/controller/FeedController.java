package siemieniuk.thesis.feedservice.controller;

import static siemieniuk.thesis.feedservice.dto.FeedResponse.asFeedResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.feedservice.dto.FeedResponse;
import siemieniuk.thesis.feedservice.dto.FollowRequest;
import siemieniuk.thesis.feedservice.dto.NewFeedRequest;
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
	public ResponseEntity<List<FeedResponse>> getFeedsBySubscriber(
			@PathVariable("subscriberId") long subscriberId) {
		return ResponseEntity.ok(feedService.getFeedsBySubscriber(subscriberId).stream()
		.map(FeedResponse::asFeedResponse).collect(Collectors.toList()));
	}

	//TODO pagination
	@GetMapping("/author/{authorId}")
	public ResponseEntity<List<FeedResponse>> getFeedsByAuthor(
			@PathVariable("authorId") long authorId) {
		//TODO should be as pagination from frontend
		long now = System.currentTimeMillis();
		long defaultTimeFrom = now - TimeUnit.DAYS.toMillis(30);
		return ResponseEntity.ok(feedService.getFeedsByAuthor(authorId, defaultTimeFrom, now)
				.stream().map(FeedResponse::asFeedResponse).collect(Collectors.toList()));
	}

	@PostMapping("/new")
	public ResponseEntity<FeedResponse> publishFeed(@RequestBody NewFeedRequest request) {
		return ResponseEntity.ok(asFeedResponse(feedService.publishFeed(request)));
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

	@PostMapping("/unfollow")
	public ResponseEntity<?> unfollow(
			@RequestBody FollowRequest request) {
		subscriptionService.unfollowUser(request);
		return ResponseEntity.noContent().build();
	}

	//TODO shouldn't be visible in public API
	@PostMapping("/subscriber/{subscriberId}/denormalizeFeeds")
	public ResponseEntity<?> denormalizeFeeds(
			@PathVariable("subscriberId") long subscriberId) {
		feedService.denormalizeFeedsForUser(subscriberId, true);
		return ResponseEntity.noContent().build();
	}
}
