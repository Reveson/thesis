package siemieniuk.thesis.feedservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import siemieniuk.thesis.feedservice.model.FeedAuthor;

public interface FeedAuthorRepository extends CassandraRepository<FeedAuthor, Long> {
	List<FeedAuthor> findAllByAuthorIdAndTimestampBetween(long authorId, UUID timeFrom, UUID timeTo);

	@Query("delete from feed_author where author_id = :authorId and timestamp = :timestamp")
	void deleteByAuthorIdAndTimestamp(long authorId, UUID timestamp);
}
