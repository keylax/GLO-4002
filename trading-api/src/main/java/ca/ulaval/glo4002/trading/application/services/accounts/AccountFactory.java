package ca.ulaval.glo4002.trading.application.services.accounts;

import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.requests.OpenAccountRequest;
import ca.ulaval.glo4002.trading.domain.accounts.Account;
import ca.ulaval.glo4002.trading.domain.accounts.Wallet;
import ca.ulaval.glo4002.trading.domain.investors.Investor;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AccountFactory {
    private static final String FULL_NAME_SEPARATOR = " ";
    private static final int FIRST_NAME_INDEX = 0;
    private static final int LAST_NAME_INDEX = 1;

    public Account createAccount(OpenAccountRequest _openAccountRequest) {
        String[] investorNameSplit = _openAccountRequest.investorName.split(FULL_NAME_SEPARATOR);
        String investorFirstName = investorNameSplit[FIRST_NAME_INDEX];
        String investorLastName = investorNameSplit[LAST_NAME_INDEX];

        ArrayList<String> walletCurrencies = new ArrayList<>();
        ArrayList<BigDecimal> walletCredits = new ArrayList<>();
        for (int i = 0; i < _openAccountRequest.credits.length; i++) {
            walletCurrencies.add(_openAccountRequest.credits[i].currency);
            walletCredits.add(new BigDecimal(_openAccountRequest.credits[i].amount));
        }

        Wallet wallet = new Wallet(walletCurrencies, walletCredits);

        Investor investor = new Investor(_openAccountRequest.investorId,
                                         investorFirstName,
                                         investorLastName);

        return new Account(investor, wallet);
    }
}
