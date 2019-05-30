package be.pxl.student.repositories;

import be.pxl.student.model.Account;
import be.pxl.student.model.Label;
import be.pxl.student.model.Payment;
import be.pxl.student.exceptions.PaymentException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentJpa {

    private EntityManager entityManager;

    public PaymentJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Payment addPayment(Payment payment) {
        entityManager.getTransaction().begin();
        entityManager.persist(payment);
        entityManager.getTransaction().commit();
        return payment;
    }

    public Payment getById(int id){
        return entityManager.find(Payment.class, id);
    }

    public List<Payment> getAllPayments() {
        TypedQuery<Payment> query = entityManager.createNamedQuery("Payment.findAll", Payment.class);
        return query.getResultList();
    }

    public List<Payment> getPaymentsByAccountId(int accountId) {
        Query query= entityManager.createQuery("select p from Payment as p where p.account.id=:accountId");
        query.setParameter("accountId", accountId);
        return (List<Payment>) query.getResultList();
    }

    public List<Payment> getPaymentsOfLabel(int labelId) {
        Query query= entityManager.createQuery("select p from Payment as p where p.label.id=:labelId");
        query.setParameter("labelId", labelId);
        return (List<Payment>) query.getResultList();
    }

    public Payment addLabelToPayment(Payment payment, Label label) {
        entityManager.getTransaction().begin();
        payment.setLabel(label);
        payment = entityManager.merge(payment);
        entityManager.getTransaction().commit();

        return payment;
    }

    public List<Payment> addLabelToAllPaymentOfAccount(Account account, Label label) {
        entityManager.getTransaction().begin();
        TypedQuery<Payment> query =
                entityManager.createQuery("select p from Payment as p where p.account.id=:accountId", Payment.class);
        query.setParameter("accountId", account.getId());
        List<Payment> payments = query.getResultList();

        payments.forEach(p->{
            p.setLabel(label);
        });
        entityManager.getTransaction().commit();

        return payments;
    }
}
