package ua.gaponov.monitor.net;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ua.gaponov.monitor.terminals.TerminalInfo;

import java.net.InetAddress;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NetUtils {

    static final RestTemplate restTemplate = new RestTemplate();

    public static TerminalInfo getTerminalInfo(String url) {
        try {
            return restTemplate.getForObject(url, TerminalInfo.class);
        } catch (RestClientException e) {
            return null;
        }
    }

    public static boolean pingAddress(String address) {
        try {
            InetAddress inetAddress = InetAddress.getByName(address);
            return inetAddress.isReachable(7);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
