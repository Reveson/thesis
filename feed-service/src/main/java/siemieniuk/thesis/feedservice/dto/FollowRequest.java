package siemieniuk.thesis.feedservice.dto;

import lombok.Data;

@Data
public class FollowRequest {
	private long userId;
	private long followedId;
}
