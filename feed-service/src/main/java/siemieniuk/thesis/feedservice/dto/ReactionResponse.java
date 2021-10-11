package siemieniuk.thesis.feedservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReactionResponse {

	private long count;
	private boolean alreadyReacted;
}
