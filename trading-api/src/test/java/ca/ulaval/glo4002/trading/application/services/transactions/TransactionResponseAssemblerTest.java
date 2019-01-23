package ca.ulaval.glo4002.trading.application.services.transactions;

import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionReport;
import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.domain.stocks.Stock;
import ca.ulaval.glo4002.trading.domain.transactions.Transaction;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionResponse;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionType;
import ca.ulaval.glo4002.trading.utilities.DateComparer;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

import ca.ulaval.glo4002.trading.utilities.DateMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionResponseAssemblerTest {
    private static final TransactionType BUY_TRANSACTION_TYPE = TransactionType.BUY;
    private static final TransactionType SELL_TRANSACTION_TYPE = TransactionType.SELL;
    private static final BigDecimal A_PRICE = BigDecimal.ONE;
    private static final String AN_ACCOUNT_NUMBER_STRING = "SD-3";
    private static final String MARKET = "NASDAQ";
    private static final String SYMBOL = "GOOG";
    private static final long QUANTITY = 1;

    private TransactionResponseAssembler assembler;
    private LocalDateTime aValidDate;
    private AccountNumber anAccountNumber;

    @Mock
    private Stock aStock;

    @Mock
    private Market aMarket;

    @Before
    public void setup() {
        aValidDate = LocalDateTime.now();
        assembler = new TransactionResponseAssembler();
        anAccountNumber = new AccountNumber(AN_ACCOUNT_NUMBER_STRING);
        when(aStock.getSymbol()).thenReturn(SYMBOL);
        when(aStock.getPrice()).thenReturn(A_PRICE);
        when(aStock.getMarketSymbol()).thenReturn(MARKET);
        when(aMarket.isOpen(any())).thenReturn(true);
    }

    @Test
    public void givenABuyTransaction_whenAssemblingTransactionResponse_returnExpectedTransactionBuyResponse() {
        Transaction buyTransaction = new Transaction(aValidDate, aStock, QUANTITY, aMarket, BUY_TRANSACTION_TYPE);
        buyTransaction.associateAccount(anAccountNumber);

        TransactionResponse buyResponse = assembler.toResponse(buyTransaction);

        Assert.assertTrue(areEquivalent(buyTransaction, buyResponse));
    }

    @Test
    public void givenASellTransaction_whenAssemblingTransactionResponse_returnExpectedTransactionSellResponse() {
        Transaction sellTransaction = new Transaction(aValidDate, aStock, QUANTITY, aMarket, SELL_TRANSACTION_TYPE);
        sellTransaction.associateAccount(anAccountNumber);

        TransactionResponse sellResponse = assembler.toResponse(sellTransaction);

        Assert.assertTrue(areEquivalent(sellTransaction, sellResponse));
    }

    @Test
    public void whenAssemblingTransactionReport_thenReturnExpectedTransactionReport() {
        Transaction sellTransaction = new Transaction(aValidDate, aStock, QUANTITY, aMarket, SELL_TRANSACTION_TYPE);
        Transaction[] someTransactions = {sellTransaction};
        LocalDateTime aDateTime = LocalDateTime.now();
        Instant instantDateTime = DateMapper.toInstant(aDateTime);
        TransactionResponse[] responses = assembler.toResponses(someTransactions);
        TransactionReport expectedTransactionReport = new TransactionReport(instantDateTime, responses);

        TransactionReport actualTransactionReport = assembler.toResponse(someTransactions, aDateTime);
        Assert.assertEquals(expectedTransactionReport, actualTransactionReport);
    }

    private boolean areEquivalent(Transaction _transaction, TransactionResponse _transactionResponse) {
        TransactionNumber transactionNumber = _transaction.getTransactionNumber();

        return transactionNumber.getNumber().equals(_transactionResponse.transactionNumber)
                && _transaction.getStockSymbol().equals(_transactionResponse.stock.symbol)
                && _transaction.getMarketSymbol().equals(_transactionResponse.stock.market)
                && DateComparer.areEquivalent(_transaction.getDate(), _transactionResponse.date);
    }
}
