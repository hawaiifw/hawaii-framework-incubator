package org.hawaiiframework.validation.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.Locale.ROOT;

/**
 * Predicate for checking ISO4217 currency codes.
 */
public class Iso4217CurrencyPredicate implements Predicate<String> {

    /**
     * The logger to use.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Iso4217CurrencyPredicate.class);

    /**
     * The length of a country code according to ISO4271.
     */
    private static final int COUNTRY_CODE_LENGTH = 3;

    /**
     * The map of parsed currencies.
     */
    private static final Map<String, Iso4217Format> FORMATS = new HashMap<>();

    /**
     * Hash.
     */
    private static final char HASH_CHAR = '#';
    /**
     * Zero.
     */
    private static final int ZERO = 0;

    static {
        init();
    }

    @SuppressWarnings({"PMD.AvoidThrowingRawExceptionTypes", "PMD.AvoidCatchingGenericException",
        "PMD.AvoidInstantiatingObjectsInLoops",
        "PMD.LawOfDemeter"})
    private static void init() {
        try {
            final InputStream resource = Iso4217CurrencyPredicate.class.getResourceAsStream("/iso4217-currencies.txt");
            final List<String> lines = IOUtils.readLines(resource, "UTF-8");
            for (final String line : lines) {
                if (line.charAt(ZERO) == HASH_CHAR) {
                    continue;
                }
                LOGGER.trace("Read currency definition '{}'", line);

                final String[] values = line.split("\\t");
                final String code = values[0];
                final Integer numericCode = Integer.valueOf(values[1]);
                final String name = values[2];
                final String locations = values[3];

                FORMATS.put(code, new Iso4217Format(code, numericCode, name, locations));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"checkstyle:returncount", "PMD.LawOfDemeter"})
    @Override
    public boolean test(final String value) {
        if (StringUtils.isEmpty(value)) {
            /*
             * Empty string is allowed, if a currency is required, mark it as '@NotBlank'.
             */
            return true;
        }

        /*
         * A blank string is not valid.
         */
        if (StringUtils.isBlank(value)) {
            return false;
        }

        /*
         * If the length is not according to the spec, the string is not valid.
         */
        if (value.length() != COUNTRY_CODE_LENGTH) {
            return false;
        }

        return FORMATS.containsKey(value.toUpperCase(ROOT));
    }

}
