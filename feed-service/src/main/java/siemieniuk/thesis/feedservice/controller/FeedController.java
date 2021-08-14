package siemieniuk.thesis.feedservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import siemieniuk.thesis.feedservice.dto.NewFeedRequest;
import siemieniuk.thesis.feedservice.model.FeedAuthor;
import siemieniuk.thesis.feedservice.model.FeedSubscriber;
import siemieniuk.thesis.feedservice.repository.FeedSubscriberRepository;
import siemieniuk.thesis.feedservice.service.FeedService;

@RestController
@RequestMapping("/feed")
@AllArgsConstructor
public class FeedController {

	private final FeedService feedService;

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

	//TODO pagination
	@PostMapping("/new")
	public ResponseEntity<FeedAuthor> publishFeed(@RequestBody NewFeedRequest request) {
		return ResponseEntity.ok(feedService.publishFeed(request));
	}


}
