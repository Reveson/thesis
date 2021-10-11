package siemieniuk.thesis.feedservice.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.feedservice.model.Reaction;
import siemieniuk.thesis.feedservice.repository.ReactionRepository;

@Service
@AllArgsConstructor
public class ReactionService {

	final ReactionRepository reactionRepository;

	public int count(UUID postId) {
		return reactionRepository.countAllByPostId(postId);
	}

	public boolean reactionAlreadyAdded(UUID postId, long userId) {
		return reactionRepository.existsByPostIdAndAuthorId(postId, userId);
	}

	public void removeReaction(UUID postId, long userId) {
		reactionRepository.deleteByPostIdAndAuthorId(postId, userId);
	}

	public void addReaction(UUID postId, long userId) {
		Reaction reaction = new Reaction();
		reaction.setPostId(postId);
		reaction.setAuthorId(userId);
		reaction.setTimestamp(Uuids.timeBased());
		reactionRepository.save(reaction);
	}
}
