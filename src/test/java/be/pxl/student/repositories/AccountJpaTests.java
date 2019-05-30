package be.pxl.student.repositories;

import be.pxl.student.model.Account;
import be.pxl.student.exceptions.AccountException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

public class AccountJpaTests {
    private AccountJpa dao;
    private EntityManager entityManager;

    @Before
    public void beforeTests() throws SQLException {
        entityManager = Persistence
                .createEntityManagerFactory("budgetplannerTest")
                .createEntityManager();
        this.dao = new AccountJpa(entityManager);
    }

    @After
    public void afterTests() throws SQLException {
        this.entityManager.close();
        this.entityManager.getEntityManagerFactory().close();
        this.dao = null;
    }

    @Test
    public void addAccount_shouldReturnAnAccountWithAnI_account() {
        //Arrange
        Account testAccount = new Account("TestAccount", "TestAccount", "TestAccount");

        //Act
        Account actual = dao.addAccount(testAccount);

        //Assert
        Assert.assertNotEquals(actual.getId(), 0);
        Assert.assertEquals(testAccount, actual);
    }

    @Test
    public void getAll_shouldReturnListOfAccounts() throws AccountException {
        //Arrange
        Account expectedAccount =
                dao.addAccount(new Account("TestAccount", "TestAccount", "TestAccount"));

        //Act
        Assert.assertNotNull(expectedAccount);
        List<Account> foundAccounts = dao.getAllAccounts();

        //Assert
        Assert.assertNotNull(foundAccounts);
        Assert.assertEquals(foundAccounts.size(), 1);
    }

    @Test
    public void getById_shouldReturnAccountWithGivenId_Account() throws AccountException {
        //Arrange
        Account expectedAccount =
                dao.addAccount(new Account("TestAccount", "TestAccount", "TestAccount"));

        //Act
        Assert.assertNotNull(expectedAccount);
        Account actualAccountFound = dao.getById(expectedAccount.getId());

        //Assert
        Assert.assertEquals(expectedAccount, actualAccountFound);
        AccountException exception = null;
        try {
            dao.getById(0);
        } catch (AccountException ex) {
            exception = ex;
        }

        Assert.assertNotNull(exception);
        Assert.assertEquals(exception.getMessage(), "No account found with id of 0");
    }

    @Test
    public void updateAccountById_shouldReturnUpdatedAccount() throws AccountException {
        //Arrange
        Account initialAccount =
                dao.addAccount(new Account("TestAccount", "TestAccount", "TestAccount"));
        Assert.assertNotNull(initialAccount);
        Assert.assertNotEquals(initialAccount.getId(), 0);
        String newName = "NewName";
        //Act
        initialAccount.setName(newName);
        Account updatedAccount = dao.updateAccount(initialAccount);

        //Assert
        Assert.assertNotNull(updatedAccount);
        Assert.assertEquals(initialAccount, updatedAccount);
        Assert.assertEquals(updatedAccount.getName(), newName);
    }
}
