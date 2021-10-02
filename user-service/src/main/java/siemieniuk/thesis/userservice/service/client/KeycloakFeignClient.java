package siemieniuk.thesis.userservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "keycloak", url = "${keycloak.auth-server-url}")
public interface KeycloakFeignClient {

	@RequestMapping(
			method= RequestMethod.POST,
			value="/realms/thesis/protocol/openid-connect/token",
			consumes="application/x-www-form-urlencoded"
	)
	ResponseEntity<String> getToken(@RequestBody MultiValueMap<String, String> body);

	@RequestMapping(
			method= RequestMethod.POST,
			value="/realms/master/protocol/openid-connect/token",
			consumes="application/x-www-form-urlencoded"
	)
	ResponseEntity<String> getTokenForRegistration(@RequestBody MultiValueMap<String, String> body);

	@RequestMapping(
			method= RequestMethod.POST,
			value="/admin/realms/thesis/users",
			consumes = "application/json"
	)
	ResponseEntity<String> registerAccount(@RequestBody String body, @RequestHeader("Authorization") String bearerToken);
}
