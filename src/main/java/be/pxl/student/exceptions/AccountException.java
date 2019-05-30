package be.pxl.student.exceptions;

public class AccountException extends Exception {
    public AccountException(String message) {
        super(message);
    }

    public AccountException(Throwable cause) {
        super(cause);
    }
}
