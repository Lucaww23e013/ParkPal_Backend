package at.technikum.parkpalbackend.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class UpperLimitValidator implements ConstraintValidator<UpperLimit, LocalDateTime> {

    private int years;

    @Override
    public void initialize(UpperLimit constraintAnnotation) {
        this.years = constraintAnnotation.years();
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are considered valid
        }
        LocalDateTime upperLimit = LocalDateTime.now().plusYears(years);
        return value.isBefore(upperLimit);
    }
}
