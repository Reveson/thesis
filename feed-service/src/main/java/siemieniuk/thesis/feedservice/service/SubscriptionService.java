package siemieniuk.thesis.feedservice.service;

import static siemieniuk.thesis.feedservice.util.RedisKeyMapper.asRedisKey;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	private static final String ONLINE_USERS = "onlineUsers";


	public List<Long> getActiveSubscribersList(long userId) {
		String onlineFollowers = asRedisKey(ONLINE_USERS, userId);
		//TODO can be done in one operation, but no interface in dependency...
		redisTemplate.opsForZSet().intersectAndStore(ONLINE_USERS, USER_FOLLOWERS, onlineFollowers);
		Set<String> onlineFollowersIds = redisTemplate.opsForZSet().rangeByScore(onlineFollowers, 0, -1);

		if (onlineFollowersIds == null) {
			//TODO null log
			onlineFollowersIds = Collections.emptySet();
		}

		return onlineFollowersIds.stream().map(Long::parseLong).collect(Collectors.toList());
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

	public List<Long> getUsersIdsFollowed(long userId) {
		String key = asRedisKey(FOLLOWED_USERS, userId);
		Set<String> followed = redisTemplate.opsForSet().members(key);
		//TODO null handle
		if (followed == null)
			return Collections.emptyList();

		return followed.stream().map(Long::valueOf).collect(Collectors.toList());
	}

	public List<Long> getUsersFollowedBy(long userId) {
		String key = asRedisKey(FOLLOWED_USERS, userId);
		//TODO null handle
		Set<String> usersFollowed = redisTemplate.opsForSet().members(key);
		if (usersFollowed == null)
			return Collections.emptyList();

		return usersFollowed.stream().map(Long::parseLong).collect(Collectors.toList());
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
