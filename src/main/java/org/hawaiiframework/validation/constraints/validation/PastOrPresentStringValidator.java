package org.hawaiiframework.validation.constraints.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ClockProvider;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.PastOrPresent;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Validator for checking the {@link PastOrPresent} constraint on a string. Currently only works on an ISO date string.
 * <p>
 * See the file {@code META-INF/services/javax.validation.ConstraintValidator} how this class is wired.
 */
public class PastOrPresentStringValidator implements ConstraintValidator<PastOrPresent, String> {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return isPastOrPresent(value, context);
    }

    private boolean isPastOrPresent(final String value, final ConstraintValidatorContext context) {
        try {
            final LocalDate localDate = LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
            final LocalDate now = LocalDate.now(getClock(context));
            return isPastOrPresent(localDate, now);
        } catch (DateTimeParseException ignored) {
            return false;
        }
    }

    private boolean isPastOrPresent(final LocalDate date, final LocalDate other) {
        return date.isEqual(other) || date.isBefore(other);
    }

    private Clock getClock(final ConstraintValidatorContext context) {
        return getClock(context.getClockProvider());
    }

    private Clock getClock(final ClockProvider clockProvider) {
        return clockProvider.getClock();
    }
}
