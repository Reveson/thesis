package siemieniuk.thesis.userservice.dto;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

public class RegistrationBearerToken {
	private String accessToken;

	public static RegistrationBearerToken fromJson(String json) {
		return new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.create()
				.fromJson(json, RegistrationBearerToken.class);
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return "Bearer " + accessToken;
	}

}
