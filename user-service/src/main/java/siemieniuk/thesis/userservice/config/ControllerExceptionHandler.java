package siemieniuk.thesis.userservice.config;

import java.time.Instant;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import siemieniuk.thesis.userservice.exception.NotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = RuntimeException.class)
	protected ResponseEntity<?> handleAny(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JsonResponse(ex));
	}

	@ExceptionHandler(value = NotFoundException.class)
	protected ResponseEntity<?> handleResourceNotFound(NotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JsonResponse(ex));
	}

	@Data
	private static class JsonResponse {
		private final Instant time = Instant.now();
		private final String message;

		JsonResponse(RuntimeException ex) {
			this.message = ex.getMessage();
		}

	}
}
