package ca.ulaval.glo4002.trading.infrastructure.repositories.accounts;

import ca.ulaval.glo4002.trading.domain.accounts.Account;
import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.domain.accounts.BoughtStock;
import ca.ulaval.glo4002.trading.domain.accounts.Wallet;
import ca.ulaval.glo4002.trading.domain.investors.Investor;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.AccountEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountEntityFactoryTest {
    private static final long AN_INVESTOR_ID = 1;
    private static final String AN_INVESTOR_FIRST_NAME = "anInvestorFirstName";
    private static final String AN_INVESTOR_LAST_NAME = "anInvestorLastName";
    private static final String USD_CURRENCY = "USD";
    private static final BigDecimal A_CURRENCY_AMOUNT = BigDecimal.TEN;
    private static final String AN_ACCOUNT_NUMBER_STRING = "AA-3";
    private static final String A_MARKET_SYMBOL = "NASDAQ";
    private static final String A_STOCK_SYMBOL = "GOOG";
    private static final long A_QUANTITY = 1;

    private AccountEntityFactory assembler;
    private AccountNumber anAccountNumber;
    private Wallet aWallet;
    private TransactionNumber aTransactionNumber;
    private BoughtStock aBoughtStock;
    private List<BoughtStock> someBoughtStocks;

    @Before
    public void setup() {
        assembler = new AccountEntityFactory();
        anAccountNumber = new AccountNumber(AN_ACCOUNT_NUMBER_STRING);
        ArrayList<String> currency = new ArrayList<>();
        currency.add(USD_CURRENCY);
        ArrayList<BigDecimal> currencyAmount = new ArrayList<>();
        currencyAmount.add(A_CURRENCY_AMOUNT);
        aWallet = new Wallet(currency, currencyAmount);
        aTransactionNumber = new TransactionNumber(UUID.randomUUID());
        aBoughtStock = new BoughtStock(A_MARKET_SYMBOL, A_STOCK_SYMBOL, A_QUANTITY, aTransactionNumber);
        someBoughtStocks = new ArrayList<>();
        someBoughtStocks.add(aBoughtStock);
    }

    @Test
    public void whenAssemblingAnAccountEntity_thenReturnExpectedAccountEntity() {
        Investor investor = new Investor(AN_INVESTOR_ID, AN_INVESTOR_FIRST_NAME, AN_INVESTOR_LAST_NAME);
        Account anAccount = new Account(investor, aWallet, anAccountNumber, someBoughtStocks);

        AccountEntity accountEntity = assembler.createAccountEntity(anAccount);

        Assert.assertTrue(AccountEntityComparer.areEquivalent(anAccount, accountEntity));
    }
}
