package siemieniuk.thesis.userservice.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String login;
	private String name;
	private String surname;
	private String city;
	private Date birthDate;
	private int privileges;
	private boolean isPrivate;

}
