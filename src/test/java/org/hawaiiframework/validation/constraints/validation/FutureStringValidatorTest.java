package org.hawaiiframework.validation.constraints.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorContext;
import java.time.Clock;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Future string validator test")
class FutureStringValidatorTest {

    private FutureStringValidator futureStringValidator;
    private ConstraintValidatorContext constraintValidatorContext;
    private ClockProvider clockProvider;
    private Clock clock;

    @BeforeEach
    public void setup() {
        futureStringValidator = new FutureStringValidator();
        constraintValidatorContext = mock(ConstraintValidatorContext.class);
        clockProvider = mock(ClockProvider.class);
        clock = Clock.systemUTC();

        when(constraintValidatorContext.getClockProvider()).thenReturn(clockProvider);
        when(clockProvider.getClock()).thenReturn(clock);
    }

    @Test
    @DisplayName("Validate date is in the future")
    public void stringIsInTheFuture() {
        var future = LocalDate.now(clock).plusYears(999).toString();
        assertTrue(futureStringValidator.isValid(future, constraintValidatorContext));
    }

    @Test
    @DisplayName("Validate date is not in the future - 1 year off")
    public void stringIsNotInFutureOneYearOff() {
        var future = LocalDate.now(Clock.systemUTC()).minusYears(1).toString();
        assertTrue(futureStringValidator.isValid(future, constraintValidatorContext));
    }

    @Test
    @DisplayName("Validate date is not in the future - 1 day off")
    public void stringIsNotInFutureOneDayOff() {
        var future = LocalDate.now(Clock.systemUTC()).minusDays(1).toString();
        assertTrue(futureStringValidator.isValid(future, constraintValidatorContext));
    }
}

