package siemieniuk.thesis.userservice.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.userservice.service.client.FeedFeignClient;

@Service
@AllArgsConstructor
public class UserActivityService {
	private static final String ONLINE_USERS = "onlineUsers";
	private static final long onlineInterval = TimeUnit.MINUTES.toMillis(30);
	private static final long cleanupIntervalMillis = 60 * 1000;

	private final RedisTemplate<String, String> redisTemplate;
	private final FeedFeignClient feedFeignClient;

	public void setUserActive(long userId) {
		long now = System.currentTimeMillis();
		Boolean added = redisTemplate.opsForZSet().add(ONLINE_USERS, String.valueOf(userId), now);
		//TODO null check
		if (added != null && !added)
			orderFeedDenormalization(userId);
	}

	@Async
	protected void orderFeedDenormalization(long userId) {
		feedFeignClient.denormalizeFeeds(userId);
	}

	@Scheduled(fixedRate = cleanupIntervalMillis, initialDelay = cleanupIntervalMillis)
	public void cleanupExpiredUsers() {
		//TODO synchronization - one job per whole cluster
		long now = System.currentTimeMillis();
		redisTemplate.opsForZSet().removeRangeByScore(ONLINE_USERS, 0, now - onlineInterval);
	}

}
