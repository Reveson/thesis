package siemieniuk.thesis.messageservice.model;

import static siemieniuk.thesis.messageservice.model.UserCache.KEY;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@RedisHash(KEY)
@Data
public class UserCache {
	public static final String KEY = "userCache";
	public static final String UNREAD_MESSAGES = "unreadMessages";

	@Id
	private long id;
	private long  unreadMessages;
}
