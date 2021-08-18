package siemieniuk.thesis.messageservice.util;

public class RedisKeyMapper {
	private static final String SEPARATOR = ":";

	public static String asRedisKey(String keyBase, long id) {
		return keyBase + SEPARATOR + id;
	}
}
