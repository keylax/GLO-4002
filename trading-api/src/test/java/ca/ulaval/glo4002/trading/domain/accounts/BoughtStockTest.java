package ca.ulaval.glo4002.trading.domain.accounts;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.NotEnoughStockException;
import ca.ulaval.glo4002.trading.domain.accounts.exceptions.StockParametersDontMatchException;
import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.domain.stocks.Stock;
import ca.ulaval.glo4002.trading.domain.transactions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BoughtStockTest {

    private static final String A_MARKET = "market";
    private static final String A_DIFFERENT_MARKET = "differentMarket";
    private static final String A_SYMBOL = "symbol";
    private static final String A_DIFFERENT_SYMBOL = "differentSymbol";
    private static final TransactionType BUY_TRANSACTION_TYPE = TransactionType.BUY;
    private static final TransactionType SELL_TRANSACTION_TYPE = TransactionType.SELL;
    private static final BigDecimal A_PRICE = BigDecimal.TEN;
    private static final int A_VALID_QUANTITY = 2;

    @Mock
    private Stock aStock;

    @Mock
    private Market aMarket;

    private BoughtStock aBoughtStock;
    private LocalDateTime aDate;

    @Before
    public void setup() {
        when(aMarket.isOpen(any())).thenReturn(true);

        aDate = LocalDateTime.now();
        stubStockBehaviours(aStock, A_MARKET, A_SYMBOL);
        Transaction associatedTransaction = new Transaction(aDate,
                                                            aStock,
                                                            A_VALID_QUANTITY,
                                                            aMarket,
                                                            BUY_TRANSACTION_TYPE);
        Purchase aPurchase = new Purchase(associatedTransaction, new TransactionFeesCalculator());
        aBoughtStock = new BoughtStock(aPurchase);

    }

    @Test(expected = NotEnoughStockException.class)
    public void givenATransactionWithGreaterQuantityThenStock_whenSellingStock_thenThrowBadRequestTransactionException() {
        Transaction aTransaction = new Transaction(aDate,
                                                   aStock,
                                                   A_VALID_QUANTITY + 1,
                                                   aMarket,
                                                   SELL_TRANSACTION_TYPE);
        Sale aSale = new Sale(aTransaction, new TransactionFeesCalculator());

        aBoughtStock.sellStock(aSale);
    }

    @Test(expected = StockParametersDontMatchException.class)
    public void givenATransactionWithAMarketDifferentThenStock_whenSellingStock_thenThrowStockParametersDontMatchException() {
        Stock aStockWithDifferentMarket = mock(Stock.class);
        stubStockBehaviours(aStockWithDifferentMarket, A_DIFFERENT_MARKET, A_SYMBOL);
        Transaction aTransaction = new Transaction(aDate,
                                                   aStockWithDifferentMarket,
                                                   A_VALID_QUANTITY,
                                                   aMarket,
                                                   SELL_TRANSACTION_TYPE);
        Sale aSale = new Sale(aTransaction, new TransactionFeesCalculator());

        aBoughtStock.sellStock(aSale);
    }

    @Test(expected = StockParametersDontMatchException.class)
    public void givenATransactionWithASymbolDifferentThenStock_whenSellingStock_thenThrowStockParametersDontMatchException() {
        Stock aStockWithDifferentSymbol = mock(Stock.class);
        stubStockBehaviours(aStockWithDifferentSymbol, A_MARKET, A_DIFFERENT_SYMBOL);
        Transaction aTransaction = new Transaction(aDate,
                                                   aStockWithDifferentSymbol,
                                                   A_VALID_QUANTITY,
                                                   aMarket,
                                                   SELL_TRANSACTION_TYPE);
        Sale aSale = new Sale(aTransaction, new TransactionFeesCalculator());

        aBoughtStock.sellStock(aSale);
    }

    @Test
    public void givenATransactionWithLesserQuantityThenStock_whenSellingStock_thenStockQuantityIsReduced() {
        Transaction aTransaction = new Transaction(aDate,
                                                   aStock,
                                                   A_VALID_QUANTITY,
                                                   aMarket,
                                                   SELL_TRANSACTION_TYPE);
        Sale aSale = new Sale(aTransaction, new TransactionFeesCalculator());

        aBoughtStock.sellStock(aSale);
        long actualQuantity = aBoughtStock.getQuantity();

        Assert.assertEquals(0, actualQuantity);
    }

    private void stubStockBehaviours(Stock _stock, String _market, String _symbol) {
        when(_stock.getMarketSymbol()).thenReturn(_market);
        when(_stock.getSymbol()).thenReturn(_symbol);
        when(_stock.getPrice()).thenReturn(A_PRICE);
    }
}
