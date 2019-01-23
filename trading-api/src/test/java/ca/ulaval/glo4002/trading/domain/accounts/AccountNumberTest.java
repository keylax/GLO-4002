package ca.ulaval.glo4002.trading.domain.accounts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountNumberTest {
    private static final String AN_OTHER_ACCOUNT_NUMBER_STRING = "bk-91";
    private static final String EXPECTED_ACCOUNT_NUMBER_STRING = "as-1";
    private static final String SOME_ACCOUNT_INITIALS = "as";
    private static final int AN_ACCOUNT_ID = 1;

    private AccountNumber anAccountNumber;

    @Before
    public void setup() {
        anAccountNumber = new AccountNumber(AN_ACCOUNT_ID, SOME_ACCOUNT_INITIALS);
    }

    @Test
    public void givenEquivalentAccountNumbers_whenCompared_thenShouldBeEqual() {
        AccountNumber anEquivalentAccountNumber = new AccountNumber(EXPECTED_ACCOUNT_NUMBER_STRING);

        Assert.assertEquals(anAccountNumber, anEquivalentAccountNumber);
    }

    @Test
    public void givenDifferentAccountNumbers_whenVCompared_thenShouldNotBeEqual() {
        AccountNumber anOtherAccountNumber = new AccountNumber(AN_OTHER_ACCOUNT_NUMBER_STRING);

        Assert.assertNotEquals(anAccountNumber, anOtherAccountNumber);
    }

    @Test
    public void whenUsingToString_thenReturnCompleteAccountNumber() {
        String returnedString = anAccountNumber.toString();

        Assert.assertEquals(EXPECTED_ACCOUNT_NUMBER_STRING, returnedString);
    }
}
