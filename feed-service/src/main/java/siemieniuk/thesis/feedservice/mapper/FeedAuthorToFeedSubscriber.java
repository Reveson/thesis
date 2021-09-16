package siemieniuk.thesis.feedservice.mapper;

import siemieniuk.thesis.feedservice.model.FeedAuthor;
import siemieniuk.thesis.feedservice.model.FeedSubscriber;

public class FeedAuthorToFeedSubscriber {

	public static FeedSubscriber map(FeedAuthor feedAuthor, long subscriberId) {
		FeedSubscriber feedSubscriber = new FeedSubscriber();
		feedSubscriber.setSubscriberId(subscriberId);
//		feedSubscriber.setAuthorName(feeA); //TODO
		feedSubscriber.setAuthorId(feedAuthor.getAuthorId());
		feedSubscriber.setContent(feedAuthor.getContent());
		feedSubscriber.setPhoto(feedAuthor.getPhoto());
		feedSubscriber.setTimestamp(feedAuthor.getTimestamp());

		return feedSubscriber;
	}
}
