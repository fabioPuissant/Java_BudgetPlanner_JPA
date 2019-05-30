package be.pxl.student.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = "Accounts.findAll", query = "SELECT a FROM Account as a")
@Table(name = "Account")
public class Account implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String number;
    private String iban;
    private String name;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Payment> payments;

    public Account() {
    }

    public Account(String number, String iban, String name) {
        this.id = id;
        this.number = number;
        this.iban = iban;
        this.name = name;
    }

    public Account(int id, String number, String iban, String name) {
        this.id = id;
        this.number = number;
        this.iban = iban;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", iban='" + iban + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(number, account.number) &&
                Objects.equals(iban, account.iban) &&
                Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, iban, name);
    }

    @OneToMany(mappedBy = "counterAccount")
    private Collection<Payment> payment;

    public Collection<Payment> getPayment() {
        return payment;
    }

    public void setPayment(Collection<Payment> payment) {
        this.payment = payment;
    }
}
