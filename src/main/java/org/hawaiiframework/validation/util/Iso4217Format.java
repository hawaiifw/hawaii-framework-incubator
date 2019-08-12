package org.hawaiiframework.validation.util;

/**
 * ISO4217 Currency Format.
 */
public class Iso4217Format {

    /**
     * The currency's code.
     */
    private final String code;

    /**
     * The currency's numeric code.
     */
    private final Integer numericCode;

    /**
     * The currency's name.
     */
    private final String name;

    /**
     * The locations where the currency is valid.
     */
    private final String locations;

    /**
     * The constructor.
     *
     * @param code        The currency's code.
     * @param numericCode The currency's numeric code.
     * @param name        The currency's name.
     * @param locations   The locations where the currency is valid.
     */
    public Iso4217Format(final String code, final Integer numericCode, final String name, final String locations) {
        this.code = code;
        this.numericCode = numericCode;
        this.name = name;
        this.locations = locations;
    }

    /**
     * Gets code.
     *
     * @return code value
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets numericCode.
     *
     * @return numericCode value
     */
    public Integer getNumericCode() {
        return numericCode;
    }

    /**
     * Gets name.
     *
     * @return name value
     */
    public String getName() {
        return name;
    }

    /**
     * Gets locations.
     *
     * @return locations value
     */
    public String getLocations() {
        return locations;
    }
}
