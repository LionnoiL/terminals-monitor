package ua.gaponov.monitor.errors;

public class UtpServerException extends RuntimeException {
    private ErrorMessages errorMessages;

    public UtpServerException(String message) {
        super(message);
    }

    public UtpServerException(ErrorMessages errorMessages) {
        this.errorMessages = errorMessages;
    }

    public ErrorMessages getErrorMessages() {
        return errorMessages;
    }
}
