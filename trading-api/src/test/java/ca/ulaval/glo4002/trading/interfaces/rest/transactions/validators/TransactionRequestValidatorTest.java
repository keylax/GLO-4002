package ca.ulaval.glo4002.trading.interfaces.rest.transactions.validators;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.StockRequest;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.TransactionRequest;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionRequestValidatorTest {
    private static final String A_STOCK_MARKET = "NASDAQ";
    private static final String A_STOCK_SYMBOL = "GOOG";
    private static final String A_TRANSACTION_TYPE = "SELL";
    private static final String A_SERIALIZED_DATE = "2016-01-04T9:24:20.142Z";
    private static final long A_TRANSACTION_QUANTITY = 1;

    private TransactionRequestValidator validator;
    private StockRequest stockRequest;
    private TransactionRequest transactionRequest;

    @Mock
    private StockRequestValidator stockRequestValidator;

    @Before
    public void setup() {
        stockRequest = new StockRequest();
        stockRequest.market = A_STOCK_MARKET;
        stockRequest.symbol = A_STOCK_SYMBOL;

        transactionRequest = new TransactionRequest();
        transactionRequest.stock = stockRequest;
        transactionRequest.type = A_TRANSACTION_TYPE;
        transactionRequest.date = A_SERIALIZED_DATE;
        transactionRequest.quantity = A_TRANSACTION_QUANTITY;
        transactionRequest.transactionNumber = UUID.randomUUID();

        validator = new TransactionRequestValidator(stockRequestValidator);
    }

    @Test
    public void givenValidTransactionRequest_whenValidating_returnTrue() {
        when(stockRequestValidator.isStockRequestValid(stockRequest)).thenReturn(true);

        boolean isTransactionValid = validator.isTransactionRequestValid(transactionRequest);

        Assert.assertTrue(isTransactionValid);
    }

    @Test
    public void givenTransactionRequestWithNullStock_whenValidating_returnFalse() {
        when(stockRequestValidator.isStockRequestValid(stockRequest)).thenReturn(false);

        boolean isTransactionValid = validator.isTransactionRequestValid(transactionRequest);

        Assert.assertFalse(isTransactionValid);
    }

    @Test
    public void givenTransactionRequestWithNullDate_whenValidating_returnFalse() {
        when(stockRequestValidator.isStockRequestValid(stockRequest)).thenReturn(true);
        transactionRequest.date = null;

        boolean isTransactionValid = validator.isTransactionRequestValid(transactionRequest);

        Assert.assertFalse(isTransactionValid);
    }

    @Test
    public void givenTransactionRequestWithNullType_whenValidating_returnFalse() {
        when(stockRequestValidator.isStockRequestValid(stockRequest)).thenReturn(true);
        transactionRequest.type = null;

        boolean isTransactionValid = validator.isTransactionRequestValid(transactionRequest);

        Assert.assertFalse(isTransactionValid);
    }
}
