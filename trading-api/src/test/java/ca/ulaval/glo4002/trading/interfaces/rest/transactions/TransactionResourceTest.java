package ca.ulaval.glo4002.trading.interfaces.rest.transactions;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.StockRequest;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.TransactionRequest;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionResponse;
import ca.ulaval.glo4002.trading.application.services.transactions.TransactionService;
import ca.ulaval.glo4002.trading.interfaces.rest.helpers.TransactionResponseHelper;
import ca.ulaval.glo4002.trading.interfaces.rest.transactions.exceptions.TransactionDtoInvalidException;
import ca.ulaval.glo4002.trading.interfaces.rest.transactions.exceptions.UnsupportedTransactionException;
import ca.ulaval.glo4002.trading.interfaces.rest.transactions.validators.TransactionRequestValidator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionResourceTest {
    private static final String A_SERIALIZED_DATE = "2016-01-04T9:24:20.142Z";
    private static final String ACCOUNTS_LOCATION = "/accounts/";
    private static final String TRANSACTION_LOCATION = "/transactions/";
    private static final String LOCATION_HEADER = "Location";
    private static final String SELL_TRANSACTION_TYPE = "SELL";
    private static final String BUY_TRANSACTION_TYPE = "BUY";
    private static final String A_VALID_TRANSACTION_TYPE = BUY_TRANSACTION_TYPE;
    private static final String AN_INVALID_TRANSACTION_TYPE = "anInvalidType";
    private static final String AN_ACCOUNT_NUMBER = "AE-1";
    private static final String A_MARKET = "aMarket";
    private static final String A_SYMBOL = "aSymbol";
    private static final int HTML_STATUS_CREATED = 201;
    private static final long A_QUANTITY = 2;

    private TransactionResource resource;
    private UUID aTransactionNumber;

    @Mock
    private TransactionService service;

    @Mock
    private TransactionRequestValidator validator;

    @Before
    public void setup() {
        aTransactionNumber = UUID.randomUUID();
        resource = new TransactionResource(service, validator);
    }

    @Test(expected = TransactionDtoInvalidException.class)
    public void givenAnInvalidTransactionRequest_whenPerformingATransaction_thenThrowAnExceptionAndServiceDoesNotPerformTransaction() {
        TransactionRequest aTransactionRequest = createATransactionRequest(A_VALID_TRANSACTION_TYPE);
        when(validator.isTransactionRequestValid(aTransactionRequest)).thenReturn(false);

        try {
            resource.performTransaction(AN_ACCOUNT_NUMBER, aTransactionRequest);
        } finally {
            verify(service, never()).performBuy(any(TransactionRequest.class), anyString());
            verify(service, never()).performSell(
                    any(TransactionRequest.class),
                    anyString(),
                    any(UUID.class));
        }
    }

    @Test(expected = UnsupportedTransactionException.class)
    public void givenATransactionRequestWithAnInvalidType_whenPerformingATransaction_thenThrowAnExceptionAndServiceDoesNotPerformTransaction() {
        TransactionRequest aTransactionRequest = createATransactionRequest(AN_INVALID_TRANSACTION_TYPE);
        when(validator.isTransactionRequestValid(aTransactionRequest)).thenReturn(true);

        try {
            resource.performTransaction(AN_ACCOUNT_NUMBER, aTransactionRequest);
        } finally {
            verify(service, never()).performBuy(any(TransactionRequest.class), anyString());
            verify(service, never()).performSell(
                    any(TransactionRequest.class),
                    anyString(),
                    any(UUID.class));
        }
    }

    @Test
    public void givenATransactionRequestWithABuyTransactionType_whenPerformingATransaction_thenServicePerformBuy() {
        TransactionRequest aTransactionRequest = createATransactionRequest(BUY_TRANSACTION_TYPE);
        when(service.performBuy(aTransactionRequest, AN_ACCOUNT_NUMBER)).thenReturn(aTransactionNumber);
        when(validator.isTransactionRequestValid(aTransactionRequest)).thenReturn(true);

        resource.performTransaction(AN_ACCOUNT_NUMBER, aTransactionRequest);

        verify(service).performBuy(aTransactionRequest, AN_ACCOUNT_NUMBER);
    }

    @Test
    public void givenATransactionRequestWithABuyTransactionType_whenPerformingATransaction_thenReturnExpectedResponseWithOkHtmlStatus() {
        String expectedLocation = ACCOUNTS_LOCATION + AN_ACCOUNT_NUMBER + TRANSACTION_LOCATION
                + aTransactionNumber.toString();
        TransactionRequest aTransactionRequest = createATransactionRequest(BUY_TRANSACTION_TYPE);
        when(service.performBuy(aTransactionRequest, AN_ACCOUNT_NUMBER)).thenReturn(aTransactionNumber);
        when(validator.isTransactionRequestValid(aTransactionRequest)).thenReturn(true);

        Response actualResponse = resource.performTransaction(AN_ACCOUNT_NUMBER, aTransactionRequest);

        int actualHtmlStatus = actualResponse.getStatus();
        String actualLocation = actualResponse.getHeaderString(LOCATION_HEADER);
        Assert.assertEquals(HTML_STATUS_CREATED, actualHtmlStatus);
        Assert.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    public void givenATransactionRequestWithASellTransactionType_whenPerformingATransaction_thenServicePerformSell() {
        UUID aBuyTransactionNumber = UUID.randomUUID();
        TransactionRequest aTransactionRequest = createATransactionRequest(SELL_TRANSACTION_TYPE);
        aTransactionRequest.transactionNumber = aBuyTransactionNumber;
        when(validator.isTransactionRequestValid(aTransactionRequest)).thenReturn(true);
        when(service.performSell(aTransactionRequest, AN_ACCOUNT_NUMBER, aBuyTransactionNumber))
                .thenReturn(aTransactionNumber);

        resource.performTransaction(AN_ACCOUNT_NUMBER, aTransactionRequest);

        verify(service).performSell(aTransactionRequest, AN_ACCOUNT_NUMBER, aBuyTransactionNumber);
    }

    @Test
    public void givenATransactionRequestWithASellTransactionType_whenPerformingATransaction_thenReturnExpectedResponseWithOkHtmlStatus() {
        UUID aBuyTransactionNumber = UUID.randomUUID();
        String expectedLocation = ACCOUNTS_LOCATION + AN_ACCOUNT_NUMBER + TRANSACTION_LOCATION
                + aTransactionNumber.toString();
        TransactionRequest aTransactionRequest = createATransactionRequest(SELL_TRANSACTION_TYPE);
        aTransactionRequest.transactionNumber = aBuyTransactionNumber;
        when(validator.isTransactionRequestValid(aTransactionRequest)).thenReturn(true);
        when(service.performSell(aTransactionRequest, AN_ACCOUNT_NUMBER, aBuyTransactionNumber))
                .thenReturn(aTransactionNumber);

        Response actualResponse = resource.performTransaction(AN_ACCOUNT_NUMBER, aTransactionRequest);

        int actualHtmlStatus = actualResponse.getStatus();
        String actualLocation = actualResponse.getHeaderString(LOCATION_HEADER);
        Assert.assertEquals(HTML_STATUS_CREATED, actualHtmlStatus);
        Assert.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    public void whenConsultingTransaction_thenReturnExpectedTransactionResponse() {
        TransactionResponse expectedTransactionResponse = TransactionResponseHelper.createATransactionResponse(
                aTransactionNumber);
        when(service.consultTransaction(aTransactionNumber, AN_ACCOUNT_NUMBER))
            .thenReturn(expectedTransactionResponse);

        TransactionResponse actualResponse = resource.consultTransaction(AN_ACCOUNT_NUMBER, aTransactionNumber);

        Assert.assertEquals(expectedTransactionResponse, actualResponse);
    }

    private TransactionRequest createATransactionRequest(String _transactionType) {
        StockRequest aStockRequest = new StockRequest();
        aStockRequest.market = A_MARKET;
        aStockRequest.symbol = A_SYMBOL;

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.date = A_SERIALIZED_DATE;
        transactionRequest.quantity = A_QUANTITY;
        transactionRequest.stock = aStockRequest;
        transactionRequest.type = _transactionType;
        return transactionRequest;
    }
}
