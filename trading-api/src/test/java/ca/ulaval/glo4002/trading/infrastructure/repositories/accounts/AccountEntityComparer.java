package ca.ulaval.glo4002.trading.infrastructure.repositories.accounts;

import ca.ulaval.glo4002.trading.domain.accounts.Account;
import ca.ulaval.glo4002.trading.domain.accounts.BoughtStock;
import ca.ulaval.glo4002.trading.domain.accounts.Wallet;
import ca.ulaval.glo4002.trading.domain.accounts.WalletCurrency;
import ca.ulaval.glo4002.trading.domain.investors.Investor;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.AccountEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.BoughtStockEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.WalletCurrencyEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.WalletEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.investors.InvestorEntity;

import java.util.List;

public class AccountEntityComparer {
    public static boolean areEquivalent(Account _account, AccountEntity _accountEntity) {
        return _account.getAccountNumber().getId() == _accountEntity.id
                && _account.getTotalCredits().floatValue() == _accountEntity.wallet.totalCredits.floatValue()
                && areEquivalent(_account.getBoughtStocks(), _accountEntity.boughtStocks)
                && areEquivalent(_account.getInvestor(), _accountEntity.investor)
                && areEquivalent(_account.getWallet(), _accountEntity.wallet);
    }

    private static boolean areEquivalent(Investor _investor, InvestorEntity _investorEntity) {
        return _investor.getInvestorId() == _investorEntity.id
                && _investor.getFirstName() == _investorEntity.firstName
                && _investor.getLastName() == _investorEntity.lastName;
    }

    private static boolean areEquivalent(Wallet _wallet, WalletEntity _walletEntity) {
        boolean areEquivalent = true;
        for (int i = 0; i < _wallet.getWalletCurrencies().size(); i++) {
            WalletCurrency currency = _wallet.getWalletCurrencies().get(i);
            WalletCurrencyEntity currencyEntity = _walletEntity.walletCurrencies.get(i);
            if (!currency.getCurrencySymbol().equals(currencyEntity.currencySymbol)
                || currency.getCredits() != currencyEntity.credits) {
                areEquivalent = false;
            }
        }
        return areEquivalent;
    }

    private static boolean areEquivalent(List<BoughtStock> _boughtStocks,
                                  List<BoughtStockEntity> _boughtStocksPersistence) {
        if (_boughtStocks.size() != _boughtStocksPersistence.size()) {
            return false;
        }

        for (int i = 0; i < _boughtStocks.size(); i++) {
            if (_boughtStocks.get(i).getTransactionNumber().getNumber()
                    != _boughtStocksPersistence.get(i).transactionNumber
                    || _boughtStocks.get(i).getMarket() != _boughtStocksPersistence.get(i).market
                    || _boughtStocks.get(i).getQuantity() != _boughtStocksPersistence.get(i).quantity
                    || _boughtStocks.get(i).getSymbol() != _boughtStocksPersistence.get(i).symbol) {
                return false;
            }
        }

        return true;
    }
}
