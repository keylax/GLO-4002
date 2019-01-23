package ca.ulaval.glo4002.trading.interfaces.rest.accounts.validators;

import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.requests.OpenAccountRequest;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.requests.WalletCurrencyRequest;

public class AccountRequestValidator {
    private static final String FULL_NAME_SEPARATOR = " ";
    private static final int NUMBER_OF_PARTS_IN_A_FULL_NAME = 2;
    
    public boolean isAccountRequestValid(OpenAccountRequest _accountRequest) {
        boolean isInvestorNameNull = _accountRequest.investorName == null;
        boolean areCurrenciesNullOrEmpty = areCurrenciesNullOrEmpty(_accountRequest);

        boolean isNameAFullName = false;
        if (!isInvestorNameNull) {
            String[] investorNameSplit = _accountRequest.investorName.split(FULL_NAME_SEPARATOR);
            isNameAFullName = investorNameSplit.length == NUMBER_OF_PARTS_IN_A_FULL_NAME;
        }

        return !isInvestorNameNull
               && isNameAFullName
               && _accountRequest.email != null
               && !areCurrenciesNullOrEmpty;
    }

    private boolean areCurrenciesNullOrEmpty(OpenAccountRequest _accountRequest) {
        if (_accountRequest.credits == null) {
            return true;
        } else {
            if (_accountRequest.credits.length == 0) {
                return true;
            } else {
                for (WalletCurrencyRequest walletCurrencyRequest : _accountRequest.credits) {
                    if (walletCurrencyRequest.currency == null) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
