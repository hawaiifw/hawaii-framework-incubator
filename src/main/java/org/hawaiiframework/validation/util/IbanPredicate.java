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
 * Predicate for checking IBAN numbers.
 * <p>
 * See https://en.wikipedia.org/wiki/International_Bank_Account_Number for defining the list of IBAN formats.
 */
public class IbanPredicate implements Predicate<String> {

    /**
     * The logger to use.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IbanPredicate.class);

    /**
     * The map of parsed IBAN formats.
     */
    private static final Map<String, IbanFormat> FORMATS = new HashMap<>();

    /**
     * The minimal length. An IBAN contains at least 2 check digits and 2 country code letters.
     */
    private static int minimumLength = 5;

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

    @SuppressWarnings({"PMD.AvoidThrowingRawExceptionTypes", "PMD.AvoidCatchingGenericException", "PMD.AvoidInstantiatingObjectsInLoops",
        "PMD.LawOfDemeter"})
    private static void init() {
        try {
            int minLength = Integer.MAX_VALUE;
            final InputStream resource = IOUtils.class.getResourceAsStream("/iban-formats.txt");
            final List<String> lines = IOUtils.readLines(resource, "UTF-8");
            for (final String line : lines) {
                if (line.charAt(ZERO) == HASH_CHAR) {
                    continue;
                }
                LOGGER.trace("Read IBAN definition '{}'", line);

                final String[] values = line.split("\\t");
                final String country = values[0];
                final int length = Integer.parseInt(values[1]);
                final String bbanFormat = values[2];
                final String ibanFields = values[3];
                final String countryCode = ibanFields.substring(0, 2);

                FORMATS.put(countryCode, new IbanFormat(country, countryCode, length, bbanFormat, ibanFields));

                minLength = Math.min(minLength, length);
            }

            minimumLength = minLength;
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
             * Empty string is allowed, if an IBAN is required, mark it as '@NotBlank'.
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
         * If the length is smaller than the min length, it is invalid anyway.
         */
        if (value.length() < minimumLength) {
            return false;
        }

        final String upperCaseValue = value.replaceAll("[^a-zA-Z0-9]", "").toUpperCase(ROOT);
        final String countryCode = upperCaseValue.substring(0, 2);

        boolean valid = false;
        final IbanFormat format = FORMATS.get(countryCode);
        if (format == null) {
            LOGGER.error("No IBAN format defined for '{}'.", value);
        } else {
            valid = format.test(upperCaseValue);
        }

        return valid;
    }

}
