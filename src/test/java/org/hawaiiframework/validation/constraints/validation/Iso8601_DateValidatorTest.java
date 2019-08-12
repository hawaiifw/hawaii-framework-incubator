package org.hawaiiframework.validation.constraints.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ISO8601 date format validation")
class Iso8601_DateValidatorTest {
    @Test
    @DisplayName("Valid formatted date")
    void validFormat() {
        var validator = new Iso8601_DateValidator();
        assertTrue(validator.isValid("1970-08-27", null));
    }

    @Test
    @DisplayName("Invalid formatted date")
    void invalidFormat() {
        var validator = new Iso8601_DateValidator();
        assertFalse(validator.isValid("27-08-1970", null));
    }
}