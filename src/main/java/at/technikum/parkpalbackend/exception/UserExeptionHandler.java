package at.technikum.parkpalbackend.exception;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.HashMap;
import java.util.Map;

public class UserExeptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class,
        DataIntegrityViolationException.class
    })
    public Map<String, String> handleValidException(Exception exception) {
        Map<String, String> errors = new HashMap<>();

        if (exception instanceof MethodArgumentNotValidException validException) {
            validException.getBindingResult().getAllErrors().forEach((error) -> {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            });
        } else if (exception instanceof DataIntegrityViolationException) {
            Throwable cause = exception.getCause();
            String errorMessage = (cause != null) ? cause.getMessage() : exception.getMessage();
            String emailErrorMessage = "Email already exists, please choose another one";
            String usernameErrorMessage = "Username already exists, please choose another one";

            if (errorMessage.contains(emailErrorMessage)) {
                errors.put("email", emailErrorMessage);
            }
            if (errorMessage.contains(usernameErrorMessage)) {
                errors.put("username", usernameErrorMessage);
            }
        }
        return errors;
    }
}