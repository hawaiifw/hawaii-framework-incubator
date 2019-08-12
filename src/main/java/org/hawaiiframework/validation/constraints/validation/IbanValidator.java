package org.hawaiiframework.validation.constraints.validation;

import org.apache.commons.lang3.StringUtils;
import org.hawaiiframework.validation.constraints.Iban;
import org.hawaiiframework.validation.util.IbanPredicate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.function.Predicate;

/**
 * The validator for checking IBAN account numbers.
 */
@SuppressWarnings("checkstyle:typename")
public class IbanValidator implements ConstraintValidator<Iban, String> {

    /**
     * The IBAN predicate.
     */
    private static final Predicate<String> PREDICATE = new IbanPredicate();

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
