package org.hawaiiframework.validation.constraints.validation;

import org.apache.commons.lang3.StringUtils;
import org.hawaiiframework.validation.constraints.OneOf;

import javax.validation.ConstraintValidator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract base implementation to validate a {@link OneOf} constraint.
 *
 * @param <T> the type that this validator checks
 */
public abstract class AbstractOneOfValidator<T> implements ConstraintValidator<OneOf, T> {

    /**
     * The lit of possible values.
     */
    private Set<String> possibleValues;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final OneOf constraintAnnotation) {
        possibleValues = new HashSet<>();
        possibleValues.addAll(Arrays.asList(constraintAnnotation.value()));

        final Class<? extends Enum<?>>[] enumClasses = constraintAnnotation.enumClasses();
        extractEnumValues(enumClasses);
    }

    /**
     * Method to check whether or not a String value is valid.
     */
    protected boolean isValid(final String value) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        return possibleValues.contains(value);
    }

    /**
     * Loops of the enums, and over each enum's constants, and adds each constant's {@link Enum#name()} to the possibleValues.
     *
     * @param enumClasses the enum classes from the annotation
     */
    @SafeVarargs
    private void extractEnumValues(final Class<? extends Enum<?>>... enumClasses) {
        if (enumClasses.length > 0) {
            for (final Class<? extends Enum<?>> enumClass : enumClasses) {
                for (final Enum<?> enumValue : enumClass.getEnumConstants()) {
                    possibleValues.add(enumValue.name());
                }
            }
        }
    }

}
