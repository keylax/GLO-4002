package ca.ulaval.glo4002.trading.domain.accounts;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.*;
import ca.ulaval.glo4002.trading.domain.transactions.Purchase;
import ca.ulaval.glo4002.trading.domain.transactions.Sale;
import ca.ulaval.glo4002.trading.domain.investors.Investor;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.transactions.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountTest {

    private static final BigDecimal A_PURCHASE_PRICE = BigDecimal.TEN;
    private static final String AN_ACCOUNT_NUMBER_STRING = "AE-123";
    private static final String AN_INVESTOR_FIRST_NAME = "Adam";
    private static final String AN_INVESTOR_LAST_NAME = "Eve";
    private static final String A_SYMBOL = "symbol";
    private static final String A_MARKET = "market";
    private static final String A_DIFFERENT_SYMBOL = "differentSymbol";
    private static final String A_DIFFERENT_MARKET = "differentMarket";
    private static final String USD_CURRENCY = "USD";
    private static final long AN_INVESTOR_ID = 1;
    private static final long A_QUANTITY = 2;

    private Investor investor;
    private Account anAccount;
    private AccountNumber anAccountNumber;

    @Mock
    private Sale aSale;

    @Mock
    private Purchase aPurchase;

    @Mock
    private Transaction aBuyTransaction;

    @Mock
    private Transaction aSellTransaction;

    @Mock
    private Wallet aWallet;

    @Before
    public void setup() {
        investor = new Investor(AN_INVESTOR_ID, AN_INVESTOR_FIRST_NAME, AN_INVESTOR_LAST_NAME);
        anAccountNumber = new AccountNumber(AN_ACCOUNT_NUMBER_STRING);
        anAccount = new Account(investor, aWallet, anAccountNumber, new ArrayList<>());

        TransactionNumber aTransactionNumber = new TransactionNumber();
        when(aPurchase.getTransaction()).thenReturn(aBuyTransaction);
        when(aSale.getTransaction()).thenReturn(aSellTransaction);
        when(aBuyTransaction.getTransactionNumber()).thenReturn(aTransactionNumber);
        when(aSellTransaction.getTransactionNumber()).thenReturn(aTransactionNumber);

        when(aBuyTransaction.getMarketSymbol()).thenReturn(A_MARKET);
        when(aBuyTransaction.getStockSymbol()).thenReturn(A_SYMBOL);
        when(aSellTransaction.getMarketSymbol()).thenReturn(A_MARKET);
        when(aSellTransaction.getStockSymbol()).thenReturn(A_SYMBOL);
    }

    @Test
    public void givenAnAccountWithAnAccountNumber_whenBuyingStock_thenPurchaseAssociateAccountNumber() {
        BigDecimal expectedCredits = anAccount.getTotalCredits();
        when(aPurchase.calculateTotalPrice()).thenReturn(expectedCredits);

        anAccount.buyStock(aPurchase);

        verify(aPurchase).associateAccount(anAccountNumber);
    }

    @Test
    public void givenAccountWithAccountNumber_whenSellingStock_thenSaleAssociateAccountNumber() {
        TransactionNumber aBuyTransactionNumber = new TransactionNumber();
        when(aBuyTransaction.getTransactionNumber()).thenReturn(aBuyTransactionNumber);
        when(aPurchase.calculateTotalPrice()).thenReturn(A_PURCHASE_PRICE);
        when(aSale.calculateTotalPrice()).thenReturn(A_PURCHASE_PRICE);

        anAccount.buyStock(aPurchase);
        anAccount.sellStock(aSale, aBuyTransactionNumber);

        verify(aSale).associateAccount(anAccountNumber);
    }

    @Test
    public void whenAStockIsPurchase_thenTheTransactionPriceDeductedFromWallet() {
        when(aPurchase.calculateTotalPrice()).thenReturn(A_PURCHASE_PRICE);
        when(aBuyTransaction.getCurrency()).thenReturn(USD_CURRENCY);

        anAccount.buyStock(aPurchase);

        verify(aWallet).removeCredits(A_PURCHASE_PRICE, USD_CURRENCY);
    }

    @Test(expected = NotEnoughCreditException.class)
    public void whenAStockIsPurchaseAndWalletThrowAnException_thenANotEnoughCreditExceptionIsThrown() {
        TransactionNumber aBuyTransactionNumber = new TransactionNumber();
        when(aBuyTransaction.getTransactionNumber()).thenReturn(aBuyTransactionNumber);
        when(aPurchase.calculateTotalPrice()).thenReturn(A_PURCHASE_PRICE);
        when(aBuyTransaction.getCurrency()).thenReturn(USD_CURRENCY);

        doThrow(new AccountInvalidAmountException()).when(aWallet).removeCredits(A_PURCHASE_PRICE, USD_CURRENCY);

        anAccount.buyStock(aPurchase);
    }

    @Test(expected = InvalidTransactionNumberException.class)
    public void givenAnAccountWithNoPurchaseMade_whenTheAccountSellsAStock_thenAnExceptionIsThrown() {
        TransactionNumber aBuyTransactionNumber = new TransactionNumber();
        when(aSellTransaction.getTransactionNumber()).thenReturn(aBuyTransactionNumber);

        anAccount.sellStock(aSale, aBuyTransaction.getTransactionNumber());
    }

    @Test(expected = InvalidTransactionNumberException.class)
    public void givenAnAccountWithNoPurchaseMade_whenTheAccountSellsAStock_thenTheWalletDoesNotAddAnyCredits() {
        TransactionNumber aBuyTransactionNumber = new TransactionNumber();
        when(aSellTransaction.getTransactionNumber()).thenReturn(aBuyTransactionNumber);

        try {
            anAccount.sellStock(aSale, aBuyTransaction.getTransactionNumber());
        } finally {
            verify(aWallet, never()).addCredits(any(BigDecimal.class), anyString());
        }
    }

    @Test(expected = StockParametersDontMatchException.class)
    public void givenAnAccountWithPurchases_whenTheAccountSellsAStockWithADifferentBoughtStock_thenAnExceptionIsThrown() {
        when(aSellTransaction.getMarketSymbol()).thenReturn(A_DIFFERENT_MARKET);
        when(aSellTransaction.getStockSymbol()).thenReturn(A_DIFFERENT_SYMBOL);
        anAccount.buyStock(aPurchase);

        anAccount.sellStock(aSale, aBuyTransaction.getTransactionNumber());
    }

    @Test(expected = StockParametersDontMatchException.class)
    public void givenAnAccountWithPurchases_whenTheAccountSellsAStockWithADifferentBoughtStock_thenTheWalletDoesNotAddAnyCredits() {
        when(aSellTransaction.getMarketSymbol()).thenReturn(A_DIFFERENT_MARKET);
        when(aSellTransaction.getStockSymbol()).thenReturn(A_DIFFERENT_SYMBOL);
        anAccount.buyStock(aPurchase);

        try {
            anAccount.sellStock(aSale, aBuyTransaction.getTransactionNumber());
        } finally {
            verify(aWallet, never()).addCredits(any(BigDecimal.class), anyString());
        }
    }

    @Test(expected = NotEnoughStockException.class)
    public void givenAnAccountWithPurchases_whenTheAccountSellsAGreaterStockQuantityThenBought_thenAnExceptionIsThrown() {
        long aGreaterQuantity = A_QUANTITY + 1;
        when(aBuyTransaction.getQuantity()).thenReturn(A_QUANTITY);
        when(aSellTransaction.getQuantity()).thenReturn(aGreaterQuantity);
        anAccount.buyStock(aPurchase);

        anAccount.sellStock(aSale, aBuyTransaction.getTransactionNumber());
    }

    @Test(expected = NotEnoughStockException.class)
    public void givenAnAccountWithPurchases_whenTheAccountSellsAGreaterStockQuantityThenBought_thenTheWalletDoesNotAddAnyCredits() {
        long aGreaterQuantity = A_QUANTITY + 1;
        when(aBuyTransaction.getQuantity()).thenReturn(A_QUANTITY);
        when(aSellTransaction.getQuantity()).thenReturn(aGreaterQuantity);
        anAccount.buyStock(aPurchase);

        try {
            anAccount.sellStock(aSale, aBuyTransaction.getTransactionNumber());
        } finally {
            verify(aWallet, never()).addCredits(any(BigDecimal.class), anyString());
        }
    }

    @Test
    public void givenAnAccountWithPurchases_whenTheAccountSellsALesserStockQuantityThenBought_thenTheWalletAddExpectedCredits() {
        long aLesserQuantity = A_QUANTITY - 1;
        BigDecimal expectedCredits = BigDecimal.TEN;
        when(aBuyTransaction.getQuantity()).thenReturn(A_QUANTITY);
        when(aSellTransaction.getQuantity()).thenReturn(aLesserQuantity);
        when(aSale.calculateTotalPrice()).thenReturn(expectedCredits);
        when(aBuyTransaction.getCurrency()).thenReturn(USD_CURRENCY);
        when(aSellTransaction.getCurrency()).thenReturn(USD_CURRENCY);
        anAccount.buyStock(aPurchase);

        anAccount.sellStock(aSale, aBuyTransaction.getTransactionNumber());

        verify(aWallet).addCredits(expectedCredits, USD_CURRENCY);
    }
}
