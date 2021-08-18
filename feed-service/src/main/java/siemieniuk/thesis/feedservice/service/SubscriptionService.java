package siemieniuk.thesis.feedservice.service;

import static siemieniuk.thesis.feedservice.util.RedisKeyMapper.asRedisKey;

import java.util.Collections;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import siemieniuk.thesis.feedservice.dto.FollowRequest;

@Service
@AllArgsConstructor
public class SubscriptionService {
	private final RedisTemplate<String, String> redisTemplate;
	private static final String FOLLOWED_USERS = "followedUsers";
	private static final String USER_FOLLOWERS = "userFollowers";


	public List<Long> getActiveSubscribersList(long userId) {
		//TODO integrate with Redis
		return Collections.emptyList();
	}

	public long getNumberOfFollowers(long userId) {
		String key = asRedisKey(USER_FOLLOWERS, userId);
		Long followers = redisTemplate.opsForSet().size(key);
		//TODO null handle
		return followers == null ? 0 : followers;
	}

	public long getNumberOfUsersFollowed(long userId) {
		String key = asRedisKey(FOLLOWED_USERS, userId);
		Long followed = redisTemplate.opsForSet().size(key);
		//TODO null handle
		return followed == null ? 0 : followed;
	}

	public boolean isFollowed(long userId, long followedId) {
		String key = asRedisKey(FOLLOWED_USERS, userId);
		Boolean isFollowed = redisTemplate.opsForSet().isMember(key, String.valueOf(followedId));
		//TODO null handle
		return isFollowed != null && isFollowed;
	}

	public void followUser(FollowRequest request) {
		String followedUsersKey = asRedisKey(FOLLOWED_USERS, request.getUserId());
		String userFollowersKey = asRedisKey(USER_FOLLOWERS, request.getFollowedId());
		//TODO same transaction
		redisTemplate.opsForSet().add(followedUsersKey, String.valueOf(request.getFollowedId()));
		redisTemplate.opsForSet().add(userFollowersKey, String.valueOf(request.getUserId()));
	}

	public void unfollowUser(FollowRequest request) {
		String followedUsersKey = asRedisKey(FOLLOWED_USERS, request.getUserId());
		String userFollowersKey = asRedisKey(USER_FOLLOWERS, request.getFollowedId());
		//TODO same transaction
		redisTemplate.opsForSet().remove(followedUsersKey, String.valueOf(request.getFollowedId()));
		redisTemplate.opsForSet().remove(userFollowersKey, String.valueOf(request.getUserId()));
	}
}
