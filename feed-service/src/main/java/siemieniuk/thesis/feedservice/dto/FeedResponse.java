package siemieniuk.thesis.feedservice.dto;

import static siemieniuk.thesis.feedservice.util.UuidUtils.toUnixTime;

import lombok.Data;
import siemieniuk.thesis.feedservice.model.Feed;
import siemieniuk.thesis.feedservice.model.FeedAuthor;
import siemieniuk.thesis.feedservice.model.FeedSubscriber;

@Data
public class FeedResponse {

	private String id;
	private long userId;
	private long timestamp;
	private String content;
	private String photo;

	public static FeedResponse asFeedResponse(Feed feed) {
		FeedResponse response = new FeedResponse();
		response.setId(feed.getTimestamp().toString());
		response.setUserId(feed.getAuthorId());
		response.setTimestamp(toUnixTime(feed.getTimestamp()));
		response.setContent(feed.getContent());
		response.setPhoto(feed.getPhoto());

		return response;
	}
}
