package ca.ulaval.glo4002.trading.domain.transactions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;


public class TransactionNumberTest {

    private TransactionNumber aTransactionNumber;

    @Before
    public void setup() {
        aTransactionNumber = new TransactionNumber();
    }

    @Test
    public void given2EquivalentAccountNumber_whenValidatingEquals_thenReturnsTrue() {
        UUID transactionUUID = aTransactionNumber.getNumber();
        TransactionNumber anEquivalentTransactionNumber = new TransactionNumber(transactionUUID);

        Assert.assertEquals(anEquivalentTransactionNumber, aTransactionNumber);
    }

    @Test
    public void given2DifferentAccountNumber_whenValidatingEquals_thenReturnsFalse() {
        TransactionNumber anOtherAccountNumber = new TransactionNumber();

        Assert.assertNotEquals(anOtherAccountNumber, aTransactionNumber);
    }
}
