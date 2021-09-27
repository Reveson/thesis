package siemieniuk.thesis.messageservice.controller;

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
import siemieniuk.thesis.messageservice.dto.MessageResponse;
import siemieniuk.thesis.messageservice.dto.NewMessageRequest;
import siemieniuk.thesis.messageservice.service.MessageService;
import siemieniuk.thesis.messageservice.service.UserCacheService;

@RestController
@RequestMapping("/message/{userId}")
@AllArgsConstructor
public class MessageController {

	private final MessageService messageService;
	private final UserCacheService userCacheService;


	//TODO pagination
	@GetMapping("/get/{recipientId}")
	public ResponseEntity<List<MessageResponse>> getMessages(
			@PathVariable("userId") long userId,
			@PathVariable("recipientId") long recipientId) {
		var messages = messageService.getMessages(userId, recipientId);
		userCacheService.setMessagesById(userId, 0);
		return ResponseEntity.ok(messages.stream()
				.map(MessageResponse::fromMessage).collect(Collectors.toList()));
	}

	//TODO pagination
	@GetMapping("/chats")
	public ResponseEntity<List<Long>> getUserIdsFromChats(@PathVariable("userId") long userId) {
		return ResponseEntity.ok(messageService.getUserIdsWithExistingMessages(userId));
	}

	@PostMapping("/new")
	public ResponseEntity<MessageResponse> sendMessage(
			@PathVariable("userId") long userId,
			@RequestBody NewMessageRequest request) {
		var message = messageService.sendMessage(userId, request);
		userCacheService.incrementMessagesById(request.getRecipientId(), 1);
		return ResponseEntity.ok(MessageResponse.fromMessage(message));
	}
}
