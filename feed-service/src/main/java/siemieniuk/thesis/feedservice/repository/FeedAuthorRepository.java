package siemieniuk.thesis.feedservice.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;

import siemieniuk.thesis.feedservice.model.FeedAuthor;
import siemieniuk.thesis.feedservice.model.FeedSubscriber;

public interface FeedAuthorRepository extends CassandraRepository<FeedAuthor, Long> {
	List<FeedAuthor> findAllByAuthorId(long authorId); //TODO limit
}
