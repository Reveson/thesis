package siemieniuk.thesis.feedservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import siemieniuk.thesis.feedservice.model.Comment;
import siemieniuk.thesis.feedservice.model.FeedAuthor;

public interface CommentRepository extends CassandraRepository<Comment, Long> {
	List<Comment> findAllByPostId(UUID postId); //TODO limit
}
