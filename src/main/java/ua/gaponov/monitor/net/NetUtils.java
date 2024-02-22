package ua.gaponov.monitor.net;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ua.gaponov.monitor.terminals.TerminalCommands;
import ua.gaponov.monitor.terminals.TerminalInfo;

import java.net.InetAddress;
import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NetUtils {

    private static final int TIMEOUT_IN_MILLISECONDS = 500; // Укажите нужный вам таймаут в миллисекундах

    private static final RestTemplate restTemplate;

    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(TIMEOUT_IN_MILLISECONDS);
        requestFactory.setReadTimeout(TIMEOUT_IN_MILLISECONDS);

        restTemplate = new RestTemplate(requestFactory);
    }

    static final Duration timeout = Duration.ofSeconds(2);

    public static TerminalInfo getTerminalInfo(String url) {
        try {
            return restTemplate.getForObject(url, TerminalInfo.class);
        } catch (RestClientException e) {
            System.out.println(e);
            return null;
        }
    }

    public static boolean sendSimpleCommand(String url, TerminalCommands command) {
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,
                    command.toString(),
                    String.class,
                    timeout.toMillis());
            HttpStatusCode statusCode = responseEntity.getStatusCode();
            int statusValue = statusCode.value();

            if (statusCode.is2xxSuccessful()) {
                return true;
            } else {
                System.err.println("Request failed with status code: " + statusValue);
                return false;
            }
        } catch (RestClientException e) {
            return false;
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
