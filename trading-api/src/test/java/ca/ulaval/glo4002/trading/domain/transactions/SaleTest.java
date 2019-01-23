package ca.ulaval.glo4002.trading.domain.transactions;

import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.domain.stocks.Stock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SaleTest {
    private static final BigDecimal A_STOCK_PRICE = BigDecimal.ONE;
    private static final TransactionType TRANSACTION_TYPE = TransactionType.SELL;
    private static final String A_SERIALIZED_VALID_DATE = "2017-03-21T02:04:03";
    private static final int VALID_QUANTITY = 4;

    private Transaction aTransaction;

    @Mock
    private Stock aStock;

    @Mock
    private Market aMarket;

    @Mock
    private TransactionFeesCalculator feesCalculator;

    @Before
    public void setup() {
        when(aMarket.isOpen(any())).thenReturn(true);

        LocalDateTime aValidDate = LocalDateTime.parse(A_SERIALIZED_VALID_DATE);
        aTransaction = new Transaction(aValidDate, aStock, VALID_QUANTITY, aMarket, TRANSACTION_TYPE);
    }

    @Test
    public void whenCreatingSale_thenTransactionIsAssociateToSellTransactionType() {
        new Sale(aTransaction, feesCalculator);

        Assert.assertEquals(TRANSACTION_TYPE, aTransaction.getType());
    }

    @Test
    public void whenCalculatingTotalPrice_thenReturnExpectedTotalPrice() {
        BigDecimal expectedTotalPrice = new BigDecimal(3);
        BigDecimal expectedRequestedAmount = new BigDecimal(4);
        Sale aSale = new Sale(aTransaction, feesCalculator);
        when(aStock.getPrice()).thenReturn(A_STOCK_PRICE);
        when(feesCalculator.calculateTransactionFees(VALID_QUANTITY, expectedRequestedAmount))
                .thenReturn(A_STOCK_PRICE);

        BigDecimal actualTotalPrice = aSale.calculateTotalPrice();

        Assert.assertEquals(expectedTotalPrice, actualTotalPrice);
    }
}
