package org.hawaiiframework.validation.constraints.validation;

import org.apache.commons.lang3.StringUtils;
import org.hawaiiframework.validation.constraints.Iso8601_DateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The validator for checking ISO-8601 date-time strings.
 */
@SuppressWarnings("checkstyle:typename")
public class Iso8601_DateTimeValidator implements ConstraintValidator<Iso8601_DateTime, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        return isValid(value);
    }

    private boolean isValid(final String value) {
        try {
            LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
            return true;
        } catch (DateTimeParseException ignored) {
            return false;
        }
    }
}
