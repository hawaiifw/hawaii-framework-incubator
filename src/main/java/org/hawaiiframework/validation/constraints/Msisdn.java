package org.hawaiiframework.validation.constraints;

import org.hawaiiframework.validation.constraints.validation.MsisdnValidator;

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
 * The annotated {@code CharSequence} must match the ITU E.123 specification (w/o spaces and plus sign).
 * <p>
 * For instance: 31612345678.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = MsisdnValidator.class)
@SuppressWarnings("checkstyle:typename")
public @interface Msisdn {

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
