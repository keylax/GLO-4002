package ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses;

import java.time.Instant;
import java.util.Arrays;


public class TransactionReport {
    public final Instant date;
    public final TransactionResponse[] transactions;

    public TransactionReport(Instant _date, TransactionResponse[] _transactions) {
        date = _date;
        transactions = _transactions;
    }

    public boolean equals(Object _other) {
        if (_other == null) {
            return false;
        }

        if (getClass() != _other.getClass()) {
            return false;
        }

        boolean sameDate = date.equals(((TransactionReport) _other).date);
        boolean sameTransactionResponses = Arrays.equals(transactions, ((TransactionReport) _other).transactions);
        return sameDate && sameTransactionResponses;
    }

    public int hashCode() {
        return super.hashCode();
    }
}
