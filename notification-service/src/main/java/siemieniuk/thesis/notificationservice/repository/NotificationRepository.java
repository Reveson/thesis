package siemieniuk.thesis.notificationservice.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;

import siemieniuk.thesis.notificationservice.model.Notification;

public interface NotificationRepository extends CassandraRepository<Notification, Long> {
	List<Notification> findAllByUserId(long userId); //TODO limit
}
