package ca.ulaval.glo4002.trading.interfaces.rest.accounts.validators;

import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.requests.OpenAccountRequest;

import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.requests.WalletCurrencyRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountRequestValidatorTest {
    private static final String A_VALID_INVESTOR_NAME = "Valid Name";
    private static final String AN_INVALID_INVESTOR_NAME = "1";
    private static final String AN_EMAIL = "test@test.ca";
    private static final String VALID_CURRENCY = "USD";
    
    private static final float SOME_CREDITS = 123.45f;
    private static final long AN_INVESTOR_ID = 1;

    private OpenAccountRequest accountRequest;
    private AccountRequestValidator validator;

    @Before
    public void setup() {
        accountRequest = new OpenAccountRequest();
        WalletCurrencyRequest walletCurrencyRequest = new WalletCurrencyRequest();
        walletCurrencyRequest.currency = VALID_CURRENCY;
        walletCurrencyRequest.amount = SOME_CREDITS;
        WalletCurrencyRequest[] walletCurrencyRequests = new WalletCurrencyRequest[1];
        walletCurrencyRequests[0] = walletCurrencyRequest;
        accountRequest.credits = walletCurrencyRequests;
        accountRequest.email = AN_EMAIL;
        accountRequest.investorId = AN_INVESTOR_ID;
        accountRequest.investorName = A_VALID_INVESTOR_NAME;

        validator = new AccountRequestValidator();
    }

    @Test
    public void givenValidAccountRequest_whenValidating_thenReturnTrue() {
        boolean isAccountValid = validator.isAccountRequestValid(accountRequest);

        Assert.assertTrue(isAccountValid);
    }

    @Test
    public void givenAccountRequestWithNullEmail_whenValidating_thenReturnFalse() {
        accountRequest.email = null;

        boolean isAccountValid = validator.isAccountRequestValid(accountRequest);

        Assert.assertFalse(isAccountValid);

    }

    @Test
    public void givenAccountRequestWithNullName_whenValidation_thenReturnFalse() {
        accountRequest.investorName = null;

        boolean isAccountValid = validator.isAccountRequestValid(accountRequest);

        Assert.assertFalse(isAccountValid);
    }

    @Test
    public void givenAccountRequestWithInvalidName_whenValidation_thenReturnFalse() {
        accountRequest.investorName = AN_INVALID_INVESTOR_NAME;

        boolean isAccountValid = validator.isAccountRequestValid(accountRequest);

        Assert.assertFalse(isAccountValid);
    }

    @Test
    public void givenAccountRequestWithNullCredits_whenValidation_thenReturnFalse() {
        accountRequest.credits = null;

        boolean isAccountValid = validator.isAccountRequestValid(accountRequest);

        Assert.assertFalse(isAccountValid);
    }
}
