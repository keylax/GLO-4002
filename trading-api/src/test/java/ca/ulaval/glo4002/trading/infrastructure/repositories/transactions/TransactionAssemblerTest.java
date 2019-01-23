package ca.ulaval.glo4002.trading.infrastructure.repositories.transactions;

import ca.ulaval.glo4002.trading.domain.transactions.Transaction;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionType;
import ca.ulaval.glo4002.trading.infrastructure.repositories.transactions.entities.TransactionEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionAssemblerTest {
    private static final BigDecimal A_STOCK_PRICE = BigDecimal.ONE;
    private static final String AN_ACCOUNT_NUMBER_STRING = "SD-3";
    private static final String A_MARKET_SYMBOL = "NASDAQ";
    private static final String A_STOCK_SYMBOL = "GOOG";
    private static final long A_QUANTITY = 1;
    private static final TransactionType BUY_TRANSACTION_TYPE = TransactionType.BUY;

    private TransactionAssembler factory;
    private LocalDateTime validDate;
    private UUID aTransactionNumber;

    @Before
    public void setUp() {
        factory = new TransactionAssembler();
        validDate = LocalDateTime.now();
        aTransactionNumber = UUID.randomUUID();
    }

    @Test
    public void whenAssemblingATransaction_thenReturnExpectedTransaction() {
        TransactionEntity transactionEntity = new TransactionEntity(
                aTransactionNumber,
                validDate,
                A_QUANTITY,
                A_STOCK_SYMBOL,
                A_MARKET_SYMBOL,
                A_STOCK_PRICE,
                AN_ACCOUNT_NUMBER_STRING,
                BUY_TRANSACTION_TYPE);

        Transaction transaction = factory.toDomainObject(transactionEntity);

        Assert.assertTrue(TransactionEntityComparer.areEquivalent(transaction, transactionEntity));
    }
}
