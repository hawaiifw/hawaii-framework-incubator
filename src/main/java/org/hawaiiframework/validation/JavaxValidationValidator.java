package org.hawaiiframework.validation;

import org.hibernate.validator.internal.engine.ValidatorFactoryImpl;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Validate a bean with Javax validations (JSR-303).
 *
 * See {@code resources/META-INF/services/javax.validation.ConstraintValidator} to add validators for existing constraints.
 *
 * @param <T> The type to validate.
 */
public class JavaxValidationValidator<T> implements Validator<T> {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Override
    public void validate(final T object, final ValidationResult validationResult) {
        final Set<String> requiredFields = new HashSet<>();
        final Set<String> invalidFields = new HashSet<>();

        final ValidatorFactoryImpl factory = (ValidatorFactoryImpl) Validation.buildDefaultValidatorFactory();
        final javax.validation.Validator validator = factory.getValidator();

        final Set<ConstraintViolation<T>> violations = validator.validate(object);
        for (final ConstraintViolation<T> constraintViolation : violations) {
            final Path path = constraintViolation.getPropertyPath();
            final String propertyPath = path.toString();
            final Annotation annotation = constraintViolation.getConstraintDescriptor().getAnnotation();
            final Class<? extends Annotation> annotationType = annotation.annotationType();
            final boolean required = isRequired(annotationType);
            if (required) {
                invalidFields.remove(propertyPath);
                requiredFields.add(propertyPath);
            } else {
                if (!requiredFields.contains(propertyPath)) {
                    invalidFields.add(propertyPath);
                }
            }
        }

        for (final String path : requiredFields) {
            validationResult.rejectValue(path, "REQUIRED");
        }
        for (final String path : invalidFields) {
            validationResult.rejectValue(path, "INVALID");
        }

    }

    private boolean isRequired(final Class<? extends Annotation> annotationType) {
        return annotationType.isAssignableFrom(NotBlank.class)
            || annotationType.isAssignableFrom(NotEmpty.class)
            || annotationType.isAssignableFrom(NotNull.class);
    }
}
