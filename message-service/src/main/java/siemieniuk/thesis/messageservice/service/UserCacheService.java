package siemieniuk.thesis.messageservice.service;

import static siemieniuk.thesis.messageservice.util.RedisKeyMapper.asRedisKey;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.messageservice.model.UserCache;

@Service
@AllArgsConstructor
public class UserCacheService {
	private final RedisTemplate<String, String> redisTemplate;
	private static final String KEY_BASE = UserCache.KEY;

	public Long incrementMessagesById(long id, long incrementBy) {
		return incrementById(id, UserCache.UNREAD_MESSAGES, incrementBy);
	}

	public void setMessagesById(long id, long value) {
		setById(id, UserCache.UNREAD_MESSAGES, value);
	}

	private Long incrementById(long id, String field, long incrementBy) {
		String key = asRedisKey(KEY_BASE, id);
		return redisTemplate.opsForHash().increment(key, field, incrementBy);
	}

	private void setById(long id, String field, long value) {
		String key = asRedisKey(KEY_BASE, id);
		redisTemplate.opsForHash().put(key, field, String.valueOf(value));
	}
}
