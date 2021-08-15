package siemieniuk.thesis.messageservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.messageservice.dto.NewMessageRequest;
import siemieniuk.thesis.messageservice.model.Message;
import siemieniuk.thesis.messageservice.repository.MessageRepository;
import siemieniuk.thesis.messageservice.service.MessageService;

@RestController
@RequestMapping("/message/{userId}")
@AllArgsConstructor
public class MessageController {

	private final MessageService messageService;

	//TODO pagination
	@GetMapping("/get/{recipientId}")
	public ResponseEntity<List<Message>> getMessages(
			@PathVariable("userId") long userId,
			@PathVariable("recipientId") long recipientId) {
		return ResponseEntity.ok(messageService.getMessages(userId, recipientId));
	}

	//TODO pagination
	@GetMapping("/chats")
	public ResponseEntity<List<Long>> getUserIdsFromChats(@PathVariable("userId") long userId) {
		return ResponseEntity.ok(messageService.getUserIdsWithExistingMessages(userId));
	}

	@PostMapping("/new")
	public ResponseEntity<Message> sendMessage(
			@PathVariable("userId") long userId,
			@RequestBody NewMessageRequest request) {
		return ResponseEntity.ok(messageService.sendMessage(userId, request));
	}
}
