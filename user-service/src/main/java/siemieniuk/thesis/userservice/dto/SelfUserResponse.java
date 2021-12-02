package siemieniuk.thesis.userservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import siemieniuk.thesis.userservice.model.User;

@Setter
@Getter
public class SelfUserResponse extends UserResponse {

	private boolean isAdmin;
	private boolean isBlocked;

	public static SelfUserResponse fromUser(User user, boolean isAdmin) {
		SelfUserResponse response = new SelfUserResponse();
		response.setId(user.getId());
		response.setLogin(user.getLogin());
		response.setName(user.getName());
		response.setSurname(user.getSurname());
		response.setCity(user.getCity());
		response.setBirthDate(parseDate(user.getBirthDate()));
		response.setAdmin(isAdmin);
		response.setBlocked(user.isBlocked());

		return response;
	}
}
