package ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class WalletCurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long id;
    public String currencySymbol;
    public BigDecimal credits;

    public WalletCurrencyEntity() {
        //for hibernate
    }

    public WalletCurrencyEntity(String _currencySymbol, BigDecimal _credits) {
        currencySymbol = _currencySymbol;
        credits = _credits;
    }
}
