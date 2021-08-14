package siemieniuk.thesis.feedservice.mapper;

import java.util.UUID;

import siemieniuk.thesis.feedservice.dto.NewFeedRequest;
import siemieniuk.thesis.feedservice.model.FeedAuthor;
import siemieniuk.thesis.feedservice.model.FeedSubscriber;

public class NewFeedRequestToFeedSubscriber {

	public static FeedSubscriber map(NewFeedRequest request, UUID timestamp, long subscriberId) {
		FeedSubscriber feedSubscriber = new FeedSubscriber();
		feedSubscriber.setSubscriberId(subscriberId);
		feedSubscriber.setAuthorName(request.getAuthorName());
		feedSubscriber.setAuthorId(request.getAuthorId());
		feedSubscriber.setContent(request.getContent());
		feedSubscriber.setPhoto(request.getPhotoUrl());
		feedSubscriber.setTimestamp(timestamp);

		return feedSubscriber;
	}
}
