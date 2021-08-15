package siemieniuk.thesis.messageservice.dto;

import lombok.Data;

@Data
public class NewMessageRequest {
	private long recipientId;
	private String content;
}
