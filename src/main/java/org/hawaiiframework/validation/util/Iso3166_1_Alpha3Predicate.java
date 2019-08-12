package org.hawaiiframework.validation.util;

import java.util.Locale;
import java.util.function.Predicate;

import static java.util.Locale.IsoCountryCode.PART1_ALPHA3;

/**
 * The predicate for checking ISO-3166-1 Alpha 3 country codes. According to the ISO standard
 * an alpha-3 country code exists of three uppercase latin characters.
 * <p>
 * The allowed country codes are read from
 *
 * @see "https://www.iso.org/iso-3166-country-codes.html"
 */
@SuppressWarnings("checkstyle:typename")
public class Iso3166_1_Alpha3Predicate implements Predicate<String> {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Override
    public boolean test(final String value) {
        return Locale.getISOCountries(PART1_ALPHA3).contains(value);
    }
}
