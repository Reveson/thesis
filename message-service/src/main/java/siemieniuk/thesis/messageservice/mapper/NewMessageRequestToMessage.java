package siemieniuk.thesis.messageservice.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import siemieniuk.thesis.messageservice.dto.NewMessageRequest;
import siemieniuk.thesis.messageservice.model.Message;

public class NewMessageRequestToMessage {
	private final UUID timestamp = Uuids.timeBased();

	public Message map(long authorId, NewMessageRequest request, boolean invertUsers) {
		long firstUser = invertUsers ? request.getRecipientId() : authorId;
		long secondUser = invertUsers ? authorId : request.getRecipientId();
		Message message = new Message();
		message.setUserId(firstUser);
		message.setSecondUserId(secondUser);
		message.setTimestamp(timestamp);
		message.setAuthorId(authorId);
		message.setContent(request.getContent());

		return message;
	}
}
