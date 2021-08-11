package siemieniuk.thesis.userservice.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChangeInfoRequest {

	private String name;
	private String surname;
	private String city;
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date birthDate;
	private boolean isPrivate;
}
