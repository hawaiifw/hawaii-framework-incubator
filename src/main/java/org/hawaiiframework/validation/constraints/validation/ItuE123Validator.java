package org.hawaiiframework.validation.constraints.validation;

import org.apache.commons.lang3.StringUtils;
import org.hawaiiframework.validation.constraints.ItuE123;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The validator for checking ITU E.123 telephone numbers (as string).
 */
@SuppressWarnings("checkstyle:typename")
public class ItuE123Validator implements ConstraintValidator<ItuE123, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        return StringUtils.isNumericSpace(value);
    }
}
