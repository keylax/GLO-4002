package ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses;

public class WalletCurrencyResponse {
    public final String currency;
    public final float amount;

    public WalletCurrencyResponse(String _currency, float _amount) {
        currency = _currency;
        amount = _amount;
    }
}
