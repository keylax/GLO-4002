package ca.ulaval.glo4002.trading.domain.transactions;

import java.util.UUID;

public class TransactionNumber {
    private UUID number;
    
    public TransactionNumber() {
        number = UUID.randomUUID();
    }
    
    public TransactionNumber(UUID _transactionId) {
        number = _transactionId;
    }

    public int hashCode() {
        return number.hashCode();
    }

    public boolean equals(Object _other) {
        if (_other == null) {
            return false;
        }
        
        if (getClass() != _other.getClass()) {
            return false;
        }
        
        TransactionNumber other = (TransactionNumber) _other;
        UUID otherNumber = other.getNumber();
        return number.equals(otherNumber);
    }
    
    public String toString() {
        return number.toString();
    }

    public UUID getNumber() {
        return number;
    }
}
