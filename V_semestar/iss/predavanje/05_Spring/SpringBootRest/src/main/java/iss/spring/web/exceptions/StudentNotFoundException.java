package iss.spring.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Ako hoćemo da se i tekst iz atributa <b>reason</b> vidi u klijentu, 
 * onda moramo u <code>application.properties</code> da stavimo ovaj red:
 * <code>server.error.include-message=always</code>
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Student not found!") 
public class StudentNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4389231360611586475L;
}
