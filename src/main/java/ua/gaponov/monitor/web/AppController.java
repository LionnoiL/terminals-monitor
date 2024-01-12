package ua.gaponov.monitor.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class AppController {

    @GetMapping("/")
    public String getMainPage() {
        return "redirect:terminals/list";
    }
}
