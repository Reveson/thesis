package siemieniuk.thesis.feedservice.service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import siemieniuk.thesis.feedservice.dto.NewFeedRequest;
import siemieniuk.thesis.feedservice.mapper.NewFeedRequestToFeedAuthor;
import siemieniuk.thesis.feedservice.mapper.NewFeedRequestToFeedSubscriber;
import siemieniuk.thesis.feedservice.model.FeedAuthor;
import siemieniuk.thesis.feedservice.model.FeedSubscriber;
import siemieniuk.thesis.feedservice.repository.FeedAuthorRepository;
import siemieniuk.thesis.feedservice.repository.FeedSubscriberRepository;

@Service
@AllArgsConstructor
public class FeedService {

	private final SubscriptionService subscriptionService;
	private final FeedSubscriberRepository feedSubscriberRepository;
	private final FeedAuthorRepository feedAuthorRepository;

	public List<FeedSubscriber> getFeedsBySubscriber(long subscriberId) {
		return feedSubscriberRepository.findAllBySubscriberId(subscriberId);
	}

	public List<FeedAuthor> getFeedsByAuthor(long authorId) {
		return feedAuthorRepository.findAllByAuthorId(authorId);
	}

	public FeedAuthor publishFeed(NewFeedRequest request) {
		UUID timestamp = Uuids.timeBased();
		FeedAuthor feedAuthor = NewFeedRequestToFeedAuthor.map(request, timestamp);

		//TODO async job
		Collection<Long> subscribersList = subscriptionService.getActiveSubscribersList(request.getAuthorId());
		subscribersList.stream().map(id -> NewFeedRequestToFeedSubscriber.map(request, timestamp, id))
				.forEach(feedSubscriberRepository::save);

		return feedAuthorRepository.save(feedAuthor);
	}
}
