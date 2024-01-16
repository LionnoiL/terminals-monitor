package ua.gaponov.monitor.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ua.gaponov.monitor.errors.ErrorMessages;
import ua.gaponov.monitor.errors.InfoMessages;
import ua.gaponov.monitor.errors.NotFoundException;
import ua.gaponov.monitor.errors.ValidationException;
import ua.gaponov.monitor.terminals.Terminal;
import ua.gaponov.monitor.terminals.TerminalDTO;
import ua.gaponov.monitor.terminals.TerminalService;

import java.util.List;
import java.util.NoSuchElementException;

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RedirectView getSearch(){
        terminalService.search();
        return new RedirectView("/terminals/list");
    }

    @GetMapping("/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getEditTerminal(@RequestParam(value = "id") Long id) {
        ModelAndView result = new ModelAndView("terminals/edit");
        TerminalDTO terminal = terminalService.getTerminalDtoByArmId(id);
        result.addObject("terminal", terminal);
        return result;
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String postUpdateTerminal(@ModelAttribute("errorsMessages") ErrorMessages errorsMessages,
                                     @ModelAttribute("infoMessages") InfoMessages infoMessages,
                                           @ModelAttribute("terminal") TerminalDTO terminal) {
        try {
            terminalService.getTerminalDtoByArmId(terminal.getArmId());
            terminalService.save(terminal);
        } catch (NotFoundException e){
            errorsMessages.addError("Терміналу з armId: [" + terminal.getArmId() + "] не існує в базі!");
            return "terminals/edit";
        }
        return "redirect:/terminals/list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getCreateTerminal() {
        ModelAndView result = new ModelAndView("terminals/add");
        TerminalDTO terminal = new TerminalDTO();
        result.addObject("terminal", terminal);
        return result;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String postCreateTerminal(@ModelAttribute("errorsMessages") ErrorMessages errorsMessages,
                                     @ModelAttribute("infoMessages") InfoMessages infoMessages,
                                     @ModelAttribute("terminal") TerminalDTO terminal) {
        try {
            terminalService.getByArmId(terminal.getArmId());
        } catch (NotFoundException e){
            terminalService.save(terminal);
            return "redirect:/terminals/list";
        }
        errorsMessages.addError("Терміналу з armId: [" + terminal.getArmId() + "] вже існує в базі!");
        return "terminals/add";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String postDeleteTerminal(@RequestParam Long id) {
        Terminal terminal = terminalService.getByArmId(id);
        terminalService.delete(terminal);
        return "redirect:/terminals/list";
    }
}
