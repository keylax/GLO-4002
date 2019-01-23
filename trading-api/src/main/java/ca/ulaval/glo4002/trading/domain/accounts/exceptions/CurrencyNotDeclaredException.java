package ca.ulaval.glo4002.trading.domain.accounts.exceptions;

public class CurrencyNotDeclaredException extends RuntimeException {
    private String currency;

    public CurrencyNotDeclaredException(String _currency) {
        currency = _currency;
    }

    public String getCurrency() {
        return currency;
    }
}
