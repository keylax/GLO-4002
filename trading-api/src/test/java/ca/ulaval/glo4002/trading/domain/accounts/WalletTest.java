package ca.ulaval.glo4002.trading.domain.accounts;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.AccountInvalidAmountException;

import java.math.BigDecimal;
import java.util.ArrayList;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.AccountDuplicateCurrencyException;
import ca.ulaval.glo4002.trading.domain.accounts.exceptions.CurrencyNotDeclaredException;
import ca.ulaval.glo4002.trading.domain.accounts.exceptions.UnsupportedCurrencyException;
import ca.ulaval.glo4002.trading.utilities.CurrencyConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WalletTest {
    private static final BigDecimal ZERO_CREDITS = BigDecimal.ZERO;
    private static final String USD_CURRENCY = "USD";
    private static final String CHF_CURRENCY = "CHF";
    private static final String JPY_CURRENCY = "JPY";
    private static final String OTHER_CURRENCY = "TEST";
    private BigDecimal fewCredits;
    private BigDecimal manyCredits;
    private BigDecimal negativeAmount;
    private Wallet walletWithFewCredits;
    private Wallet walletWithManyCredits;
    private Wallet walletWithMultipleCurrencies;
    private ArrayList<String> usdCurrency;
    private ArrayList<String> multipleCurrencies;
    private ArrayList<BigDecimal> fewCreditsCurrency;
    private ArrayList<BigDecimal> manyCreditsCurrency;
    private ArrayList<BigDecimal> negativeAmountCurrency;
    private ArrayList<BigDecimal> zeroCreditsCurrency;
    
    @Before
    public void setup() {
        negativeAmount = new BigDecimal(-10.23);
        fewCredits = new BigDecimal(1.11);
        manyCredits = new BigDecimal(999.99);

        setupCurrencyLists();

        walletWithMultipleCurrencies = new Wallet(multipleCurrencies, manyCreditsCurrency);
        walletWithFewCredits = new Wallet(usdCurrency, fewCreditsCurrency);
        walletWithManyCredits = new Wallet(usdCurrency, manyCreditsCurrency);
    }
    
    @Test(expected = AccountInvalidAmountException.class)
    public void givenANegativeCreditsAmount_whenAWalletIsCreated_thenThrowAnException() {
        new Wallet(usdCurrency, negativeAmountCurrency);
    }

    @Test(expected = AccountInvalidAmountException.class)
    public void givenANullCreditsAmount_whenAWalletIsCreated_thenThrowAnException() {
        new Wallet(usdCurrency, zeroCreditsCurrency);
    }
    
    @Test
    public void whenCreditsAreAdded_thenAddThemToTheWallet() {
        walletWithFewCredits.addCredits(fewCredits, USD_CURRENCY);

        BigDecimal actualCredits = walletWithFewCredits.getCredits(USD_CURRENCY);
        BigDecimal expectedCredits = fewCredits.add(fewCredits);
        Assert.assertEquals(expectedCredits, actualCredits);
    }
    
    @Test(expected = AccountInvalidAmountException.class)
    public void whenNegativeCreditsAreAdded_thenThrowAnException() {
        walletWithFewCredits.addCredits(negativeAmount, USD_CURRENCY);
    }
    
    @Test(expected = AccountInvalidAmountException.class)
    public void whenZeroCreditsAreAdded_thenThrowAnException() {
        walletWithFewCredits.addCredits(ZERO_CREDITS, USD_CURRENCY);
    }
   
    @Test
    public void whenCreditsAreRemoved_thenTheAmountIsRemovedFromTheWallet() {
        walletWithManyCredits.removeCredits(fewCredits, USD_CURRENCY);

        BigDecimal actualCredits = walletWithManyCredits.getCredits(USD_CURRENCY);
        BigDecimal expectedCredits = manyCredits.subtract(fewCredits);
        Assert.assertEquals(expectedCredits, actualCredits);
    }

    @Test(expected = AccountDuplicateCurrencyException.class)
    public void whenCreatingWalletWithDuplicateCurrencies_thenThrowAnException() {
        multipleCurrencies.add(USD_CURRENCY);
        manyCreditsCurrency.add(manyCredits);

        new Wallet(multipleCurrencies, manyCreditsCurrency);
    }
    
    @Test(expected = AccountInvalidAmountException.class)
    public void whenNegativeCreditsAreRemoved_thenThrowAnException() {
        walletWithFewCredits.removeCredits(negativeAmount, USD_CURRENCY);
    }
    
    @Test(expected = AccountInvalidAmountException.class)
    public void whenZeroCreditsAreRemoved_thenThrowAnException() {
        walletWithFewCredits.removeCredits(ZERO_CREDITS, USD_CURRENCY);
    }
    
    @Test(expected = AccountInvalidAmountException.class)
    public void whenCreditsAreRemovedButTheWalletDoesNotHaveEnough_thenThrowAnException() {
        walletWithFewCredits.removeCredits(manyCredits, USD_CURRENCY);
    }

    @Test(expected = CurrencyNotDeclaredException.class)
    public void whenRemoveCreditsOnCurrencyNotInWalletsCurrencies_thenThrowAnException() {
        walletWithFewCredits.removeCredits(manyCredits, OTHER_CURRENCY);
    }

    @Test(expected = CurrencyNotDeclaredException.class)
    public void whenAddCreditsOnCurrencyNotInWalletsCurrencies_thenThrowAnException() {
        walletWithFewCredits.addCredits(manyCredits, OTHER_CURRENCY);
    }

    @Test
    public void givenAWalletWithMultipleCurrencies_whenCreditsAreRemoved_thenRemovedThemFromCurrencyAmount() {
        walletWithMultipleCurrencies.removeCredits(fewCredits, USD_CURRENCY);

        BigDecimal actualCredits = walletWithMultipleCurrencies.getCredits(USD_CURRENCY);
        BigDecimal expectedCredits = manyCredits.subtract(fewCredits);
        Assert.assertEquals(expectedCredits, actualCredits);
    }

    @Test
    public void givenAWalletWithMultipleCurrencies_whenCreditsAreRemovedFromACurrency_thenOthersRemainUnchanged() {
        walletWithMultipleCurrencies.removeCredits(fewCredits, JPY_CURRENCY);

        BigDecimal actualUSDCredits = walletWithMultipleCurrencies.getCredits(USD_CURRENCY);
        BigDecimal actualJPYCredits = walletWithMultipleCurrencies.getCredits(JPY_CURRENCY);
        BigDecimal expectedJPYCredits = manyCredits.subtract(fewCredits);
        BigDecimal expectedUSDCredits = manyCredits;
        Assert.assertEquals(expectedUSDCredits, actualUSDCredits);
        Assert.assertEquals(expectedJPYCredits, actualJPYCredits);
    }

    @Test
    public void givenAWalletWithMultipleCurrencies_whenCreditsChanged_thenTotalCreditsIsUpdated() {
        walletWithMultipleCurrencies.removeCredits(BigDecimal.ONE, USD_CURRENCY);

        BigDecimal actualTotalCredits = walletWithMultipleCurrencies.getTotalCredits();
        BigDecimal newUSDCreditsInWallet = manyCredits.subtract(BigDecimal.ONE);
        BigDecimal expectedTotalCredits = CurrencyConverter.convertToCAD(newUSDCreditsInWallet, USD_CURRENCY);
        expectedTotalCredits = expectedTotalCredits.add(CurrencyConverter.convertToCAD(manyCredits, CHF_CURRENCY));
        expectedTotalCredits = expectedTotalCredits.add(CurrencyConverter.convertToCAD(manyCredits, JPY_CURRENCY));
        Assert.assertEquals(expectedTotalCredits, actualTotalCredits);
    }

    @Test(expected = UnsupportedCurrencyException.class)
    public void whenCreatingWalletWithInvalidCurrency_thenThrowException() {
        multipleCurrencies.add(OTHER_CURRENCY);
        manyCreditsCurrency.add(manyCredits);

        new Wallet(multipleCurrencies, manyCreditsCurrency);
    }

    private void setupCurrencyLists() {
        usdCurrency = new ArrayList<>();
        usdCurrency.add(USD_CURRENCY);
        fewCreditsCurrency = new ArrayList<>();
        fewCreditsCurrency.add(fewCredits);
        manyCreditsCurrency = new ArrayList<>();
        manyCreditsCurrency.add(manyCredits);
        manyCreditsCurrency.add(manyCredits);
        manyCreditsCurrency.add(manyCredits);

        negativeAmountCurrency = new ArrayList<>();
        negativeAmountCurrency.add(negativeAmount);
        zeroCreditsCurrency = new ArrayList<>();
        zeroCreditsCurrency.add(ZERO_CREDITS);

        multipleCurrencies = new ArrayList<>();
        multipleCurrencies.add(USD_CURRENCY);
        multipleCurrencies.add(JPY_CURRENCY);
        multipleCurrencies.add(CHF_CURRENCY);
    }
}
