package ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class BoughtStockEntity {
    @Id
    public UUID transactionNumber;
    public String market;
    public String symbol;
    public long quantity;

    public BoughtStockEntity() {
        //for hibernate
    }

    public BoughtStockEntity(String _market, String _symbol, long _quantity, UUID _transactionNumber) {
        transactionNumber = _transactionNumber;
        market = _market;
        symbol = _symbol;
        quantity = _quantity;
    }
}
