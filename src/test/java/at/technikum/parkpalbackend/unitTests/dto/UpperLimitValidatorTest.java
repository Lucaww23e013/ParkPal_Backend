package at.technikum.parkpalbackend.unitTests.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

public class UpperLimitValidatorTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testDateWithinLimit() {
        TestEntity entity = new TestEntity(LocalDateTime.now().plusMonths(6));
        Set<ConstraintViolation<TestEntity>> violations = validator.validate(entity);
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    public void testDateExceedsLimit() {
        TestEntity entity = new TestEntity(LocalDateTime.now().plusYears(2));
        Set<jakarta.validation.ConstraintViolation<TestEntity>> violations = validator.validate(entity);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("Date exceeds the upper limit of 1 year", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullDate() {
        TestEntity entity = new TestEntity(null);
        Set<jakarta.validation.ConstraintViolation<TestEntity>> violations = validator.validate(entity);
        Assertions.assertEquals(1, violations.size()); // upper limit Date is Optional
    }

}