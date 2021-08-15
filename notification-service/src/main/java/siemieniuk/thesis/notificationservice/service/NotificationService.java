package siemieniuk.thesis.notificationservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.notificationservice.dto.NewNotificationRequest;
import siemieniuk.thesis.notificationservice.mapper.NewNotificationRequestToNotification;
import siemieniuk.thesis.notificationservice.model.Notification;
import siemieniuk.thesis.notificationservice.repository.NotificationRepository;

@Service
@AllArgsConstructor
public class NotificationService {

	final NotificationRepository notificationRepository;

	public List<Notification> getNotifications(long userId) {
		return notificationRepository.findAllByUserId(userId);
	}

	public Notification createNotification(NewNotificationRequest request) {
		return notificationRepository.save(NewNotificationRequestToNotification.map(request));
	}

	public void markAsRead(List<Notification> notifications) {
		notificationRepository.saveAll(
				notifications.stream()
						.filter(n -> !n.isRead())
						.map(n -> n.toBuilder().isRead(true).build())
						.collect(Collectors.toList()));
	}

}
