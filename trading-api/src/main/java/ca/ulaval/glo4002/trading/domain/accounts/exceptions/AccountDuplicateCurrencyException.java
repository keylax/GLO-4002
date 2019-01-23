package ca.ulaval.glo4002.trading.domain.accounts.exceptions;

public class AccountDuplicateCurrencyException extends RuntimeException {
    private String currency;

    public AccountDuplicateCurrencyException(String _currency) {
        currency = _currency;
    }

    public String getCurrency() {
        return currency;
    }
}
