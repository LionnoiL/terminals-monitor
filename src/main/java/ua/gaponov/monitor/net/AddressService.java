package ua.gaponov.monitor.net;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private static final int THREAD_POOL_SIZE = 10;

    public List<String> getAddressRange(String networkNumber) {
        List<String> result = new ArrayList<>();

        for (int i = 2; i < 254; i++) {
            String address = networkNumber + i;
            result.add(address);
        }

        return result;
    }
}
