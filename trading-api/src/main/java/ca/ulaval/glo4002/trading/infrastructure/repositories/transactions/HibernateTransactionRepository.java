package ca.ulaval.glo4002.trading.infrastructure.repositories.transactions;

import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.domain.transactions.Transaction;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionRepository;
import ca.ulaval.glo4002.trading.infrastructure.repositories.transactions.entities.TransactionEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.EntityManagerProvider;
import ca.ulaval.glo4002.trading.infrastructure.repositories.transactions.exceptions.TransactionNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class HibernateTransactionRepository implements TransactionRepository {
    private static final String LOWER_BOUND_DATE_PARAMETER = "lowerBoundDate";
    private static final String UPPER_BOUND_DATE_PARAMETER = "upperBoundDate";
    private static final String ACCOUNT_NUMBER_STRING_PARAMETER = "accountNumberString";

    private TransactionEntityFactory transactionEntityFactory;
    private TransactionAssembler transactionAssembler;

    public HibernateTransactionRepository() {
        transactionEntityFactory = new TransactionEntityFactory();
        transactionAssembler = new TransactionAssembler();
    }

    public void createTransaction(Transaction _transaction) {
        TransactionEntity transactionEntity = transactionEntityFactory
                .createTransactionEntity(_transaction);

        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(transactionEntity);
            entityManager.getTransaction().commit();
        } catch (RuntimeException exception) {
            entityManager.getTransaction().rollback();
        }
    }

    public Transaction findTransactionByIdAndAccountNumber(
            TransactionNumber _transactionNumber,
            AccountNumber _accountNumber) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        TransactionEntity transactionEntity = entityManager.find(
                TransactionEntity.class,
                _transactionNumber.getNumber());

        if (transactionEntity != null && transactionEntity.accountNumber.equals(_accountNumber.toString())) {
            Transaction transaction = transactionAssembler.toDomainObject(transactionEntity);
            return transaction;
        }

        throw new TransactionNotFoundException(_transactionNumber);
    }

    public Transaction[] findAccountTransactionsInDateRange(
            AccountNumber _accountNumber,
            LocalDateTime _lowerBoundDate,
            LocalDateTime _upperBoundDate) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        Query query = entityManager.createQuery(
                "select o from TransactionEntity o"
                + " where o.accountNumber = :" + ACCOUNT_NUMBER_STRING_PARAMETER
                + " and (o.date BETWEEN :" + LOWER_BOUND_DATE_PARAMETER
                + " and :" + UPPER_BOUND_DATE_PARAMETER + ")",
                TransactionEntity.class);
        query.setParameter(LOWER_BOUND_DATE_PARAMETER, _lowerBoundDate);
        query.setParameter(UPPER_BOUND_DATE_PARAMETER, _upperBoundDate);
        query.setParameter(ACCOUNT_NUMBER_STRING_PARAMETER, _accountNumber.toString());
        List<TransactionEntity> listResultingTransactionsEntity = query.getResultList();

        List<Transaction> listResultingTransactions = assembleTransactions(listResultingTransactionsEntity);
        Transaction[] arrayResultingTransactions = new Transaction[listResultingTransactions.size()];
        return listResultingTransactions.toArray(arrayResultingTransactions);
    }

    private List<Transaction> assembleTransactions(List<TransactionEntity> _listTransactionEntity) {
        return _listTransactionEntity.stream()
                                          .map(transactionPersistence ->
                                               transactionAssembler.toDomainObject(transactionPersistence))
                                          .collect(Collectors.toList());
    }
}
