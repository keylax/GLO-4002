package ca.ulaval.glo4002.trading.infrastructure.repositories.transactions;

import ca.ulaval.glo4002.trading.domain.transactions.Transaction;
import ca.ulaval.glo4002.trading.infrastructure.repositories.transactions.entities.TransactionEntity;

public class TransactionEntityFactory {
    public TransactionEntity createTransactionEntity(Transaction _transaction) {
        return new TransactionEntity(_transaction.getTransactionNumber().getNumber(),
                                          _transaction.getDate(),
                                          _transaction.getQuantity(),
                                          _transaction.getStockSymbol(),
                                          _transaction.getMarketSymbol(),
                                          _transaction.getStockPrice(),
                                          _transaction.getAssociatedAccountNumber().toString(),
                                          _transaction.getType());
    }
}
