package ca.ulaval.glo4002.trading.infrastructure.repositories.transactions;

import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.domain.stocks.Stock;
import ca.ulaval.glo4002.trading.domain.transactions.Transaction;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionType;
import ca.ulaval.glo4002.trading.infrastructure.repositories.transactions.entities.TransactionEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionEntityFactoryTest {
    private static final BigDecimal A_STOCK_PRICE = BigDecimal.ONE;
    private static final String AN_ACCOUNT_NUMBER_STRING = "SD-3";
    private static final String A_MARKET_SYMBOL = "NASDAQ";
    private static final String A_STOCK_SYMBOL = "GOOG";
    private static final long A_QUANTITY = 1;
    private static final TransactionType BUY_TRANSACTION_TYPE = TransactionType.BUY;

    private TransactionEntityFactory assembler;
    private LocalDateTime aValidDate;
    private AccountNumber anAccountNumber;

    @Mock
    private Stock aStock;

    @Mock
    private Market aMarket;

    @Before
    public void setUp() {
        assembler = new TransactionEntityFactory();
        aValidDate = LocalDateTime.now();
        anAccountNumber = new AccountNumber(AN_ACCOUNT_NUMBER_STRING);
        when(aStock.getSymbol()).thenReturn(A_STOCK_SYMBOL);
        when(aStock.getPrice()).thenReturn(A_STOCK_PRICE);
        when(aStock.getMarketSymbol()).thenReturn(A_MARKET_SYMBOL);
        when(aMarket.isOpen(aValidDate)).thenReturn(true);
    }

    @Test
    public void whenAssemblingATransactionEntity_thenReturnExpectedTransactionEntity() {
        Transaction transaction = new Transaction(aValidDate, aStock, A_QUANTITY, aMarket, BUY_TRANSACTION_TYPE);
        transaction.associateAccount(anAccountNumber);

        TransactionEntity transactionEntity = assembler.createTransactionEntity(transaction);

        Assert.assertTrue(TransactionEntityComparer.areEquivalent(transaction, transactionEntity));
    }
}
