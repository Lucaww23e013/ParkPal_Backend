package at.technikum.parkpalbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Resource Access Error")
public class ResourceAccessException extends RuntimeException {
    public ResourceAccessException(String message) {
        super(message);
    }

    public ResourceAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
