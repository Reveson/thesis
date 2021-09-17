package siemieniuk.thesis.feedservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.feedservice.model.UserCache;
import siemieniuk.thesis.feedservice.repository.UserCacheRepository;

@Service
@AllArgsConstructor
public class UserCacheService {
	private final UserCacheRepository userCacheRepository;

	public Optional<UserCache> findById(long userId) {
		Optional<UserCache> optionalUserCache = userCacheRepository.findById(String.valueOf(userId));
		return optionalUserCache.isEmpty() || optionalUserCache.get().getId() == null ? Optional.empty() : optionalUserCache;
	}

	public void save(UserCache userCache) {
		userCacheRepository.save(userCache);
	}
}
