package ca.ulaval.glo4002.trading.infrastructure.repositories.accounts;

import ca.ulaval.glo4002.trading.domain.accounts.Account;
import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.domain.accounts.AccountRepository;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.entities.AccountEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.exceptions.AccountAlreadyOpenException;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.exceptions.AccountNotFoundException;
import ca.ulaval.glo4002.trading.infrastructure.repositories.investors.InvestorEntity;
import ca.ulaval.glo4002.trading.infrastructure.repositories.EntityManagerProvider;

import javax.persistence.EntityManager;

public class HibernateAccountRepository implements AccountRepository {
    private AccountEntityFactory accountEntityFactory;
    private AccountAssembler accountAssembler;

    public HibernateAccountRepository() {
        accountEntityFactory = new AccountEntityFactory();
        accountAssembler = new AccountAssembler();
    }

    public int persistAccount(Account _account) {
        long investorId = _account.getInvestorId();
        if (investorHasAnAccount(investorId)) {
            throw new AccountAlreadyOpenException(investorId);
        }

        AccountEntity accountEntity = accountEntityFactory
                .createAccountEntity(_account);

        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(accountEntity);
            entityManager.getTransaction().commit();
        } catch (RuntimeException exception) {
            entityManager.getTransaction().rollback();
        }

        return accountEntity.id;
    }

    public void updateAccount(Account _account) {
        AccountEntity accountEntity = accountEntityFactory
                .createAccountEntity(_account);

        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(accountEntity);
            entityManager.getTransaction().commit();
        } catch (RuntimeException exception) {
            entityManager.getTransaction().rollback();
        }
    }

    public Account findAccountByNumber(AccountNumber _accountNumber) {
        int accountNumberId = _accountNumber.getId();

        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        AccountEntity accountEntity = entityManager.find(AccountEntity.class, accountNumberId);

        if (!isReturnedEntityTheWantedAccount(accountEntity, _accountNumber)) {
            throw new AccountNotFoundException(_accountNumber);
        }

        return accountAssembler.toDomainObject(accountEntity);
    }

    public boolean isAccountExistent(AccountNumber _accountNumber) {
        int accountNumberId = _accountNumber.getId();

        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        AccountEntity accountEntity = entityManager.find(AccountEntity.class, accountNumberId);

        return isReturnedEntityTheWantedAccount(accountEntity, _accountNumber);
    }

    private boolean investorHasAnAccount(long _investorId) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        InvestorEntity investorEntity = entityManager.find(
                InvestorEntity.class, _investorId);

        return investorEntity != null;
    }

    private boolean isReturnedEntityTheWantedAccount(AccountEntity _accountEntity, AccountNumber _accountNumber) {
        boolean sameAccount = true;

        if (_accountEntity == null) {
            sameAccount = false;
        } else {
            String expectedAccountNumber = _accountEntity.getAccountNumber();
            String actualAccountNumber = _accountNumber.toString();

            if (!expectedAccountNumber.equals(actualAccountNumber)) {
                sameAccount = false;
            }
        }

        return sameAccount;
    }
}
