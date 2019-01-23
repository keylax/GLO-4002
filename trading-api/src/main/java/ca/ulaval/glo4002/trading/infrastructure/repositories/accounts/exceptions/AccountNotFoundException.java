package ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.exceptions;

import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;

public class AccountNotFoundException extends RuntimeException {
    private AccountNumber accountNumber;

    public AccountNotFoundException(AccountNumber _accountNumber) {
        accountNumber = _accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber.toString();
    }
}
