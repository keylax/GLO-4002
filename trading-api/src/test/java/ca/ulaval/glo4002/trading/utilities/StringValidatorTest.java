package ca.ulaval.glo4002.trading.utilities;

import org.junit.Assert;
import org.junit.Test;

public class StringValidatorTest {
    private static final String STRING_EMPTY_VALUE = "";
    private static final String STRING_WITH_WHITESPACE = "   ";
    private static final String STRING_WITH_DEFINE_VALUE = "myValue";

    @Test
    public void givenStringWithNullValue_whenValidateIfStringIsNullOrEmpty_thenReturnTrue() {
        boolean isNullOrEmpty = StringValidator.isNullOrEmpty(null);

        Assert.assertTrue(isNullOrEmpty);
    }

    @Test
    public void givenStringWithEmptyValue_whenValidateIfStringIsNullOrEmpty_thenReturnTrue() {
        boolean isNullOrEmpty = StringValidator.isNullOrEmpty(STRING_EMPTY_VALUE);

        Assert.assertTrue(isNullOrEmpty);
    }

    @Test
    public void givenStringWithWhiteSpace_whenValidateIfStringIsNullOrEmpty_thenReturnTrue() {
        boolean isNullOrEmpty = StringValidator.isNullOrEmpty(STRING_WITH_WHITESPACE);

        Assert.assertTrue(isNullOrEmpty);
    }

    @Test
    public void givenStringWithValue_whenValidateIfStringIsNullOrEmpty_thenReturnFalse() {
        boolean isNullOrEmpty = StringValidator.isNullOrEmpty(STRING_WITH_DEFINE_VALUE);

        Assert.assertFalse(isNullOrEmpty);
    }
}
