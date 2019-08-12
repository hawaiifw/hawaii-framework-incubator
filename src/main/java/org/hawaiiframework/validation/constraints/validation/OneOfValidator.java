package org.hawaiiframework.validation.constraints.validation;

import org.hawaiiframework.validation.constraints.OneOf;

import javax.validation.ConstraintValidatorContext;

/**
 * Check value against the list of possible values as defined in the {@link OneOf} constraint.
 */
public class OneOfValidator extends AbstractOneOfValidator<String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return super.isValid(value);
    }

}
