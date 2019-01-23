package ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses;

import java.time.Instant;
import java.util.UUID;

public class TransactionBuyResponse extends TransactionResponse {
    public static final String TYPE = "BUY";

    public final float purchasedPrice;
    public final long quantity;

    public TransactionBuyResponse(UUID _transactionNumber,
                                  Instant _date,
                                  float _fees,
                                  float _purchasedPrice,
                                  long _quantity,
                                  StockResponse _stock) {
        super(_transactionNumber, TYPE, _date, _fees, _stock);
        purchasedPrice = _purchasedPrice;
        quantity = _quantity;
    }
}
