package ca.ulaval.glo4002.trading.domain.transactions;

import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.domain.stocks.Stock;
import ca.ulaval.glo4002.trading.domain.transactions.exceptions.InvalidQuantityException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import ca.ulaval.glo4002.trading.domain.transactions.exceptions.MarketIsClosedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionTest {
    private static final String AN_ACCOUNT_NUMBER = "AN-1";
    private static final TransactionType A_TRANSACTION_TYPE = TransactionType.BUY;
    private static final String A_SYMBOL = "symbol";
    private static final String A_MARKET = "market";
    private static final String A_SERIALIZED_VALID_DATE = "2017-03-21T02:04:03";
    private static final BigDecimal A_PRICE = BigDecimal.TEN;
    private static final int VALID_QUANTITY = 5;

    @Mock
    private Stock aStock;

    @Mock
    private Market aMarket;

    private LocalDateTime aValidDate;

    @Before
    public void setup() {
        aValidDate = LocalDateTime.parse(A_SERIALIZED_VALID_DATE);

        when(aMarket.isOpen(aValidDate)).thenReturn(true);
        when(aStock.getSymbol()).thenReturn(A_SYMBOL);
        when(aStock.getPrice()).thenReturn(A_PRICE);
        when(aStock.getMarketSymbol()).thenReturn(A_MARKET);
    }

    @Test(expected = InvalidQuantityException.class)
    public void givenNullQuantity_whenCreatingTransaction_thenThrowInvalidQuantityException() {
        int nullQuantity = 0;

        new Transaction(aValidDate, aStock, nullQuantity, aMarket, A_TRANSACTION_TYPE);
    }

    @Test(expected = InvalidQuantityException.class)
    public void givenNegativeQuantity_whenCreatingTransaction_thenInvalidQuantityException() {
        int negativeQuantity = -3;

        new Transaction(aValidDate, aStock, negativeQuantity, aMarket, A_TRANSACTION_TYPE);
    }

    @Test(expected = MarketIsClosedException.class)
    public void whenCreatingTransactionAndMarketIsClosed_thenThrowBadRequestTransactionException() {
        when(aMarket.isOpen(aValidDate)).thenReturn(false);

        new Transaction(aValidDate, aStock, VALID_QUANTITY, aMarket, A_TRANSACTION_TYPE);
    }

    @Test
    public void whenAssociatingAccount_thenAccountIsAssociateWithExpectedNumber() {
        Transaction aTransaction = new Transaction(aValidDate, aStock, VALID_QUANTITY, aMarket, A_TRANSACTION_TYPE);
        AccountNumber expectedAccountNumber = new AccountNumber(AN_ACCOUNT_NUMBER);

        aTransaction.associateAccount(expectedAccountNumber);
        AccountNumber actualAccountNumber = aTransaction.getAssociatedAccountNumber();

        Assert.assertEquals(expectedAccountNumber, actualAccountNumber);
    }

    @Test(expected = IllegalStateException.class)
    public void givenATransactionWithAssociatedAccount_whenAssociatingAccount_thenAnExceptionIsThrown() {
        Transaction aTransaction = new Transaction(aValidDate, aStock, VALID_QUANTITY, aMarket, A_TRANSACTION_TYPE);
        AccountNumber expectedAccountNumber = new AccountNumber(AN_ACCOUNT_NUMBER);
        aTransaction.associateAccount(expectedAccountNumber);

        aTransaction.associateAccount(expectedAccountNumber);
    }

    @Test
    public void whenCalculatingRequestedAmount_thenReturnExpectedAmount() {
        BigDecimal expectedQuantity = BigDecimal.valueOf(VALID_QUANTITY);
        BigDecimal expectedRequestedAmount = A_PRICE.multiply(expectedQuantity);
        Transaction aTransaction = new Transaction(aValidDate, aStock, VALID_QUANTITY, aMarket, A_TRANSACTION_TYPE);

        BigDecimal actualRequestedAmount = aTransaction.calculateRequestedAmount();

        Assert.assertEquals(expectedRequestedAmount, actualRequestedAmount);
    }

    @Test
    public void whenCalculatingTransactionFees_thenReturnExpectedFees() {
        TransactionFeesCalculator transactionFeesCalculator = mock(TransactionFeesCalculator.class);
        BigDecimal expectedTransactionFees = BigDecimal.ONE;
        BigDecimal expectedQuantity = BigDecimal.valueOf(VALID_QUANTITY);
        BigDecimal expectedRequestedAmount = A_PRICE.multiply(expectedQuantity);
        when(transactionFeesCalculator.calculateTransactionFees(VALID_QUANTITY, expectedRequestedAmount))
                .thenReturn(expectedTransactionFees);
        Transaction aTransaction = new Transaction(aValidDate, aStock, VALID_QUANTITY, aMarket, A_TRANSACTION_TYPE);

        BigDecimal actualTransactionFees = aTransaction.calculateFees(transactionFeesCalculator);

        Assert.assertEquals(expectedTransactionFees, actualTransactionFees);
    }
}
