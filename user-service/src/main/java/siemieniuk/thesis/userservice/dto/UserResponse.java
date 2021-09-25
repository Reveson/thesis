package siemieniuk.thesis.userservice.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import siemieniuk.thesis.userservice.model.User;

@Data
public class UserResponse {
	private long id;
	private String login;
	private String name;
	private String surname;
	private String city;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date birthDate;

	public static UserResponse fromUser(User user) {
		UserResponse response = new UserResponse();
		response.setId(user.getId());
		response.setLogin(user.getLogin());
		response.setName(user.getName());
		response.setSurname(user.getSurname());
		response.setCity(user.getCity());
		response.setBirthDate(user.getBirthDate());

		return response;
	}
}
