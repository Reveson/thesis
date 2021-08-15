package siemieniuk.thesis.notificationservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.notificationservice.dto.NewNotificationRequest;
import siemieniuk.thesis.notificationservice.model.Notification;
import siemieniuk.thesis.notificationservice.service.NotificationService;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	//TODO pagination
	@GetMapping("/{userId}")
	public ResponseEntity<List<Notification>> getNotifications(
			@PathVariable("userId") long userId) {
		var notifications = notificationService.getNotifications(userId);
		notificationService.markAsRead(notifications);
		return ResponseEntity.ok(notifications);
	}

	@PostMapping("/new")
	public ResponseEntity<Notification> createNotification(@RequestBody NewNotificationRequest request) {
		return ResponseEntity.ok(notificationService.createNotification(request));
	}
}
