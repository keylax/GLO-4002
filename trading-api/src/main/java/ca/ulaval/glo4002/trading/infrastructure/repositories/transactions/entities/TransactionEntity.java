package ca.ulaval.glo4002.trading.infrastructure.repositories.transactions.entities;

import ca.ulaval.glo4002.trading.domain.transactions.TransactionType;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class TransactionEntity {
    @Id
    public UUID transactionNumber;
    public LocalDateTime date;
    public long quantity;
    public String stockSymbol;
    public String marketSymbol;
    public BigDecimal stockPrice;
    public String accountNumber;
    public TransactionType type;

    public TransactionEntity() {
        //for hibernate
    }

    public TransactionEntity(UUID _transactionNumber,
                             LocalDateTime _date,
                             long _quantity,
                             String _stockSymbol,
                             String _marketSymbol,
                             BigDecimal _stockPrice,
                             String _accountNumber,
                             TransactionType _type) {
        transactionNumber = _transactionNumber;
        date = _date;
        quantity = _quantity;
        stockSymbol = _stockSymbol;
        marketSymbol = _marketSymbol;
        stockPrice = _stockPrice;
        accountNumber = _accountNumber;
        type = _type;
    }
}
