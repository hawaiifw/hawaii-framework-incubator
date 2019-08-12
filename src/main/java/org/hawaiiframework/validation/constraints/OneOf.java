package org.hawaiiframework.validation.constraints;

import org.hawaiiframework.validation.constraints.validation.OneOfValidator;
import org.hawaiiframework.validation.constraints.validation.OneOfValidatorForInteger;

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
 * The annotated {@code CharSequence} must match be one of the specified values.
 *
 * The values may be specified as a list of Strings, or as one or more <code>enum</code> classes. The acceptable values will be
 * the combined set.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {OneOfValidator.class, OneOfValidatorForInteger.class})
public @interface OneOf {

    /**
     * @return the possible values.
     */
    String[] value() default {};

    /**
     * @return the enums that contain the possible values.
     */
    Class<? extends Enum<?>>[] enumClasses() default {};

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
