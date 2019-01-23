package ca.ulaval.glo4002.trading.application.services.accounts;

import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.requests.OpenAccountRequest;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses.AccountResponse;
import ca.ulaval.glo4002.trading.domain.accounts.Account;
import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.domain.accounts.AccountRepository;

public class AccountService {
    private AccountRepository repository;
    private AccountFactory factory;
    private AccountResponseAssembler assembler;

    public AccountService(AccountRepository _repository, AccountFactory _factory, AccountResponseAssembler _assembler) {
        repository = _repository;
        factory = _factory;
        assembler = _assembler;
    }

    public String openAccount(OpenAccountRequest _openAccountRequest) {
        Account newAccount = factory.createAccount(_openAccountRequest);
        int accountPersistedID = repository.persistAccount(newAccount);
        AccountNumber accountNumber = new AccountNumber(accountPersistedID, newAccount.getInvestorInitials());

        return accountNumber.toString();
    }

    public AccountResponse consultAccount(String _accountNumber) {
        AccountNumber accountNumber = new AccountNumber(_accountNumber);
        Account selectedAccount = repository.findAccountByNumber(accountNumber);

        return assembler.toResponse(selectedAccount);
    }
}
