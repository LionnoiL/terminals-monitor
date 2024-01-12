package ua.gaponov.monitor.user;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ua.gaponov.monitor.errors.ErrorMessages;
import ua.gaponov.monitor.errors.ValidationException;
import ua.gaponov.monitor.utils.Helper;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Component
public class UserValidator {

    protected ErrorMessages errorMessages = new ErrorMessages();
    @Setter
    private UserService userService;


    public void validate(UserDTO userDTO, boolean newUser) {
        errorMessages.clear();
        checkPassword(userDTO.getPassword());
        checkUserName(userDTO.getUserName(), newUser);

        if (!errorMessages.getErrors().isEmpty()) {
            throw new ValidationException(errorMessages);
        }
    }

    private void checkUserName(String userName, boolean newUser) {
        if (StringUtils.isBlank(userName)) {
            errorMessages.addError("Ім'я не може бути порожнім!");
        } else {
            if (newUser) {
                try {
                    userService.findUserByName(userName);
                    errorMessages.addError("Користувач з ім'ям " + userName + " вже зареєстрований");
                } catch (NoSuchElementException ex) {
                    //nop
                }
            }
        }
    }

    private void checkPassword(String password) {
        if (StringUtils.isBlank(password)) {
            errorMessages.addError("Пароль не може бути порожнім!");
        }

        String passwordRegexPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})";
        if (!Helper.patternMatches(password, passwordRegexPattern)) {
            errorMessages.addError(
                "Не вірний формат паролю! Довжина паролю повинна бути не меньше 8 символів, латинські літери та цифри. Мінімум одна велика літера.");
        }
    }
}
