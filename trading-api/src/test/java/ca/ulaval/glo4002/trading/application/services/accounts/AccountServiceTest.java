package ca.ulaval.glo4002.trading.application.services.accounts;

import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses.WalletCurrencyResponse;
import ca.ulaval.glo4002.trading.domain.accounts.Account;
import ca.ulaval.glo4002.trading.domain.accounts.Wallet;
import ca.ulaval.glo4002.trading.domain.accounts.AccountRepository;
import ca.ulaval.glo4002.trading.domain.investors.Investor;
import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.requests.OpenAccountRequest;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses.AccountResponse;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses.InvestorProfileResponse;

import java.util.ArrayList;
import java.math.BigDecimal;

import ca.ulaval.glo4002.trading.utilities.CurrencyConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    private static final String AN_ACCOUNT_NUMBER_STRING = "BG-0";
    private static final String USD_CURRENCY = "USD";
    private static final String AN_INVESTOR_FIRST_NAME = "Bob";
    private static final String AN_INVESTOR_LAST_NAME = "Gratton";
    private static final String AN_INVESTOR_TYPE = "investorType";
    private static final float SOME_CREDITS = 5.85f;
    private static final long AN_INVESTOR_ID = 1;
    private static final int AN_ACCOUNT_ID = 0;

    private AccountService accountService;
    private Account anAccount;
    private AccountNumber anAccountNumber;
    ArrayList<String> walletCurrencies;
    ArrayList<BigDecimal> walletCredits;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountFactory accountFactory;

    @Mock
    private AccountResponseAssembler accountResponseAssembler;
    
    @Before
    public void setup() {
        accountService = new AccountService(accountRepository, accountFactory, accountResponseAssembler);
        Investor investor = new Investor(AN_INVESTOR_ID, AN_INVESTOR_FIRST_NAME, AN_INVESTOR_LAST_NAME);
        BigDecimal someCredit = new BigDecimal(SOME_CREDITS);
        walletCredits = new ArrayList<>();
        walletCredits.add(someCredit);
        walletCurrencies = new ArrayList<>();
        walletCurrencies.add(USD_CURRENCY);
        Wallet wallet = new Wallet(walletCurrencies, walletCredits);
        anAccount = new Account(investor, wallet);
        anAccountNumber = new AccountNumber(AN_ACCOUNT_NUMBER_STRING);
    }
        
    @Test
    public void whenAnAccountIsOpened_thenItIsAddedToTheRepository() {
        OpenAccountRequest openAccountRequest = new OpenAccountRequest();
        when(accountFactory.createAccount(openAccountRequest)).thenReturn(anAccount);
        when(accountRepository.persistAccount(anAccount)).thenReturn(AN_ACCOUNT_ID);

        accountService.openAccount(openAccountRequest);

        verify(accountRepository).persistAccount(anAccount);
    }

    @Test
    public void whenAnAccountIsOpened_thenTheAccountNumberIsReturned() {
        OpenAccountRequest openAccountRequest = new OpenAccountRequest();
        when(accountFactory.createAccount(openAccountRequest)).thenReturn(anAccount);
        when(accountRepository.persistAccount(anAccount)).thenReturn(AN_ACCOUNT_ID);

        String actualAccountNumber = accountService.openAccount(openAccountRequest);

        Assert.assertEquals(AN_ACCOUNT_NUMBER_STRING, actualAccountNumber);
    }

    @Test
    public void whenAnAccountIsSelected_thenTheRightAccountIsReturned() {
        AccountResponse expectedResponse = createAccountResponse();
        when(accountRepository.findAccountByNumber(anAccountNumber)).thenReturn(anAccount);
        when(accountResponseAssembler.toResponse(anAccount)).thenReturn(expectedResponse);

        AccountResponse actualAccountResponse = accountService.consultAccount(
                AN_ACCOUNT_NUMBER_STRING);
        
        Assert.assertEquals(expectedResponse, actualAccountResponse);
    }

    private AccountResponse createAccountResponse() {
        InvestorProfileResponse investorProfileResponse = new InvestorProfileResponse(
                AN_INVESTOR_TYPE,
                new ArrayList<>());

        WalletCurrencyResponse walletCurrencyResponse = new WalletCurrencyResponse(USD_CURRENCY, SOME_CREDITS);

        WalletCurrencyResponse[] walletCurrencyResponses = new WalletCurrencyResponse[1];
        walletCurrencyResponses[0] = walletCurrencyResponse;
        BigDecimal totalCredits = CurrencyConverter.convertToCAD(new BigDecimal(SOME_CREDITS), USD_CURRENCY);

        return new AccountResponse(
                anAccountNumber.toString(),
                AN_INVESTOR_ID,
                walletCurrencyResponses,
                investorProfileResponse,
                totalCredits.floatValue());
    }
}
