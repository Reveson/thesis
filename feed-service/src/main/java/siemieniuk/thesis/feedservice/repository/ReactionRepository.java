package siemieniuk.thesis.feedservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import siemieniuk.thesis.feedservice.model.Comment;
import siemieniuk.thesis.feedservice.model.Reaction;

public interface ReactionRepository extends CassandraRepository<Reaction, Long> {
	int countAllByPostId(UUID postId);
	boolean existsByPostIdAndAuthorId(UUID postId, long authorId);
	boolean deleteByPostIdAndAuthorId(UUID postId, long authorId);
}
