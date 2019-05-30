package be.pxl.student.repositories;

import be.pxl.student.model.Account;
import be.pxl.student.exceptions.AccountException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountJpa {

    private EntityManager entityManager;

    public AccountJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Account addAccount(Account account) {
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
        return account;
    }

    public List<Account> getAllAccounts() {
        TypedQuery<Account> query = entityManager.createNamedQuery("Accounts.findAll", Account.class);
        return query.getResultList();
    }

    public Account getById(int id) throws AccountException {
        Account account = entityManager.find(Account.class, id);
        if(account != null){
            return account;
        }
        throw new AccountException("No account found with id of 0");
    }

    public Account updateAccount(Account account) throws AccountException {
       entityManager.getTransaction().begin();
       entityManager.merge(account);
       entityManager.getTransaction().commit();
       return entityManager.find(Account.class, account.getId());
    }
}
