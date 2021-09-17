package siemieniuk.thesis.feedservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Data;

@RedisHash(UserCache.KEY)
@Data
@Builder(toBuilder = true)
public class UserCache {
	public static final String KEY = "userCache";
	public static final String SYNC_HEAD = "syncHead";
	public static final String SYNC_TAIL = "syncTail";

	@Id
	private Long id;
	private Long syncHead;
	private Long syncTail;
}
