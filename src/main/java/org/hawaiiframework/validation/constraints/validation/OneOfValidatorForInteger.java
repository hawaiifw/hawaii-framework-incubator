package org.hawaiiframework.validation.constraints.validation;

import org.hawaiiframework.validation.constraints.OneOf;

import javax.validation.ConstraintValidatorContext;

/**
 * Check value against the list of possible values as defined in the {@link OneOf} constraint.
 */
public class OneOfValidatorForInteger extends AbstractOneOfValidator<Integer> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final Integer value, final ConstraintValidatorContext context) {
        return super.isValid(value.toString());
    }

}
