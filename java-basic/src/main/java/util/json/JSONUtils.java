package util.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class JSONUtils {
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.registerModules(new CustomModule());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static String json(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T deserialize(String content, Class<?> c) {
        try {
            return mapper.readValue(content, constructType(c));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T deserialize(String content, JavaType type) {
        try {
            return mapper.readValue(content, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T deserialize(String content, TypeReference<T> jsonTypeReference) {
        try {
            return mapper.readValue(content, jsonTypeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JavaType constructType(Class<?> c) {
        return mapper.getTypeFactory().constructType(c);
    }

    public static JavaType constructParametricType(Class<?> c, Class<?>... classes) {
        return mapper.getTypeFactory().constructParametricType(c, classes);
    }
}
