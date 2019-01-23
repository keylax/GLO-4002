package ca.ulaval.glo4002.trading.infrastructure.repositories.transactions;

import ca.ulaval.glo4002.trading.domain.transactions.Transaction;
import ca.ulaval.glo4002.trading.infrastructure.repositories.transactions.entities.TransactionEntity;

public class TransactionEntityComparer {
    public static boolean areEquivalent(Transaction _transaction, TransactionEntity _transactionEntity) {
        return _transaction.getTransactionNumber().getNumber() == _transactionEntity.transactionNumber
                && _transaction.getDate() ==  _transactionEntity.date
                && _transaction.getQuantity() == _transactionEntity.quantity
                && _transaction.getStockSymbol().equals(_transactionEntity.stockSymbol)
                && _transaction.getMarketSymbol().equals(_transactionEntity.marketSymbol)
                && _transaction.getStockPrice() == _transactionEntity.stockPrice
                && _transaction.getAssociatedAccountNumber().toString().equals(_transactionEntity.accountNumber)
                && _transaction.getType().equals(_transactionEntity.type);
    }
}
