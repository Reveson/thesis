package siemieniuk.thesis.messageservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.messageservice.dto.NewMessageRequest;
import siemieniuk.thesis.messageservice.mapper.NewMessageRequestToMessage;
import siemieniuk.thesis.messageservice.model.Message;
import siemieniuk.thesis.messageservice.model.MessageSecondUserIdProjection;
import siemieniuk.thesis.messageservice.repository.MessageRepository;

@Service
@AllArgsConstructor
public class MessageService {

	final MessageRepository messageRepository;

	public List<Message> getMessages(long userId, long recipientId) {
		return messageRepository.findAllByUserIdAndSecondUserId(userId, recipientId);
	}

	public Message sendMessage(long authorId, NewMessageRequest request) {
		NewMessageRequestToMessage mapper = new NewMessageRequestToMessage();
		Message message = mapper.map(authorId, request, false);
		Message denormalizedMessage = mapper.map(authorId, request, true);

		messageRepository.saveAll(Arrays.asList(message, denormalizedMessage));

		return message;
	}

	public List<Long> getUserIdsWithExistingMessages(long userId) {
		return messageRepository.findDistinctByUserId(userId).stream()
				.map(MessageSecondUserIdProjection::getSecondUserId)
				.collect(Collectors.toList());
	}
}
