package org.hawaiiframework.validation.util;

import java.math.BigInteger;
import java.util.UnknownFormatConversionException;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

/**
 * A definition of a country's IBAN format.
 * <p>
 * This is a predicate to test a String against this country's IBAN format.
 */
public class IbanFormat implements Predicate<String> {

    /**
     * The regular expression to match the bban format against.
     */
    private static final Pattern DEFINITION = Pattern.compile("([0-9]+)(.)");

    /**
     * The IBAN's country.
     */
    private final String country;

    /**
     * The IBAN's country code.
     */
    private final String countryCode;

    /**
     * The IBAN's length.
     */
    private final int length;

    /**
     * The format that defines the IBAN. Defined after the country code + 2 check digits.
     */
    private final String bbanFormat;

    /**
     * A more descriptive format of the IBAN.
     */
    private final String ibanFields;

    /**
     * The compiled pattern for this IBAN.
     */
    private final Pattern ibanPattern;

    /**
     * Contructor.
     *
     * @param country     the country.
     * @param countryCode the country code.
     * @param length      the length.
     * @param bbanFormat  the bban format.
     * @param ibanFields  the iband fields.
     */
    @SuppressWarnings("PMD.NcssCount")
    public IbanFormat(final String country, final String countryCode, final int length, final String bbanFormat, final String ibanFields) {
        this.country = country;
        this.length = length;
        this.bbanFormat = bbanFormat;
        this.ibanFields = ibanFields;
        this.countryCode = countryCode;

        final var ibanRegExp = new StringBuilder(19);
        ibanRegExp.append(countryCode).append("[0-9]{2}");
        final String[] checkParts = bbanFormat.split(",");
        for (final String part : checkParts) {
            final Matcher matcher = DEFINITION.matcher(part);
            if (!matcher.matches()) {
                throw new IllegalArgumentException(
                    format("IBAN definition of '%s\t%d\t%s\t%s' is invalid.", country, length, bbanFormat, ibanFields));
            }

            final String what = matcher.group(2);

            switch (what) {
                case "n":
                    ibanRegExp.append("[0-9]");
                    break;

                case "a":
                    ibanRegExp.append("[A-Z]");
                    break;

                case "c":
                    ibanRegExp.append("[A-Z0-9]");
                    break;

                default:
                    throw new UnknownFormatConversionException(format("Unknown format specified '%s'", what));
            }
            final String times = matcher.group(1);

            ibanRegExp.append('{').append(times).append('}');
        }

        ibanPattern = Pattern.compile(ibanRegExp.toString());
    }

    @SuppressWarnings("PMD.CommentRequired")
    public String getCountry() {
        return country;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public String getCountryCode() {
        return countryCode;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public int getLength() {
        return length;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public String getBbanFormat() {
        return bbanFormat;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public String getIbanFields() {
        return ibanFields;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public Pattern getIbanPattern() {
        return ibanPattern;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean test(final String iban) {
        return lengthMatches(iban) && patternMatches(iban) && checkDigitsMatch(iban);
    }

    private boolean lengthMatches(final String iban) {
        return length == iban.length();
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private boolean patternMatches(final String iban) {
        return ibanPattern.matcher(iban).matches();
    }

    /**
     * Will perform the modulo 97 check on an IBAN. IBAN should already be in
     * upper case.
     */
    private boolean checkDigitsMatch(final String iban) {
        // Calculate checksum using modulo 97
        final BigInteger numericIban = getNumericIban(iban);
        final int checkDigit = numericIban.mod(new BigInteger("97")).intValue();
        return checkDigit == 1;
    }

    /**
     * Return a numeric representation of the IBAN, calculated by moving the
     * check digits to the end and replacing all letters by their numerical
     * value.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    private BigInteger getNumericIban(final String iban) {
        // Move first four characters to end of string to put check digit at end
        final String checkDigitIban = iban.substring(4) + iban.substring(0, 4);

        // Convert all letters to numeric values
        final StringBuilder numericIban = new StringBuilder();
        for (int i = 0; i < checkDigitIban.length(); i++) {
            if (Character.isDigit(checkDigitIban.charAt(i))) {
                numericIban.append(checkDigitIban.charAt(i));
            } else {
                numericIban.append(10 + (checkDigitIban.charAt(i) - 'A'));
            }
        }

        return new BigInteger(numericIban.toString());
    }
}
