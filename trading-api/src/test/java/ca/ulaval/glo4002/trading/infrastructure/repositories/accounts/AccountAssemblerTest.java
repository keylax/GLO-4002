package ca.ulaval.glo4002.trading.infrastructure.repositories.accounts;

import ca.ulaval.glo4002.trading.domain.accounts.Account;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.AccountEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.BoughtStockEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.WalletCurrencyEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.WalletEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.investors.InvestorEntity;

import ca.ulaval.glo4002.trading.utilities.CurrencyConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountAssemblerTest {
    private static final String AN_INVESTOR_FIRST_NAME = "anInvestorFirstName";
    private static final String AN_INVESTOR_LAST_NAME = "anInvestorLastName";
    private static final BigDecimal A_CREDIT_BALANCE = BigDecimal.valueOf(10.0);
    private static final String USD_CURRENCY = "USD";
    private static final long AN_INVESTOR_ID = 2;
    private static final int AN_ACCOUNT_PERSISTENCE_ID = 1;
    private static final String A_MARKET_SYMBOL = "NASDAQ";
    private static final String A_STOCK_SYMBOL = "GOOG";
    private static final long A_QUANTITY = 1;

    private AccountAssembler accountAssembler;
    private UUID aTransactionNumber;
    private BoughtStockEntity aBoughtStockEntity;
    private List<BoughtStockEntity> boughtStocksPersistence;
    private InvestorEntity anInvestor;
    private WalletEntity aWalletEntity;
    private WalletCurrencyEntity aWalletCurrencyEntity;
    private List<WalletCurrencyEntity> walletCurrencyEntities;

    @Before
    public void setup() {
        accountAssembler = new AccountAssembler();
        aTransactionNumber = UUID.randomUUID();
        aBoughtStockEntity = new BoughtStockEntity(A_MARKET_SYMBOL, A_STOCK_SYMBOL, A_QUANTITY, aTransactionNumber);
        boughtStocksPersistence = new ArrayList<>();
        boughtStocksPersistence.add(aBoughtStockEntity);
        anInvestor = new InvestorEntity(AN_INVESTOR_ID, AN_INVESTOR_FIRST_NAME, AN_INVESTOR_LAST_NAME);
        aWalletCurrencyEntity = new WalletCurrencyEntity(USD_CURRENCY, A_CREDIT_BALANCE);
        walletCurrencyEntities = new ArrayList<>();
        walletCurrencyEntities.add(aWalletCurrencyEntity);
        BigDecimal totalCredits = CurrencyConverter.convertToCAD(A_CREDIT_BALANCE, USD_CURRENCY);
        aWalletEntity = new WalletEntity(walletCurrencyEntities, totalCredits);
    }

    @Test
    public void whenAssemblingAnAccount_thenReturnExpectedAccount() {
        AccountEntity accountEntity = new AccountEntity(
                AN_ACCOUNT_PERSISTENCE_ID,
                boughtStocksPersistence,
                anInvestor,
                aWalletEntity);

        Account account = accountAssembler.toDomainObject(accountEntity);

        Assert.assertTrue(AccountEntityComparer.areEquivalent(account, accountEntity));
    }
}
