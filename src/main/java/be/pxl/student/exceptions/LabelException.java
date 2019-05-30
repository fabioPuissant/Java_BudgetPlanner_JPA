package be.pxl.student.exceptions;

public class LabelException extends Exception {
    public LabelException(String message) {
        super(message);
    }

    public LabelException(Throwable cause) {
        super(cause);
    }
}
