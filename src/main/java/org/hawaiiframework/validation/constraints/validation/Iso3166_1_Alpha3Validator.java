package org.hawaiiframework.validation.constraints.validation;

import org.apache.commons.lang3.StringUtils;
import org.hawaiiframework.validation.constraints.Iso3166_1_Alpha3;
import org.hawaiiframework.validation.util.Iso3166_1_Alpha3Predicate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.function.Predicate;

/**
 * The validator for checking ISO-3166-1 Alpha 3 country codes. According to the ISO standard
 * an alpha-3 country code exists of three uppercase latin characters.
 * <p>
 * The allowed country codes are read from <code>iso3116_1_alpha3.txt</code>
 *
 * @see "https://www.iso.org/iso-3166-country-codes.html"
 */
@SuppressWarnings("checkstyle:typename")
public class Iso3166_1_Alpha3Validator implements ConstraintValidator<Iso3166_1_Alpha3, String> {

    /**
     * The predicate that does the check.
     */
    private static final Predicate<String> PREDICATE = new Iso3166_1_Alpha3Predicate();

    /**
     * {@inheritDoc}
     *
     * @return whether the value is exactly present in the ISO list.
     */
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        return PREDICATE.test(value);
    }
}
