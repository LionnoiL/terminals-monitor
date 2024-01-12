package ua.gaponov.monitor.errors;

public class ValidationException extends RuntimeException {
    private ErrorMessages errorMessages;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(ErrorMessages errorMessages) {
        this.errorMessages = errorMessages;
    }

    public ErrorMessages getErrorMessages() {
        return errorMessages;
    }
}
