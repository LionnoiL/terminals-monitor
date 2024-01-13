package ua.gaponov.monitor.net;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class AddressService {

    private static final int THREAD_POOL_SIZE = 10;

    public List<String> getAddressRange(String networkNumber) {
        List<String> result = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Future<String>> futures = new ArrayList<>();

        for (int i = 2; i < 254; i++) {
            String address = networkNumber + i;
            Callable<String> pingTask = () -> {
                if (NetUtils.pingAddress(address)) {
                    return address;
                } else {
                    return null;
                }
            };
            futures.add(executorService.submit(pingTask));
        }
        for (Future<String> future : futures) {
            try {
                String address = future.get();
                if (address != null) {
                    result.add(address);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        return result;
    }
}
