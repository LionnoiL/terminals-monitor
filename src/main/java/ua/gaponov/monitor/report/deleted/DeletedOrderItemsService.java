package ua.gaponov.monitor.report.deleted;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import ua.gaponov.monitor.net.CommandResponse;
import ua.gaponov.monitor.net.NetUtils;
import ua.gaponov.monitor.report.DatePeriod;
import ua.gaponov.monitor.terminals.Terminal;
import ua.gaponov.monitor.terminals.TerminalCommand;
import ua.gaponov.monitor.utils.JsonConverter;

import java.util.List;

import static ua.gaponov.monitor.utils.JsonConverter.GSON;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeletedOrderItemsService {

    public List<DeletedOrderItemDTO> getDeletedItems(Terminal terminal, DatePeriod datePeriod) {
        String json = GSON.toJson(datePeriod);
        String escapedJsonString = StringEscapeUtils.escapeJson(json);

        CommandResponse commandResponse = NetUtils.sendCommand(
                "http://" + terminal.getIpAddress() + ":5555/command",
                TerminalCommand.GET_DELETED_ITEMS,
                escapedJsonString
        );
        String response = commandResponse.getResponse();
        return JsonConverter.convertJsonStringToList(response, DeletedOrderItemDTO.class);
    }
}
