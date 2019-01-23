package ca.ulaval.glo4002.trading.infrastructure.repositories.transactions;

import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.domain.stocks.Stock;
import ca.ulaval.glo4002.trading.domain.transactions.Transaction;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;
import ca.ulaval.glo4002.trading.infrastructure.repositories.transactions.entities.TransactionEntity;

public class TransactionAssembler {
    public Transaction toDomainObject(TransactionEntity _transactionEntity) {
        TransactionNumber transactionNumber = new TransactionNumber(_transactionEntity.transactionNumber);
        AccountNumber accountNumber = new AccountNumber(_transactionEntity.accountNumber);

        Stock stock = new Stock(
                _transactionEntity.stockSymbol,
                _transactionEntity.marketSymbol,
                _transactionEntity.stockPrice);

        return new Transaction(transactionNumber,
                               _transactionEntity.date,
                               stock,
                               _transactionEntity.quantity,
                               _transactionEntity.type,
                               accountNumber);
    }
}
