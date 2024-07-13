package at.technikum.parkpalbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserWithUserNameOrEmailAlreadyExists extends RuntimeException{
    public UserWithUserNameOrEmailAlreadyExists() {
    }

    public UserWithUserNameOrEmailAlreadyExists(String message) {
        super(message);
    }

    public UserWithUserNameOrEmailAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public UserWithUserNameOrEmailAlreadyExists(Throwable cause) {
        super(cause);
    }
}
