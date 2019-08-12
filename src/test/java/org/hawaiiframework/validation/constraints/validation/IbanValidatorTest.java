package org.hawaiiframework.validation.constraints.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Validate IBAN account numbers")
class IbanValidatorTest {
    @Test
    @DisplayName("Valid NL, BE or DE")
    void validNLBEDE() {
        // assumed to be the most important IBAN formats to support.
        var validator = new IbanValidator();
        assertTrue(validator.isValid("BE68539007547034", null));
        assertTrue(validator.isValid("NL91ABNA0417164300", null));
        assertTrue(validator.isValid("DE89370400440532013000", null));
    }

    @Test
    @DisplayName("Valid other European")
    void validOtherEurope() {
        var validator = new IbanValidator();
        assertTrue(validator.isValid("DK5000400440116243", null));
        assertTrue(validator.isValid("AD1200012030200359100100", null));
        assertTrue(validator.isValid("LI21088100002324013AA", null));
        assertTrue(validator.isValid("FR1420041010050500013M02606", null));
        assertTrue(validator.isValid("GB82WEST12345698765432", null));
    }

    @Test
    @DisplayName("Invalid IBAN format")
    void invalid() {
        var validator = new IbanValidator();
        assertFalse(validator.isValid("NL", null));
        assertFalse(validator.isValid("NL91ABNA04171643005", null));
        assertFalse(validator.isValid("DEU8937040440532013000", null));
    }

    @Test
    @DisplayName("Invalid modulo 97")
    void checkModulo97() {
        var validator = new IbanValidator();
        assertFalse(validator.isValid("NL91ABNA0417164301", null));
        assertFalse(validator.isValid("GB82WEST12345698765430", null));
    }
}