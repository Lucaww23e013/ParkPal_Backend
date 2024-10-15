package at.technikum.parkpalbackend.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class InvalidEventTimeException extends RuntimeException {
    public InvalidEventTimeException(String message) {
        super(message);
    }
}
