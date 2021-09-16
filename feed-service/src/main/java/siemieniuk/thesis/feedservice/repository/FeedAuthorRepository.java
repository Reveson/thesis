package siemieniuk.thesis.feedservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import siemieniuk.thesis.feedservice.model.FeedAuthor;

public interface FeedAuthorRepository extends CassandraRepository<FeedAuthor, Long> {
	List<FeedAuthor> findAllByAuthorIdAndTimestampBetween(long authorId, UUID timeFrom, UUID timeTo);
}
