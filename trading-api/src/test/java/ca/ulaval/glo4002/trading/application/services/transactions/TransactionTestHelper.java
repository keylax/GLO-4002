package ca.ulaval.glo4002.trading.application.services.transactions;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.StockRequest;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.TransactionRequest;

public class TransactionTestHelper {
    private static final long A_QUANTITY = 2;

    public static TransactionRequest createATransactionRequest(String _date, String _market, String _symbol) {
        StockRequest aStockRequest = new StockRequest();
        aStockRequest.market = _market;
        aStockRequest.symbol = _symbol;

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.date = _date;
        transactionRequest.quantity = A_QUANTITY;
        transactionRequest.stock = aStockRequest;
        return transactionRequest;
    }
}
