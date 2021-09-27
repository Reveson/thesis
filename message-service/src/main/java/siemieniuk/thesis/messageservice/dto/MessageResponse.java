package siemieniuk.thesis.messageservice.dto;

import static siemieniuk.thesis.messageservice.util.UuidUtils.toUnixTime;

import lombok.Data;
import siemieniuk.thesis.messageservice.model.Message;

@Data
public class MessageResponse {
	private long authorId;
	private long timestamp;
	private String content;

	public static MessageResponse fromMessage(Message message) {
		MessageResponse response = new MessageResponse();
		response.setAuthorId(message.getAuthorId());
		response.setTimestamp(toUnixTime(message.getTimestamp()));
		response.setContent(message.getContent());

		return response;
	}
}
