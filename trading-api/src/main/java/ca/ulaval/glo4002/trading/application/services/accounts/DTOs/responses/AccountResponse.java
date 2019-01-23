package ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses;

public class AccountResponse {
    public String accountNumber;
    public long investorId;
    public InvestorProfileResponse investorProfile;
    public WalletCurrencyResponse[] credits;
    public float total;

    public AccountResponse(String _accountNumber,
                           long _investorId,
                           WalletCurrencyResponse[] _currencies,
                           InvestorProfileResponse _investorProfileResponse,
                           float _total) {
        accountNumber = _accountNumber;
        investorId = _investorId;
        credits = _currencies;
        investorProfile = _investorProfileResponse;
        total = _total;
    }
}
