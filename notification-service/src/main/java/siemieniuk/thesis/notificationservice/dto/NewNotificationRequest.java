package siemieniuk.thesis.notificationservice.dto;

import lombok.Data;

@Data
public class NewNotificationRequest {

	private long userId;
	String content;

}
