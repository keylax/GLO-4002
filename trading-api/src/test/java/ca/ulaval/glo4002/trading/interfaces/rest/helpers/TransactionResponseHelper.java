package ca.ulaval.glo4002.trading.interfaces.rest.helpers;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.StockResponse;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionBuyResponse;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionResponse;

import java.util.Date;
import java.util.UUID;

public class TransactionResponseHelper {
    private static final String A_MARKET = "aMarket";
    private static final String A_SYMBOL = "aSymbol";
    private static final long A_QUANTITY = 2;
    private static final float A_TRANSACTION_PRICE = 2.99f;
    private static final float A_TRANSACTION_FEE = 1.99f;

    public static TransactionResponse createATransactionResponse(UUID _transactionNumber) {
        StockResponse aStockResponse = new StockResponse(A_MARKET, A_SYMBOL);
        return new TransactionBuyResponse(
                _transactionNumber,
                new Date().toInstant(),
                A_TRANSACTION_FEE,
                A_TRANSACTION_PRICE,
                A_QUANTITY,
                aStockResponse);
    }
}
