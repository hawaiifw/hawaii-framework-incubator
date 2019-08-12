package org.hawaiiframework.validation.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Locale.ROOT;

/**
 * Predicate for checking IBAN numbers.
 * <p>
 * See https://en.wikipedia.org/wiki/International_Bank_Account_Number for defining the list of IBAN formats.
 */
public class IbanOfCountryPredicate implements Predicate<String> {

    /**
     * The IBAN predicate.
     */
    private static final IbanPredicate PREDICATE = new IbanPredicate();

    /**
     * The list of countries.
     */
    private final List<String> countries = new ArrayList<>();

    /**
     * The constructor.
     *
     * @param countries the list of countries.
     */
    public IbanOfCountryPredicate(final List<String> countries) {
        this.countries.addAll(countries);
    }

    /**
     * The constructor.
     *
     * @param countries the countries.
     */
    public IbanOfCountryPredicate(final String... countries) {
        for (final String country : countries) {
            this.countries.add(country.toUpperCase(ROOT));
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Override
    public boolean test(final String value) {
        return PREDICATE.test(value) && countries.contains(value.substring(0, 2).toUpperCase(ROOT));
    }
}
