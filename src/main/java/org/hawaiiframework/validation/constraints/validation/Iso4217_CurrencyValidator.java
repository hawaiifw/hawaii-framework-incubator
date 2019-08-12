package org.hawaiiframework.validation.constraints.validation;

import org.apache.commons.lang3.StringUtils;
import org.hawaiiframework.validation.constraints.Iso4217_Currency;
import org.hawaiiframework.validation.util.Iso4217CurrencyPredicate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.function.Predicate;

/**
 * The validator for checking ISO-4217 currency codes.
 */
@SuppressWarnings("checkstyle:typename")
public class Iso4217_CurrencyValidator implements ConstraintValidator<Iso4217_Currency, String> {

    /**
     * The IBAN predicate.
     */
    private static final Predicate<String> PREDICATE = new Iso4217CurrencyPredicate();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        return PREDICATE.test(value);
    }
}
