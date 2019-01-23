package ca.ulaval.glo4002.trading.infrastructure.repositories.accounts;

import ca.ulaval.glo4002.trading.domain.accounts.Account;
import ca.ulaval.glo4002.trading.domain.accounts.BoughtStock;
import ca.ulaval.glo4002.trading.domain.accounts.WalletCurrency;
import ca.ulaval.glo4002.trading.domain.investors.Investor;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.AccountEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.BoughtStockEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.WalletCurrencyEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.WalletEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.investors.InvestorEntity;

import java.util.List;
import java.util.stream.Collectors;

public class AccountEntityFactory {
    public AccountEntity createAccountEntity(Account _account) {
        int accountNumberId = getAccountNumberId(_account);

        List<BoughtStock> boughtStocks = _account.getBoughtStocks();
        List<WalletCurrency> currencies = _account.getCurrencies();
        List<BoughtStockEntity> boughtStockEntities = createBoughtStockEntities(boughtStocks);
        List<WalletCurrencyEntity> walletCurrencyEntities = createWalletCurrencyEntities(currencies);

        WalletEntity walletEntity = new WalletEntity(walletCurrencyEntities, _account.getTotalCredits());

        Investor investor = _account.getInvestor();
        InvestorEntity investorEntity = new InvestorEntity(
                investor.getInvestorId(),
                investor.getFirstName(),
                investor.getLastName());

        return new AccountEntity(
                accountNumberId,
                boughtStockEntities,
                investorEntity,
                walletEntity);
    }

    private int getAccountNumberId(Account _account) {
        if (_account.getAccountNumber() == null) {
            return 0;
        } else {
            return _account.getAccountNumber().getId();
        }
    }

    private List<BoughtStockEntity> createBoughtStockEntities(List<BoughtStock> _boughtStocks) {
        return _boughtStocks.stream()
                .map(boughtStock -> new BoughtStockEntity(
                        boughtStock.getMarket(),
                        boughtStock.getSymbol(),
                        boughtStock.getQuantity(),
                        boughtStock.getTransactionNumber().getNumber()))
                .collect(Collectors.toList());
    }

    private List<WalletCurrencyEntity> createWalletCurrencyEntities(List<WalletCurrency> _currencies) {
        return _currencies.stream()
                            .map(currency -> new WalletCurrencyEntity(
                                    currency.getCurrencySymbol(),
                                    currency.getCredits()))
                            .collect(Collectors.toList());
    }
}

