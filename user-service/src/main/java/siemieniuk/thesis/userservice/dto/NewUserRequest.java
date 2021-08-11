package siemieniuk.thesis.userservice.dto;

import lombok.Data;

@Data
public class NewUserRequest {

	private String login;
	private String password;
	private String passwordRepeated;
}
