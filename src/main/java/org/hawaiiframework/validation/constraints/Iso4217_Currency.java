package org.hawaiiframework.validation.constraints;

import org.hawaiiframework.validation.constraints.validation.Iso4217_CurrencyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated {@code CharSequence} must be one the ISO 4217 country codes.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = Iso4217_CurrencyValidator.class)
@SuppressWarnings("checkstyle:typename")
public @interface Iso4217_Currency {

    /**
     * @return placeholder, ignored.
     */
    String[] value() default "";

    /**
     * The error message template.
     * <p>
     * Overruled by ConstraintDescription.properties.
     *
     * @return The error message template.
     */
    String message() default "The value is not one of the defined list.";

    /**
     * @return The groups the constraint belongs to.
     */
    Class<?>[] groups() default {};

    /**
     * @return The payload associated to the constraint.
     */
    Class<? extends Payload>[] payload() default {};
}
