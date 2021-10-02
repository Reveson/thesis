package siemieniuk.thesis.notificationservice.service;

import static siemieniuk.thesis.notificationservice.util.RedisKeyMapper.asRedisKey;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.notificationservice.model.UserCache;

@Service
@AllArgsConstructor
public class UserCacheService {
	private final RedisTemplate<String, String> redisTemplate;
	private static final String KEY_BASE = UserCache.KEY;

	public Long incrementNotificationsById(long id, long incrementBy) {
		return incrementById(id, UserCache.UNREAD_NOTIFICATIONS, incrementBy);
	}

	public void setNotificationsById(long id, long value) {
		setById(id, UserCache.UNREAD_NOTIFICATIONS, value);
	}

	public long getNumberOfNotifications(long userId) {
		return getById(userId, UserCache.UNREAD_NOTIFICATIONS);
	}

	public long getNumberOfUnreadMessages(long userId) {
		return getById(userId, UserCache.UNREAD_MESSAGES);
	}

	private Long incrementById(long id, String field, long incrementBy) {
		String key = asRedisKey(KEY_BASE, id);
		return redisTemplate.opsForHash().increment(key, field, incrementBy);
	}

	private void setById(long id, String field, long value) {
		String key = asRedisKey(KEY_BASE, id);
		redisTemplate.opsForHash().put(key, field, String.valueOf(value));
	}

	private long getById(long id, String field) {
		String key = asRedisKey(KEY_BASE, id);
		Object value = redisTemplate.opsForHash().get(key, field);

		if (value == null)
			return 0;

		return Long.parseLong(value.toString());
	}
}
