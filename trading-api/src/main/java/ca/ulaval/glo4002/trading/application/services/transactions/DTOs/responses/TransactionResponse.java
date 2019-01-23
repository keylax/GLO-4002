package ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses;

import java.time.Instant;
import java.util.UUID;

public abstract class TransactionResponse {
    public final UUID transactionNumber;
    public final String type;
    public final Instant date;
    public final float fees;
    public final StockResponse stock;

    public TransactionResponse(UUID _transactionNumber,
                               String _type,
                               Instant _date,
                               float _fees,
                               StockResponse _stock) {
        transactionNumber = _transactionNumber;
        type = _type;
        date = _date;
        fees = _fees;
        stock = _stock;
    }

    public boolean equals(Object _other) {
        if (_other == null) {
            return false;
        }

        if (getClass() != _other.getClass()) {
            return false;
        }

        boolean sameTransactionNumber = transactionNumber.equals(((TransactionResponse) _other).transactionNumber);
        boolean sameType = type.equals(((TransactionResponse) _other).type);
        boolean sameDate = date.equals(((TransactionResponse) _other).date);
        boolean sameFees = fees == ((TransactionResponse) _other).fees;
        boolean sameStock = stock.equals(((TransactionResponse) _other).stock);
        return sameTransactionNumber && sameType && sameDate && sameFees && sameStock;
    }

    public int hashCode() {
        return super.hashCode();
    }
}
