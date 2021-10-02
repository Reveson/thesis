package siemieniuk.thesis.notificationservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.notificationservice.dto.NewNotificationRequest;
import siemieniuk.thesis.notificationservice.dto.NotificationResponse;
import siemieniuk.thesis.notificationservice.model.Notification;
import siemieniuk.thesis.notificationservice.service.NotificationService;
import siemieniuk.thesis.notificationservice.service.UserCacheService;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;
	private final UserCacheService userCacheService;

	//TODO pagination
	@GetMapping("/notifications/{userId}/list")
	public ResponseEntity<List<NotificationResponse>> getNotifications(
			@PathVariable("userId") long userId) {
		var notifications = notificationService.getNotifications(userId);
		notificationService.markAsRead(notifications);
		userCacheService.setNotificationsById(userId, 0);
		return ResponseEntity.ok(notifications.stream()
				.map(NotificationResponse::asNotificationResponse).collect(Collectors.toList()));
	}

	@PostMapping("notifications/new")
	public ResponseEntity<Notification> createNotification(@RequestBody NewNotificationRequest request) {
		var notification = notificationService.createNotification(request);
		userCacheService.incrementNotificationsById(request.getUserId(), 1);
		return ResponseEntity.ok(notification);
	}

	@GetMapping("/notifications/{userId}/count")
	public ResponseEntity<Long> getNumberOfNotifications(@PathVariable("userId") long userId) {
		return ResponseEntity.ok(userCacheService.getNumberOfNotifications(userId));
	}

	@GetMapping("/messages/{userId}/count")
	public ResponseEntity<Long> getNumberOfUnreadMessages(@PathVariable("userId") long userId) {
		return ResponseEntity.ok(userCacheService.getNumberOfUnreadMessages(userId));
	}
}
