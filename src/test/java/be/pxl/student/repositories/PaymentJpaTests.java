package be.pxl.student.repositories;

import be.pxl.student.exceptions.PaymentException;
import be.pxl.student.model.Account;
import be.pxl.student.model.Label;
import be.pxl.student.model.Payment;
import be.pxl.student.utils.ConnectionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class PaymentJpaTests {
    private PaymentJpa dao;
    private EntityManager entityManager;

    @Before
    public void beforeTests() throws SQLException {
        this.entityManager =
                Persistence.createEntityManagerFactory("budgetplannerTest").createEntityManager();
        this.dao = new PaymentJpa(entityManager);
    }

    @After
    public void afterTests() throws SQLException {
        this.dao = null;
    }

    @Test
    public void addPayment_shouldAddPayment_Payment() {
        //Arrange
        Payment payment = getTestPayment();

        //Act
        Payment result = dao.addPayment(payment);

        //Assert
        Assert.assertNotNull(result);
        Assert.assertNotEquals(result.getId(), 0);
        Assert.assertEquals(payment, result);
    }

    @Test
    public void getAllPayments_shouldReturnAllPayments() throws PaymentException {
        //Arrange
        dao.addPayment(getTestPayment());

        //Act
        List<Payment> result = dao.getAllPayments();

        //Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void getById_shouldReturnPaymentWithGivenId() throws PaymentException {
        //Arrange
        Payment expected = dao.addPayment(getTestPayment());
        Assert.assertNotNull(expected);

        //Act
        Payment actual = dao.getById(expected.getId());

        //Assert
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void getPaymentsByAccountId_shouldReturnPaymentsWithGivenAccountId() {
        //Arrange
        AccountJpa accountJpa = new AccountJpa(entityManager);
        Account account = getTestingAccount();
        accountJpa.addAccount(account);

        int size = seedMultiplePaymentsWithAccount(account);

        //Act
        List<Payment> paymentsOfAccount = dao.getPaymentsByAccountId(account.getId());

        //Assert
        Assert.assertNotNull(paymentsOfAccount);
        Assert.assertEquals(paymentsOfAccount.size(), size);
    }

    @Test
    public void getPaymentsOfLabel_shouldReturnPaymentsWithGivenLabelId() throws PaymentException {
        //Arrange
        Label label = getTestingLabel();
        int size = seedMultiplePaymentsWithLabel(label);

        //Act
        List<Payment> paymentsOfLabel = dao.getPaymentsOfLabel(label.getId());

        //Assert
        Assert.assertNotNull(paymentsOfLabel);
        Assert.assertEquals(paymentsOfLabel.size(), size);
    }

    @Test
    public void addLabelToPayment_shouldAddLabelToPayment() throws PaymentException {
        //Arrange
        Payment payment = dao.addPayment(getTestPayment());
        Label label = getTestingLabel();

        Assert.assertNotNull(payment);
        Assert.assertNotEquals(payment.getId(), 0);

        //Act
        Payment actual = dao.addLabelToPayment(payment, label);

        //Assert
        Assert.assertNotNull(actual);
        Assert.assertEquals(actual.getLabel(), label);
    }

    @Test
    public void addLabelToAllPaymentOfAccount() throws PaymentException {
        //Arrange
        Account account = getTestingAccount();
        seedMultiplePaymentsWithAccount(account);
        Label label = getTestingLabel();

        //Act
        List<Payment> payments = dao.addLabelToAllPaymentOfAccount(account, label);

        //Assert
        Assert.assertNotNull(payments);
        payments.forEach(payment -> {
            Assert.assertEquals(payment.getLabel(), label);
            Assert.assertEquals(payment.getAccount(), account);
        });
    }

    private int seedMultiplePaymentsWithLabel(Label label) {
        IntStream.rangeClosed(0, 2).forEach(i -> {
            Payment payment = getTestPayment();
            payment.setLabel(label);
            dao.addPayment(payment);
        });

        return 3;
    }

    private int seedMultiplePaymentsWithAccount(Account account) {
        IntStream.rangeClosed(0, 2).forEach(i -> {
            Payment payment = getTestPayment();
            payment.setAccount(account);
            dao.addPayment(payment);
        });

        return 3;
    }

    private Label getTestingLabel() {
        return new Label("test", "test");
    }

    private Account getTestingAccount() {
        return new Account("test", "test", "test");
    }

    private Payment getTestPayment() {
        Payment payment = new Payment();
        payment.setCurrency("euro");
        payment.setAmount(500F);
        payment.setDetail("Testing Details");
        return payment;
    }
}
