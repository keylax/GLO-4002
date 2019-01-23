package ca.ulaval.glo4002.trading.application.services.accounts;

import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses.WalletCurrencyResponse;
import ca.ulaval.glo4002.trading.domain.accounts.Account;
import ca.ulaval.glo4002.trading.domain.accounts.WalletCurrency;
import ca.ulaval.glo4002.trading.domain.investors.InvestorProfile;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses.AccountResponse;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses.InvestorProfileResponse;

import java.util.ArrayList;
import java.util.List;

public class AccountResponseAssembler {
    public AccountResponse toResponse(Account _account) {
        InvestorProfile investorProfile = _account.getInvestorProfile();
        InvestorProfileResponse investorProfileResponse = new InvestorProfileResponse(
                investorProfile.getInvestorType(),
                investorProfile.getFocusArea());

        List<WalletCurrency> accountCurrencies = _account.getCurrencies();
        List<WalletCurrencyResponse> walletCurrencyResponses = new ArrayList<>();
        for (WalletCurrency currency : accountCurrencies) {
            WalletCurrencyResponse walletCurrencyResponse =
                    new WalletCurrencyResponse(currency.getCurrencySymbol(), currency.getCredits().floatValue());
            walletCurrencyResponses.add(walletCurrencyResponse);
        }

        WalletCurrencyResponse[] displayWalletCurrencyResponses =
                new WalletCurrencyResponse[walletCurrencyResponses.size()];

        displayWalletCurrencyResponses = walletCurrencyResponses.toArray(displayWalletCurrencyResponses);

        float total = _account.getTotalCredits().floatValue();

        return new AccountResponse(
                _account.getAccountNumber().toString(),
                _account.getInvestorId(),
                displayWalletCurrencyResponses,
                investorProfileResponse,
                total);
    }
}
