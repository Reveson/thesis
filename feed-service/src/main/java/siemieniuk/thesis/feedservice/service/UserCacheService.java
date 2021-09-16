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
		return userCacheRepository.findById(String.valueOf(userId));
	}

	public void save(UserCache userCache) {
		userCacheRepository.save(userCache);
	}
}
