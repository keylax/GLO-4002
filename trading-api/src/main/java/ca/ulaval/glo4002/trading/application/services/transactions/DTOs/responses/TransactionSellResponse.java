package ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses;

import java.time.Instant;
import java.util.UUID;

public class TransactionSellResponse extends TransactionResponse {
    public static final String TYPE = "SELL";

    public final float priceSold;
    public final long quantity;

    public TransactionSellResponse(UUID _transactionNumber,
                                   Instant _date,
                                   float _fees,
                                   float _priceSold,
                                   long _quantity,
                                   StockResponse _stock) {
        super(_transactionNumber, TYPE, _date, _fees, _stock);
        priceSold = _priceSold;
        quantity = _quantity;
    }
}
