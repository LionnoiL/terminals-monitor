package ua.gaponov.monitor.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.gaponov.monitor.errors.ErrorMessages;
import ua.gaponov.monitor.errors.InfoMessages;
import ua.gaponov.monitor.user.UserService;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getLoginPage() {
        return "user/login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("errorsMessages") ErrorMessages errorsMessages,
        @ModelAttribute("infoMessages") InfoMessages infoMessages,
        @RequestParam(value = "username") String username,
        @RequestParam(value = "password") String password) {
        try {
            userService.findUserByName(username);
            errorsMessages.addError("Користувач вже зареєстрований. Ви можете увійти.");
            return "user/login";
        } catch (NoSuchElementException e) {
            userService.createNewUser(username, password);
            infoMessages.addMessage("Ви успішно зареєструвалися. Можете увійти.");
            return "user/login";
        }
    }
}
