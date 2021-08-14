package siemieniuk.thesis.feedservice.dto;

import lombok.Data;

@Data
public class NewCommentRequest {

	private long authorId;
	private String authorName;
	private String content;
}
