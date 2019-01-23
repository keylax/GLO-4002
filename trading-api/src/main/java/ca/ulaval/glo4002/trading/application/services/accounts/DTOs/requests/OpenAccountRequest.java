package ca.ulaval.glo4002.trading.application.services.accounts.DTOs.requests;

public class OpenAccountRequest {
    public long investorId;
    public String investorName;
    public String email;
    public WalletCurrencyRequest[] credits;
}
