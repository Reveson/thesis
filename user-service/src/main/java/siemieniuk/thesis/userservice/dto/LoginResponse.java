package siemieniuk.thesis.userservice.dto;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import lombok.Data;

@Data
public class LoginResponse {
	private String accessToken;
	private long expiresIn;
	private long refreshExpiresIn;
	private String refreshToken;
	private String tokenType;
	private String sessionState;
	private String scope;
	private UserResponse user;

	public static LoginResponse fromJson(String json) {
		return new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.create()
				.fromJson(json, LoginResponse.class);
	}
}
