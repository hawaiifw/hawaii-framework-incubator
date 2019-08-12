package org.hawaiiframework.validation.constraints;

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
 * The annotated {@code LocalDateTime} will be returned in milliseconds.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface LocalDateTimeInMillis {

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

