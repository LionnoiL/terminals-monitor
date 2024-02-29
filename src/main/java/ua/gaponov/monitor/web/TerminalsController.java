package ua.gaponov.monitor.web;

import lombok.RequiredArgsConstructor;
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
import ua.gaponov.monitor.terminals.*;

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RedirectView getSearch(){
        terminalService.search();
        return new RedirectView("/terminals/list");
    }

    @GetMapping("/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getEditTerminal(@ModelAttribute("errorsMessages") ErrorMessages errorsMessages,
                                        @ModelAttribute("infoMessages") InfoMessages infoMessages,
                                        @RequestParam(value = "id") int id) {
        ModelAndView result = new ModelAndView("terminals/edit");
        TerminalDTO terminal = terminalService.getTerminalDtoByArmId(id);
        TerminalProperties properties = TerminalService.getProperties(terminalService.getByArmId(id));
        if (properties == null){
            properties = new TerminalProperties();
            properties.setTerminalId(id);
            errorsMessages.addError("Немає зв'язку з терміналом. Зберегти налаштування не можливо.");
        }
        result.addObject("terminal", terminal);
        result.addObject("properties", properties);
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

    @PostMapping("/properties/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RedirectView postUpdatePropertiesTerminal(RedirectAttributes redirectAttributes,
                                     @ModelAttribute("errorsMessages") ErrorMessages errorsMessages,
                                     @ModelAttribute("infoMessages") InfoMessages infoMessages,
                                     @ModelAttribute("properties") TerminalProperties properties) {
        boolean ok;
        try {
            Terminal terminal = terminalService.getByArmId(properties.getTerminalId());
            ok = TerminalService.setProperties(terminal, properties);
        } catch (NotFoundException e){
            errorsMessages.addError("Терміналу з armId: [" + properties.getTerminalId() + "] не існує в базі!");
            redirectAttributes.addFlashAttribute("errorsMessages", errorsMessages);
            ok = false;
        }
        if (ok) {
            infoMessages.addMessage("Налаштування відправленні на термінал");
            redirectAttributes.addFlashAttribute("infoMessages", infoMessages);
        } else {
            errorsMessages.addError("Помилка відправки запиту!");
            redirectAttributes.addFlashAttribute("errorsMessages", errorsMessages);
        }

        return new RedirectView("/terminals/edit?id=" + properties.getTerminalId());
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
    public String postDeleteTerminal(@RequestParam int id) {
        Terminal terminal = terminalService.getByArmId(id);
        terminalService.delete(terminal);
        return "redirect:/terminals/list";
    }

    @GetMapping("/{id}/command")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RedirectView getCommandTerminal(RedirectAttributes redirectAttributes,
                                           @PathVariable int id, @RequestParam TerminalCommand name) {
        Terminal terminal = terminalService.getByArmId(id);
        boolean ok = terminalService.executeSimpleCommand(terminal, name);
        if (!ok) {
            ErrorMessages errorMessages = new ErrorMessages();
            errorMessages.addError("Помилка виконання команди");
            redirectAttributes.addFlashAttribute("errorsMessages", errorMessages);
        } else {
            InfoMessages infoMessages = new InfoMessages();
            infoMessages.addMessage("Команда виконана");
            redirectAttributes.addFlashAttribute("infoMessages", infoMessages);
        }
        return new RedirectView("/terminals/edit?id=" + id);
    }
}
