package ua.gaponov.monitor.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.gaponov.monitor.report.DatePeriod;
import ua.gaponov.monitor.report.deleted.DeletedOrderItemDTO;
import ua.gaponov.monitor.report.deleted.DeletedOrderItemsService;
import ua.gaponov.monitor.terminals.Terminal;
import ua.gaponov.monitor.terminals.TerminalService;
import ua.gaponov.monitor.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/reports")
@RequiredArgsConstructor
@Controller
public class ReportsController {

    private final TerminalService terminalService;
    private final DeletedOrderItemsService deletedOrderItemsService;

    @GetMapping("/deletedItems")
    public ModelAndView getMainPage(@RequestParam(value = "terminalId") int terminalId,
                                    @RequestParam(value = "startDate", required = false) String startDate,
                                    @RequestParam(value = "endDate", required = false) String endDate) {
        DatePeriod datePeriod = DateUtils.getCurrentDayPeriod();
        if (startDate != null){
            datePeriod.setStartDate(DateUtils.getLocalDateFromString(startDate, false));
        }
        if (endDate != null){
            datePeriod.setEndDate(DateUtils.getLocalDateFromString(endDate, true));
        }

        Terminal terminal = terminalService.getByArmId(terminalId);
        ModelAndView result = new ModelAndView("reports/deletedItems");

        List<DeletedOrderItemDTO> deletedItems = deletedOrderItemsService.getDeletedItems(terminal, datePeriod);
        result.addObject("terminal", terminal);
        result.addObject("items", deletedItems);
        result.addObject("startDate", DateUtils.formatDateTime(datePeriod.getStartDate()));
        result.addObject("endDate", DateUtils.formatDateTime(datePeriod.getEndDate()));
        return result;
    }
}
