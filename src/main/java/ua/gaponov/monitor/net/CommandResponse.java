package ua.gaponov.monitor.net;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class CommandResponse {

    boolean ok;
    String response;
    String message;
}
