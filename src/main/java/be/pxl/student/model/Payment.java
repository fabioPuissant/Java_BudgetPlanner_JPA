package be.pxl.student.model;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
@Entity
@NamedQuery(name = "Payment.findAll", query = "select p from Payment as p")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDate date;
    private float amount;
    private String currency;
    private String detail;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "counterAccountId")
    private Account counterAccount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "labelId")
    private Label label;

    public Payment() {
        date = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getCounterAccountId() {
        return this.counterAccount;
    }

    public void setCounterAccountId(Account counterAccount) {
        this.counterAccount = counterAccount;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

}
