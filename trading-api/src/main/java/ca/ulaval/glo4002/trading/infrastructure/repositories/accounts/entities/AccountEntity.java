package ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities;

import ca.ulaval.glo4002.trading.infrastructure.repositories.investors.InvestorEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class AccountEntity {
    private static final String ACCOUNT_NUMBER_SEPERATOR = "-";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "account")
    public List<BoughtStockEntity> boughtStocks;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "investor_id")
    public InvestorEntity investor;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "wallet_id")
    public WalletEntity wallet;

    public AccountEntity() {
        //for hibernate
    }

    public AccountEntity(
            int _id,
            List<BoughtStockEntity> _boughtStocks,
            InvestorEntity _investor,
            WalletEntity _wallet) {
        id = _id;
        boughtStocks = _boughtStocks;
        investor = _investor;
        wallet = _wallet;
    }

    public String getAccountNumber() {
        return investor.getInitials() + ACCOUNT_NUMBER_SEPERATOR + String.valueOf(id);
    }

}
