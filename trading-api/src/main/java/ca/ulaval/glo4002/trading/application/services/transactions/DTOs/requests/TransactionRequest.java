package ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests;

import java.util.UUID;

public class TransactionRequest {
    public String type;
    public String date;
    public StockRequest stock;
    public UUID transactionNumber;
    public long quantity;
}
