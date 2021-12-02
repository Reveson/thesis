package siemieniuk.thesis.feedservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.feedservice.model.UserCache;
import siemieniuk.thesis.feedservice.repository.UserCacheRepository;
import siemieniuk.thesis.feedservice.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

	private final UserCacheRepository userCacheRepository;
	private final UserRepository userRepository;

	public Optional<UserCache> findById(long userId) {
		Optional<UserCache> optionalUserCache = userCacheRepository.findById(String.valueOf(userId));
		return optionalUserCache.isEmpty() || optionalUserCache.get().getId() == null ? Optional.empty() : optionalUserCache;
	}

	public void save(UserCache userCache) {
		userCacheRepository.save(userCache);
	}

	public boolean canPublish(long userId) {
		return !userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException(String.format("User with id %s not found.", userId)))
				.isBlocked();
	}

}
