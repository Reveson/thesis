package siemieniuk.thesis.feedservice.dto;

import static siemieniuk.thesis.feedservice.util.UuidUtils.toUnixTime;

import lombok.Data;
import siemieniuk.thesis.feedservice.model.Comment;
import siemieniuk.thesis.feedservice.model.Feed;

@Data
public class CommentResponse {

	private long userId;
	private long timestamp;
	private String content;
	private String photo;

	public static CommentResponse asCommentResponse(Comment comment) {
		CommentResponse response = new CommentResponse();
		response.setUserId(comment.getAuthorId());
		response.setTimestamp(toUnixTime(comment.getTimestamp()));
		response.setContent(comment.getContent());

		return response;
	}
}
