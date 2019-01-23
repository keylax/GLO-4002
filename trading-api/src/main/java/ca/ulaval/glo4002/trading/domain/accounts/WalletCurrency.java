package ca.ulaval.glo4002.trading.domain.accounts;

import java.math.BigDecimal;

public class WalletCurrency {
    private String currencySymbol;
    private BigDecimal credits;

    public WalletCurrency(String _currencySymbol, BigDecimal _initialCredits) {
        currencySymbol = _currencySymbol;
        credits = _initialCredits;
    }

    public void addCredits(BigDecimal _credits) {
        credits = credits.add(_credits);
    }

    public void removeCredits(BigDecimal _credits) {
        credits = credits.subtract(_credits);
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

}
