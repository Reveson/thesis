package siemieniuk.thesis.feedservice.mapper;

import java.util.UUID;

import siemieniuk.thesis.feedservice.dto.NewFeedRequest;
import siemieniuk.thesis.feedservice.model.FeedAuthor;

public class NewFeedRequestToFeedAuthor {

	public static FeedAuthor map(NewFeedRequest request, UUID timestamp) {
		FeedAuthor feedAuthor = new FeedAuthor();
		feedAuthor.setAuthorId(request.getAuthorId());
		feedAuthor.setContent(request.getContent());
		feedAuthor.setPhoto(request.getPhotoUrl());
		feedAuthor.setTimestamp(timestamp);

		return feedAuthor;
	}
}
