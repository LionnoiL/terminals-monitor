package ua.gaponov.monitor.errors;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorMessages {

    private List<String> errors = new ArrayList<>();

    public void addError(String message) {
        errors.add(message);
    }

    public void clear() {
        errors.clear();
    }
}
