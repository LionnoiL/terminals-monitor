package ua.gaponov.monitor.net;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.gaponov.monitor.terminals.TerminalCommand;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class CommandResult {

    private TerminalCommand command;
    private String result;
    private String message;
}
