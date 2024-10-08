package at.technikum.parkpalbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // Maps the exception to a 400 Bad Request response
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

}
