package siemieniuk.thesis.feedservice.mapper;

import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import siemieniuk.thesis.feedservice.dto.NewCommentRequest;
import siemieniuk.thesis.feedservice.model.Comment;

public class NewCommentRequestToComment {

	public static Comment map(UUID postId, NewCommentRequest request) {
		Comment comment = new Comment();
		UUID timestamp = Uuids.timeBased();

		comment.setAuthorId(request.getAuthorId());
		comment.setAuthorName(request.getAuthorName());
		comment.setPostId(postId);
		comment.setContent(request.getContent());
		comment.setTimestamp(timestamp);

		return comment;
	}
}
