package ua.gaponov.monitor.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ua.gaponov.monitor.terminals.TerminalDTO;
import ua.gaponov.monitor.terminals.TerminalService;

import java.util.List;

@RequestMapping("/terminals")
@RequiredArgsConstructor
@Controller
public class TerminalsController {

    @Value("${terminals.list.reload.interval}")
    private int reloadPageInterval;

    private final TerminalService terminalService;

    @GetMapping("/list")
    public ModelAndView getMainPage() {
        ModelAndView result = new ModelAndView("terminals/list");
        List<TerminalDTO> terminals = terminalService.getAllTerminals();
        result.addObject("terminals", terminals);
        result.addObject("reloadTime", reloadPageInterval);
        return result;
    }

    @GetMapping("/scan")
    public RedirectView getSearch(){
        terminalService.search();
        return new RedirectView("/terminals/list");
    }
}
