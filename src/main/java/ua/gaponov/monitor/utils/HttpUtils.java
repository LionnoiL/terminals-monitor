package ua.gaponov.monitor.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ua.gaponov.monitor.terminals.TerminalInfo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {

    static final RestTemplate restTemplate = new RestTemplate();

    public static TerminalInfo getTerminalInfo(String url){
        try {
            return restTemplate.getForObject(url, TerminalInfo.class);
        } catch (RestClientException e){
            return null;
        }
    }
}
