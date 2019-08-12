package org.hawaiiframework.validation.constraints.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Validate ISO 3166-1 country codes")
class Iso3166_1_Alpha3ValidatorTest {
    @Test
    @DisplayName("Valid ISO codes")
    public void validIsoCodes() {
        var validator = new Iso3166_1_Alpha3Validator();

        assertTrue(validator.isValid("NLD", null));
        assertTrue(validator.isValid("DEU", null));
        assertTrue(validator.isValid("BEL", null));
        assertTrue(validator.isValid("NZL", null));
    }

    @Test
    @DisplayName("Invalid ISO codes")
    public void invalidIsoCodes() {
        var validator = new Iso3166_1_Alpha3Validator();

        assertFalse(validator.isValid("XXX", null));
        assertFalse(validator.isValid("nld", null));
        assertFalse(validator.isValid("Deu", null));
        assertFalse(validator.isValid("bel", null));
    }
}