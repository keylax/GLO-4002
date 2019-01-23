package ca.ulaval.glo4002.trading.infrastructure.repositories.accounts;

import ca.ulaval.glo4002.trading.domain.accounts.Account;
import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.domain.accounts.BoughtStock;
import ca.ulaval.glo4002.trading.domain.accounts.Wallet;
import ca.ulaval.glo4002.trading.domain.investors.Investor;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.AccountEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.BoughtStockEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.investors.InvestorEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountAssembler {
    public Account toDomainObject(AccountEntity _accountEntity) {
        InvestorEntity investorEntity = _accountEntity.investor;
        Investor investor = new Investor(
                investorEntity.id,
                investorEntity.firstName,
                investorEntity.lastName);

        ArrayList<String> walletCurrencies = new ArrayList<>();
        ArrayList<BigDecimal> walletCredits = new ArrayList<>();
        for (int i = 0; i < _accountEntity.wallet.walletCurrencies.size(); i++) {
            walletCurrencies.add(_accountEntity.wallet.walletCurrencies.get(i).currencySymbol);
            walletCredits.add(_accountEntity.wallet.walletCurrencies.get(i).credits);
        }

        Wallet wallet = new Wallet(walletCurrencies, walletCredits);
        List<BoughtStock> boughtStocks = assembleBoughtStocks(_accountEntity.boughtStocks);
        AccountNumber accountNumber = new AccountNumber(_accountEntity.id, investor.getInvestorInitials());

        return new Account(investor, wallet, accountNumber, boughtStocks);
    }

    private List<BoughtStock> assembleBoughtStocks(List<BoughtStockEntity> _boughtStockEntities) {
        return _boughtStockEntities.stream()
                            .map(boughtStockPersistence -> new BoughtStock(
                                    boughtStockPersistence.market,
                                    boughtStockPersistence.symbol,
                                    boughtStockPersistence.quantity,
                                    new TransactionNumber(boughtStockPersistence.transactionNumber)))
                            .collect(Collectors.toList());
    }
}
