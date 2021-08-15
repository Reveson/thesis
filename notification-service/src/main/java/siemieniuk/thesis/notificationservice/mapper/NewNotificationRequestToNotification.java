package siemieniuk.thesis.notificationservice.mapper;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import siemieniuk.thesis.notificationservice.dto.NewNotificationRequest;
import siemieniuk.thesis.notificationservice.model.Notification;

public class NewNotificationRequestToNotification {

	public static Notification map(NewNotificationRequest request) {
		Notification notification = new Notification();
		notification.setUserId(request.getUserId());
		notification.setContent(request.getContent());
		notification.setTimestamp(Uuids.timeBased());
		notification.setRead(false);

		return notification;
	}
}
