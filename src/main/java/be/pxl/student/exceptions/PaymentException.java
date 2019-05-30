package be.pxl.student.exceptions;

public class PaymentException extends Exception {
    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(Throwable cause) {
        super(cause);
    }
}
