package ca.ulaval.glo4002.trading.domain.investors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InvestorTest {
    private static final String AN_INVESTOR_FIRST_NAME = "Adam";
    private static final String AN_INVESTOR_LAST_NAME = "Eve";
    private static final long AN_INVESTOR_ID = 1;

    private Investor investor;

    @Before
    public void setup() {
        investor = new Investor(AN_INVESTOR_ID, AN_INVESTOR_FIRST_NAME, AN_INVESTOR_LAST_NAME);
    }

    @Test
    public void whenGetInvestorInitials_returnFirstLetterOfFirstAndLastName() {
        String returnedInitials = investor.getInvestorInitials();

        String expectedInitials = "AE";
        Assert.assertEquals(expectedInitials, returnedInitials);
    }
}
