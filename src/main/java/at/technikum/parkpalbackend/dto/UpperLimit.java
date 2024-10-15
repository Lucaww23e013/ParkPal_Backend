package at.technikum.parkpalbackend.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UpperLimitValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UpperLimit {
    String message() default "Date exceeds the upper limit";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int years();;
}
