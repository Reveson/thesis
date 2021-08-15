package siemieniuk.thesis.messageservice.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import siemieniuk.thesis.messageservice.model.Message;
import siemieniuk.thesis.messageservice.model.MessageSecondUserIdProjection;

public interface MessageRepository extends CassandraRepository<Message, Long> {
	List<Message> findAllByUserIdAndSecondUserId(long userId, long secondUserId); //TODO limit
	
	@Query("select distinct user_id, second_user_id from message where user_id = :userId")
	List<MessageSecondUserIdProjection> findDistinctByUserId(long userId);
}
