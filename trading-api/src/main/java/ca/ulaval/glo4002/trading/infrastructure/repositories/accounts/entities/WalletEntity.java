package ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Entity
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long id;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "currency_id")
    public List<WalletCurrencyEntity> walletCurrencies;

    public BigDecimal totalCredits;

    public WalletEntity() {
        //for hibernate...
    }

    public WalletEntity(List<WalletCurrencyEntity> _walletCurrencies, BigDecimal _totalCredits) {
        walletCurrencies = _walletCurrencies;
        totalCredits = _totalCredits;
    }
}
