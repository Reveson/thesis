package siemieniuk.thesis.feedservice.service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.feedservice.dto.NewFeedRequest;
import siemieniuk.thesis.feedservice.mapper.FeedAuthorToFeedSubscriber;
import siemieniuk.thesis.feedservice.mapper.NewFeedRequestToFeedAuthor;
import siemieniuk.thesis.feedservice.mapper.NewFeedRequestToFeedSubscriber;
import siemieniuk.thesis.feedservice.model.FeedAuthor;
import siemieniuk.thesis.feedservice.model.FeedSubscriber;
import siemieniuk.thesis.feedservice.model.UserCache;
import siemieniuk.thesis.feedservice.repository.FeedAuthorRepository;
import siemieniuk.thesis.feedservice.repository.FeedSubscriberRepository;

@Service
@AllArgsConstructor
public class FeedService {

	private static final long FEED_SYNC_INTERVAL = TimeUnit.DAYS.toMillis(30);

	private final SubscriptionService subscriptionService;
	private final UserCacheService userCacheService;

	private final FeedSubscriberRepository feedSubscriberRepository;
	private final FeedAuthorRepository feedAuthorRepository;

	public List<FeedSubscriber> getFeedsBySubscriber(long subscriberId) {
		return feedSubscriberRepository.findAllBySubscriberId(subscriberId);
	}

	public List<FeedAuthor> getFeedsByAuthor(long authorId, long timeFromUnixTime, long timeToUnixTime) {
		UUID uuidTimeFrom = Uuids.endOf(timeFromUnixTime);
		UUID uuidTimeTo = Uuids.endOf(timeToUnixTime);
		return feedAuthorRepository.findAllByAuthorIdAndTimestampBetween(authorId, uuidTimeFrom, uuidTimeTo);
	}

	public FeedAuthor publishFeed(NewFeedRequest request) {
		UUID timestamp = Uuids.timeBased();
		FeedAuthor feedAuthor = feedAuthorRepository.save(NewFeedRequestToFeedAuthor.map(request, timestamp));
		publishFeedForSubscribers(request, timestamp);

		return feedAuthor;
	}

	@Async
	protected void publishFeedForSubscribers(NewFeedRequest request,
			UUID timestamp) {
		Collection<Long> subscribersList = subscriptionService.getActiveSubscribersList(request.getAuthorId());
		Stream.concat(subscribersList.stream(), Stream.of(request.getAuthorId()))
				.map(id -> NewFeedRequestToFeedSubscriber.map(request, timestamp, id))
				.forEach(feedSubscriberRepository::save);
	}

	@Async
	public void denormalizeFeedsForUser(long userId, boolean forward) {
		if (forward)
			denormalizeFeedsForUserForwards(userId);
		else
			denormalizeFeedsForUserBackwards(userId);
	}

	private void denormalizeFeedsForUserBackwards(long userId) {
		long now = System.currentTimeMillis();
		UserCache userCache = userCacheService.findById(userId)
				.orElseGet(() -> UserCache.builder().id(userId).syncHead(now).syncTail(now).build());
		long timeFrom = userCache.getSyncTail() - FEED_SYNC_INTERVAL;
		long timeTo = userCache.getSyncTail();

		subscriptionService.getUsersFollowedBy(userId).stream()
				.flatMap(authorId -> getFeedsByAuthor(authorId, timeFrom, timeTo).stream())
				.map(feedAuthor -> FeedAuthorToFeedSubscriber.map(feedAuthor, userId))
				.forEach(feedSubscriberRepository::save);

		userCacheService.save(userCache.toBuilder().syncTail(timeFrom).build());
	}

	private void denormalizeFeedsForUserForwards(long userId) {
		long now = System.currentTimeMillis();
		UserCache userCache = userCacheService.findById(userId)
				.orElseGet(() -> UserCache.builder().id(userId).syncHead(now - FEED_SYNC_INTERVAL).syncTail(now - FEED_SYNC_INTERVAL).build());

		subscriptionService.getUsersFollowedBy(userId).stream()
				.flatMap(authorId -> getFeedsByAuthor(authorId, userCache.getSyncHead(), now).stream())
				.map(feedAuthor -> FeedAuthorToFeedSubscriber.map(feedAuthor, userId))
				.forEach(feedSubscriberRepository::save);

		userCacheService.save(userCache.toBuilder().syncHead(now).build());
	}
}
