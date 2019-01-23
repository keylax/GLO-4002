package ca.ulaval.glo4002.trading.application.services.accounts;

import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses.WalletCurrencyResponse;
import ca.ulaval.glo4002.trading.domain.accounts.Account;
import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.domain.accounts.Wallet;
import ca.ulaval.glo4002.trading.domain.accounts.WalletCurrency;
import ca.ulaval.glo4002.trading.domain.investors.Investor;
import ca.ulaval.glo4002.trading.domain.investors.InvestorProfile;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses.AccountResponse;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses.InvestorProfileResponse;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountResponseAssemblerTest {
    private static final String AN_ACCOUNT_NUMBER = "bg-1";
    private static final long AN_INVESTOR_ID = 1;
    private static final String AN_INVESTOR_FIRST_NAME = "Bob";
    private static final String AN_INVESTOR_LAST_NAME = "Gratton";
    private static final String USD_CURRENCY = "USD";
    private static final float A_CREDIT_BALANCE = 0.45f;
    private static final BigDecimal A_CURRENCY_AMOUNT = BigDecimal.TEN;

    private AccountResponseAssembler assembler;

    @Before
    public void setup() {
        assembler = new AccountResponseAssembler();
    }

    @Test
    public void whenAssemblingAccountResponse_thenReturnExpectedAccountResponse() {
        Investor investor = new Investor(AN_INVESTOR_ID, AN_INVESTOR_FIRST_NAME, AN_INVESTOR_LAST_NAME);
        AccountNumber anAccountNumberObject = new AccountNumber(AN_ACCOUNT_NUMBER);
        ArrayList<String> currency = new ArrayList<>();
        currency.add(USD_CURRENCY);
        ArrayList<BigDecimal> currencyAmount = new ArrayList<>();
        currencyAmount.add(A_CURRENCY_AMOUNT);
        Wallet wallet = new Wallet(currency, currencyAmount);
        Account anAccount = new Account(investor, wallet, anAccountNumberObject, new ArrayList<>());

        AccountResponse actualResponse = assembler.toResponse(anAccount);

        Assert.assertTrue(areEquivalent(anAccount, actualResponse));
    }

    private boolean areEquivalent(Account _account, AccountResponse _accountResponse) {
        BigDecimal accountCredits = _account.getTotalCredits();
        BigDecimal accountResponseCredits = new BigDecimal(_accountResponse.total);

        boolean creditsAreEquivalent = accountResponseCredits.floatValue() == accountCredits.floatValue();

        AccountNumber accountNumber = _account.getAccountNumber();
        return  areEquivalent(_account.getInvestorProfile(), _accountResponse.investorProfile)
                && _account.getInvestorId() == _accountResponse.investorId
                && _accountResponse.accountNumber.equals(accountNumber.toString())
                && creditsAreEquivalent
                && areEquivalent(_account.getWallet(), _accountResponse.credits);
    }

    private boolean areEquivalent(InvestorProfile _profile, InvestorProfileResponse _investorProfileResponse) {
        return _profile.getFocusArea() == _investorProfileResponse.focusAreas
                && _profile.getInvestorType() == _investorProfileResponse.type;
    }

    private boolean areEquivalent(Wallet _wallet, WalletCurrencyResponse[] _responses) {
        boolean areEquivalent = true;
        for (int i = 0; i < _wallet.getWalletCurrencies().size(); i++) {
            WalletCurrency currency = _wallet.getWalletCurrencies().get(i);
            WalletCurrencyResponse currencyResponse = _responses[i];
            if (!currency.getCurrencySymbol().equals(currencyResponse.currency)
                || currency.getCredits().floatValue() != currencyResponse.amount) {
                areEquivalent = false;
            }
        }
        return areEquivalent;
    }
}
