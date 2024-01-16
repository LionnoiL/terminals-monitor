package ua.gaponov.monitor.errors;

public class NotFoundException extends RuntimeException {
    private ErrorMessages errorMessages;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(ErrorMessages errorMessages) {
        this.errorMessages = errorMessages;
    }

    public ErrorMessages getErrorMessages() {
        return errorMessages;
    }
}
