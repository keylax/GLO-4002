package ca.ulaval.glo4002.trading.domain.accounts;

public interface AccountRepository {
    int persistAccount(Account _account);
    void updateAccount(Account _account);
    Account findAccountByNumber(AccountNumber _accountNumber);
    boolean isAccountExistent(AccountNumber _accountNumber);
}
