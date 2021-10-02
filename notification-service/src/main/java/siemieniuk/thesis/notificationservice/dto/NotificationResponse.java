package siemieniuk.thesis.notificationservice.dto;

import static siemieniuk.thesis.notificationservice.util.UuidUtils.toUnixTime;

import lombok.Data;
import siemieniuk.thesis.notificationservice.model.Notification;

@Data
public class NotificationResponse {
	private long timestamp;
	private String content;
	private boolean isRead;

	public static NotificationResponse asNotificationResponse(Notification notification) {
		NotificationResponse response = new NotificationResponse();
		response.setTimestamp(toUnixTime(notification.getTimestamp()));
		response.setContent(notification.getContent());
		response.setRead(notification.isRead());

		return response;
	}
}
