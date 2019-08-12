package org.hawaiiframework.validation.constraints.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ClockProvider;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Past;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Validator for checking the {@link Past} constraint on a string. Currently only works on an ISO date time string.
 * <p>
 * See the file {@code META-INF/services/javax.validation.ConstraintValidator} how this class is wired.
 */
public class PastStringValidator implements ConstraintValidator<Past, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        return isPast(value, context);
    }

    private boolean isPast(final String value, final ConstraintValidatorContext context) {
        try {
            final LocalDateTime localDateTime = LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
            final LocalDateTime now = LocalDateTime.now(getClock(context));
            return isPast(localDateTime, now);
        } catch (DateTimeParseException ignored) {
            return true;
        }
    }

    private boolean isPast(final LocalDateTime date, final LocalDateTime other) {
        return date.isBefore(other);
    }

    private Clock getClock(final ConstraintValidatorContext context) {
        return getClock(context.getClockProvider());
    }

    private Clock getClock(final ClockProvider clockProvider) {
        return clockProvider.getClock();
    }
}

