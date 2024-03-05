package ua.gaponov.monitor.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonConverter {

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static <T> List<T> convertJsonStringToList(String response,
                                                      Class<T> typeListElementClass) {
        Type type = TypeToken.getParameterized(List.class, typeListElementClass).getType();
        return GSON.fromJson(response, type);
    }
}
