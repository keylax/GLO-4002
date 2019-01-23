package ca.ulaval.glo4002.trading.application.services.transactions;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.TransactionRequest;
import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.domain.stocks.Stock;
import ca.ulaval.glo4002.trading.domain.transactions.*;
import ca.ulaval.glo4002.trading.utilities.DateMapper;

import java.time.LocalDateTime;

public class TransactionFactory {
    private TransactionFeesCalculator transactionFeesCalculator;

    public TransactionFactory() {
        transactionFeesCalculator = new TransactionFeesCalculator();
    }

    public Sale createSale(TransactionRequest _transactionRequest, Stock _stock, Market _market) {
        Transaction transaction = createTransaction(_transactionRequest, _stock, _market, TransactionType.SELL);
        return new Sale(transaction, transactionFeesCalculator);
    }

    public Purchase createPurchase(TransactionRequest _transactionRequest, Stock _stock, Market _market) {
        Transaction transaction = createTransaction(_transactionRequest, _stock, _market, TransactionType.BUY);
        return new Purchase(transaction, transactionFeesCalculator);
    }

    private Transaction createTransaction(TransactionRequest _transactionRequest,
                                          Stock _stock,
                                          Market _market,
                                          TransactionType _type) {
        LocalDateTime dateTime = DateMapper.toDateTime(_transactionRequest.date);
        return new Transaction(dateTime, _stock, _transactionRequest.quantity, _market, _type);
    }
}
