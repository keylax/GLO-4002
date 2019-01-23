package ca.ulaval.glo4002.trading.interfaces.rest.accounts;

import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses.InvestorProfileResponse;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.requests.OpenAccountRequest;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses.AccountResponse;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses.WalletCurrencyResponse;
import ca.ulaval.glo4002.trading.interfaces.rest.accounts.exceptions.AccountRequestInvalidException;
import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.interfaces.rest.accounts.validators.AccountRequestValidator;
import ca.ulaval.glo4002.trading.application.services.accounts.AccountService;

import javax.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.ArrayList;

import ca.ulaval.glo4002.trading.utilities.CurrencyConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountResourceTest {
    private static final String LOCATION_HEADER = "Location";
    private static final String OPEN_ACCOUNT_RESPONSE_LOCATION = "/accounts/";
    private static final String AN_INVESTOR_TYPE = "investorType";
    private static final String USD_CURRENCY = "USD";
    private static final String AN_ACCOUNT_NUMBER = "JC-2";
    private static final int HTML_STATUS_CREATED = 201;
    private static final long AN_INVESTOR_ID = 1;
    private static final float SOME_CREDITS = 5.85f;

    private AccountResource resource;
    private AccountNumber anAccountNumber;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountRequestValidator accountRequestValidator;

    @Before
    public void setup() {
        resource = new AccountResource(accountService, accountRequestValidator);
        anAccountNumber = new AccountNumber(AN_ACCOUNT_NUMBER);
    }

    @Test
    public void whenAnAccountIsOpen_thenReturnResponseWithExpectedLocationHeaderAndOkHtmlStatus() {
        String expectedLocation = OPEN_ACCOUNT_RESPONSE_LOCATION + AN_ACCOUNT_NUMBER;
        OpenAccountRequest openAccountRequest = new OpenAccountRequest();
        when(accountRequestValidator.isAccountRequestValid(openAccountRequest)).thenReturn(true);
        when(accountService.openAccount(openAccountRequest)).thenReturn(AN_ACCOUNT_NUMBER);

        Response actualResponse = resource.openAccount(openAccountRequest);

        int actualHtmlStatus = actualResponse.getStatus();
        String actualLocation = actualResponse.getHeaderString(LOCATION_HEADER);
        Assert.assertEquals(HTML_STATUS_CREATED, actualHtmlStatus);
        Assert.assertEquals(expectedLocation, actualLocation);
    }

    @Test(expected = AccountRequestInvalidException.class)
    public void givenAnInvalidOpenAccountRequest_whenAnAccountIsOpen_thenThrowException() {
        OpenAccountRequest openAccountRequest = new OpenAccountRequest();
        when(accountRequestValidator.isAccountRequestValid(openAccountRequest)).thenReturn(false);

        resource.openAccount(openAccountRequest);
    }

    @Test
    public void whenAnAccountIsConsulted_thenReturnExpectedAccountResponse() {
        AccountResponse expectedResponse = createAccountResponse();
        when(accountService.consultAccount(AN_ACCOUNT_NUMBER)).thenReturn(expectedResponse);

        AccountResponse actualResponse = resource.consultAccount(AN_ACCOUNT_NUMBER);

        Assert.assertEquals(expectedResponse, actualResponse);
    }

    private AccountResponse createAccountResponse() {
        InvestorProfileResponse investorProfileResponse = new InvestorProfileResponse(
                AN_INVESTOR_TYPE,
                new ArrayList<>());

        WalletCurrencyResponse walletCurrencyResponse = new WalletCurrencyResponse(USD_CURRENCY, SOME_CREDITS);

        WalletCurrencyResponse[] walletCurrencyResponses = new WalletCurrencyResponse[1];
        walletCurrencyResponses[0] = walletCurrencyResponse;
        BigDecimal totalCredits = CurrencyConverter
                .convertToCAD(new BigDecimal(SOME_CREDITS), USD_CURRENCY);

        return new AccountResponse(
                anAccountNumber.toString(),
                AN_INVESTOR_ID,
                walletCurrencyResponses,
                investorProfileResponse,
                totalCredits.floatValue());
    }
}
