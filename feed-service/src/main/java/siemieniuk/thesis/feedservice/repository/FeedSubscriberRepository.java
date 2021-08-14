package siemieniuk.thesis.feedservice.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;

import siemieniuk.thesis.feedservice.model.FeedSubscriber;

public interface FeedSubscriberRepository extends CassandraRepository<FeedSubscriber, Long> {

	List<FeedSubscriber> findAllBySubscriberId(long subscriberId); //TODO limit
}
