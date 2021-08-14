package siemieniuk.thesis.feedservice.dto;

import lombok.Data;

@Data
public class NewFeedRequest {
	long authorId;
	String authorName;
	String content;
	String photoUrl;
}
