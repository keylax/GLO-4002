package ca.ulaval.glo4002.trading.application.services.transactions;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.TransactionRequest;
import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.domain.stocks.Stock;
import ca.ulaval.glo4002.trading.domain.transactions.Purchase;
import ca.ulaval.glo4002.trading.domain.transactions.Sale;
import ca.ulaval.glo4002.trading.domain.transactions.Transaction;
import ca.ulaval.glo4002.trading.utilities.DateComparer;

import ca.ulaval.glo4002.trading.utilities.DateMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionFactoryTest {
    private static final String A_SERIALIZED_DATE = "2018-07-08T23:59:59.999Z";
    private static final String A_MARKET = "aMarket";
    private static final String A_SYMBOL = "aSymbol";
    private static final BigDecimal A_PRICE = BigDecimal.ONE;

    private TransactionFactory transactionFactory;

    @Mock
    private Market aMarket;

    @Before
    public void setup() {
        transactionFactory = new TransactionFactory();

        when(aMarket.isOpen(any())).thenReturn(true);
    }

    @Test
    public void whenCreatingPurchase_thenReturnExpectedTransaction() {
        TransactionRequest aTransactionRequest = createATransactionRequest();
        Stock aStock = new Stock(A_SYMBOL, A_MARKET, A_PRICE);

        Purchase actualPurchase = transactionFactory.createPurchase(aTransactionRequest, aStock, aMarket);

        Transaction actualTransaction = actualPurchase.getTransaction();
        Assert.assertTrue(areEquivalent(aTransactionRequest, actualTransaction, aStock));
    }

    @Test
    public void whenCreatingSale_thenReturnExpectedTransaction() {
        TransactionRequest aTransactionRequest = createATransactionRequest();
        Stock aStock = new Stock(A_SYMBOL, A_MARKET, A_PRICE);

        Sale actualSale = transactionFactory.createSale(aTransactionRequest, aStock, aMarket);

        Transaction actualTransaction = actualSale.getTransaction();
        Assert.assertTrue(areEquivalent(aTransactionRequest, actualTransaction, aStock));
    }

    private boolean areEquivalent(TransactionRequest _transactionRequest, Transaction _transaction, Stock _stock) {
        return _transactionRequest.stock.symbol == _stock.getSymbol()
                && _transactionRequest.stock.market == _stock.getMarketSymbol()
                && _transactionRequest.quantity == _transaction.getQuantity()
                && DateComparer.areEquivalent(_transaction.getDate(),
                                              DateMapper.toDate(_transactionRequest.date).toInstant());
    }

    private TransactionRequest createATransactionRequest() {
        return TransactionTestHelper.createATransactionRequest(A_SERIALIZED_DATE, A_MARKET, A_SYMBOL);
    }
}
