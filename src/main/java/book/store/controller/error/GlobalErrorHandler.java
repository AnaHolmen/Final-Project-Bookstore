package book.store.controller.error;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

//Tells Spring that this class is a global error handler
@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

	//Tells Spring that this is an exception handler method for the NoSuchElementException
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	//Handles the exception by returning a custom message that explains why the NoSuchElementException was thrown
	public Map<String, String> handleNoSuchElementException(NoSuchElementException ex) {
		Map<String, String> exception = new HashMap<String, String>();
		String message = ex.toString();
		log.error("Exception: {}", message);
		
		exception.put("message", message);
		
		return exception; 
	}
}