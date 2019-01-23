package ca.ulaval.glo4002.trading.domain.transactions;

import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;

import java.time.LocalDateTime;

public interface TransactionRepository {
    void createTransaction(Transaction _transaction);
    Transaction findTransactionByIdAndAccountNumber(TransactionNumber _transactionId, AccountNumber _accountNumber);
    Transaction[] findAccountTransactionsInDateRange(
            AccountNumber _accountNumber,
            LocalDateTime _lowerBoundDate,
            LocalDateTime _upperBoundDate);
}
