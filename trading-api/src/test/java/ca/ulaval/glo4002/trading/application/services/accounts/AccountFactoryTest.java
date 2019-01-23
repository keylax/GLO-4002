package ca.ulaval.glo4002.trading.application.services.accounts;

import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.requests.OpenAccountRequest;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.requests.WalletCurrencyRequest;

import ca.ulaval.glo4002.trading.domain.accounts.Account;

import ca.ulaval.glo4002.trading.domain.accounts.WalletCurrency;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class AccountFactoryTest {
    private static final String AN_INVESTOR_FIRST_NAME = "anInvestorFirstName";
    private static final String AN_INVESTOR_LAST_NAME = "anInvestorLastName";
    private static final String USD_CURRENCY = "USD";
    private static final String AN_INVESTOR_NAME = AN_INVESTOR_FIRST_NAME + " " + AN_INVESTOR_LAST_NAME;
    private static final float A_CREDIT_BALANCE = 10.0f;
    private static final long A_INVESTOR_ID = 2;
    private static final double EPSILON = 0.00000001;

    private AccountFactory accountFactory;

    @Before
    public void setup() {
        accountFactory = new AccountFactory();
    }

    @Test
    public void givenAnOpenAccountRequest_whenCreatingAccount_thenReturnExpectedAccount() {
        OpenAccountRequest openAccountRequest = createOpenAccountRequest();

        Account actualAccount = accountFactory.createAccount(openAccountRequest);

        Assert.assertTrue(areEquivalent(openAccountRequest, actualAccount));
    }

    @Test
    public void whenCreatingAccount_thenFullInvestorNameIsSplitCorrectly() {
        OpenAccountRequest openAccountRequest = createOpenAccountRequest();

        Account actualAccount = accountFactory.createAccount(openAccountRequest);

        String actualFirstName = actualAccount.getInvestor().getFirstName();
        String actualLastName = actualAccount.getInvestor().getLastName();
        Assert.assertEquals(actualFirstName, AN_INVESTOR_FIRST_NAME);
        Assert.assertEquals(actualLastName, AN_INVESTOR_LAST_NAME);
    }

    private boolean areEquivalent(OpenAccountRequest _openAccountRequest, Account _account) {
        boolean areInvestorIDEquivalent = _openAccountRequest.investorId == _account.getInvestorId();
        boolean areCreditsEquivalent = true;
        float differenceInCredits;
        List<WalletCurrency> accountCurrencies = _account.getCurrencies();
        for (int i = 0; i < _openAccountRequest.credits.length; i++) {
            float creditAmountInCreatedAccount = accountCurrencies.get(i).getCredits().floatValue();
            float creditAmountInRequest = _openAccountRequest.credits[i].amount;
            differenceInCredits = creditAmountInRequest - creditAmountInCreatedAccount;

            if (!(Math.abs(differenceInCredits) < EPSILON)) {
                areCreditsEquivalent = false;
            }
        }

        return areInvestorIDEquivalent && areCreditsEquivalent;
    }

    private OpenAccountRequest createOpenAccountRequest() {
        OpenAccountRequest openAccountRequest = new OpenAccountRequest();
        openAccountRequest.investorName = AN_INVESTOR_NAME;

        WalletCurrencyRequest walletCurrencyRequest = new WalletCurrencyRequest();
        walletCurrencyRequest.currency = USD_CURRENCY;
        walletCurrencyRequest.amount = A_CREDIT_BALANCE;

        WalletCurrencyRequest[] walletCurrencyRequests = new WalletCurrencyRequest[1];
        walletCurrencyRequests[0] = walletCurrencyRequest;

        openAccountRequest.credits = walletCurrencyRequests;
        openAccountRequest.investorId = A_INVESTOR_ID;
        return openAccountRequest;
    }
}
