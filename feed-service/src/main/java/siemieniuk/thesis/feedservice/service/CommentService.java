package siemieniuk.thesis.feedservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.feedservice.dto.NewCommentRequest;
import siemieniuk.thesis.feedservice.mapper.NewCommentRequestToComment;
import siemieniuk.thesis.feedservice.model.Comment;
import siemieniuk.thesis.feedservice.repository.CommentRepository;

@Service
@AllArgsConstructor
public class CommentService {

	final CommentRepository commentRepository;

	public List<Comment> getComments(UUID postId) {
		return commentRepository.findAllByPostId(postId);
	}

	public int count(UUID postId) {
		return commentRepository.countAllByPostId(postId);
	}

	public Comment publishComment(UUID postId, NewCommentRequest request) {
		Comment comment = NewCommentRequestToComment.map(postId, request);
		return commentRepository.save(comment);
	}
}
