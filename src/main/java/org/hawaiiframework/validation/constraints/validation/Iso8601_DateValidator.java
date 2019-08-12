package org.hawaiiframework.validation.constraints.validation;

import org.apache.commons.lang3.StringUtils;
import org.hawaiiframework.validation.constraints.Iso8601_Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The validator for checking ISO-8601 date strings. Does not check whether the date <em>itself</em> is a valid
 * date, in other words, specifying dates like 30th February, or the 99th day of the 99th month is fine.
 *
 * <p>The ISO format defines a most to least significant order: year-month-day.
 */
@SuppressWarnings("checkstyle:typename")
public class Iso8601_DateValidator implements ConstraintValidator<Iso8601_Date, String> {

    /**
     * Pattern to validate a date.
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE;

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
            DATE_TIME_FORMATTER.parse(value);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
