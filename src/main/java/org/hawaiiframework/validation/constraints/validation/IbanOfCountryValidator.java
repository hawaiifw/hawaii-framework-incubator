package org.hawaiiframework.validation.constraints.validation;

import org.apache.commons.lang3.StringUtils;
import org.hawaiiframework.validation.constraints.IbanOfCountry;
import org.hawaiiframework.validation.util.IbanOfCountryPredicate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The validator for checking IBAN account numbers.
 */
@SuppressWarnings("checkstyle:typename")
public class IbanOfCountryValidator implements ConstraintValidator<IbanOfCountry, String> {

    /**
     * The IBAN predicate.
     */
    private IbanOfCountryPredicate predicate;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final IbanOfCountry constraintAnnotation) {
        predicate = new IbanOfCountryPredicate(constraintAnnotation.value());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        return predicate.test(value);
    }
}
