package ca.ulaval.glo4002.trading.domain.accounts;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.AccountInvalidAmountException;
import ca.ulaval.glo4002.trading.domain.accounts.exceptions.AccountDuplicateCurrencyException;
import ca.ulaval.glo4002.trading.domain.accounts.exceptions.CurrencyNotDeclaredException;
import ca.ulaval.glo4002.trading.domain.accounts.exceptions.UnsupportedCurrencyException;
import ca.ulaval.glo4002.trading.utilities.CurrencyConverter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private List<WalletCurrency> walletCurrencies;
    private BigDecimal totalCredits;
    
    public Wallet(ArrayList<String> _walletCurrencies, List<BigDecimal> _initialCredits) {
        validateInitialCreditAmounts(_initialCredits);
        validateCurrencies(_walletCurrencies);
        walletCurrencies = new ArrayList<>();
        for (int i = 0; i < _walletCurrencies.size(); i++) {
            WalletCurrency newCurrency = new WalletCurrency(_walletCurrencies.get(i), _initialCredits.get(i));
            walletCurrencies.add(newCurrency);
        }
        calculateTotalCredits();
    }

    public void addCredits(BigDecimal _credits, String _currency) {
        validateCreditAmount(_credits);
        getCurrencyMatchingSymbol(_currency).addCredits(_credits);
        calculateTotalCredits();
    }
    
    public void removeCredits(BigDecimal _credits, String _currency) {
        validateCreditAmount(_credits);
        validateWalletGotEnoughCredits(_credits, _currency);
        getCurrencyMatchingSymbol(_currency).removeCredits(_credits);
        calculateTotalCredits();
    }

    private void validateInitialCreditAmounts(List<BigDecimal> _credits) {
        for (int i = 0; i < _credits.size(); i++) {
            validateCreditAmount(_credits.get(i));
        }
    }

    private void validateCreditAmount(BigDecimal _credits) {
        if (!isCreditAmountValid(_credits)) {
            throw new AccountInvalidAmountException();
        }
    }
    
    public BigDecimal getCredits(String _currency) {
        return getCurrencyMatchingSymbol(_currency).getCredits();
    }

    public BigDecimal getTotalCredits() {
        return totalCredits;
    }

    public List<WalletCurrency> getWalletCurrencies() {
        return walletCurrencies;
    }

    private WalletCurrency getCurrencyMatchingSymbol(String _currencySymbol) {
        for (int i = 0; i < walletCurrencies.size(); i++) {
            if (walletCurrencies.get(i).getCurrencySymbol().equals(_currencySymbol)) {
                return walletCurrencies.get(i);
            }
        }
        throw new CurrencyNotDeclaredException(_currencySymbol);
    }

    private void calculateTotalCredits() {
        totalCredits = BigDecimal.ZERO;
        for (int i = 0; i < walletCurrencies.size(); i++) {
            BigDecimal amount = walletCurrencies.get(i).getCredits();
            String currency = walletCurrencies.get(i).getCurrencySymbol();
            BigDecimal convertedToCAD = CurrencyConverter.convertToCAD(amount, currency);
            totalCredits = totalCredits.add(convertedToCAD);
        }
    }

    private void validateCurrencies(ArrayList<String> _walletCurrencies) {
        for (int i = 0; i < _walletCurrencies.size(); i++) {
            if (!_walletCurrencies.get(i).equals("USD")
                && !_walletCurrencies.get(i).equals("CHF")
                && !_walletCurrencies.get(i).equals("JPY")) {
                throw new UnsupportedCurrencyException(_walletCurrencies.get(i));
            }
            for (int j = i + 1; j < _walletCurrencies.size(); j++) {
                if (_walletCurrencies.get(i).equals(_walletCurrencies.get(j))) {
                    throw new AccountDuplicateCurrencyException(_walletCurrencies.get(i));
                }
            }
        }
    }

    private void validateWalletGotEnoughCredits(BigDecimal _credits, String _currency) {
        if (!isWalletWealthyEnough(_credits, _currency)) {
            throw new AccountInvalidAmountException();
        }
    }
    
    private boolean isCreditAmountValid(BigDecimal _credits) {
        return _credits.signum() == 1;
    }
    
    private boolean isWalletWealthyEnough(BigDecimal _credits, String _currency) {
        return getCurrencyMatchingSymbol(_currency).getCredits().compareTo(_credits) >= 0;
    }
}
