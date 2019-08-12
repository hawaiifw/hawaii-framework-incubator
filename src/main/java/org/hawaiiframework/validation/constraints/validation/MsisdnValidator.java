package org.hawaiiframework.validation.constraints.validation;

import org.apache.commons.lang3.StringUtils;
import org.hawaiiframework.validation.constraints.Msisdn;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The validator for checking ITU E.123 telephone numbers (as string).
 */
@SuppressWarnings("checkstyle:typename")
public class MsisdnValidator implements ConstraintValidator<Msisdn, String> {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        final boolean ituE123Valid = new ItuE123Validator().isValid(value, context);
        return ituE123Valid && startsWithMsisdnPrefix(value);
    }

    private boolean startsWithMsisdnPrefix(final String value) {
        return value.startsWith("316");
    }
}
