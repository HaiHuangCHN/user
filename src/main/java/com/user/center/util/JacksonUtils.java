package com.user.center.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;
import java.util.Objects;

/**
 * JSON实用类（选型Jackson）
 */
public class JacksonUtils {


    /**
     * 默认objectMapper实例
     */
    public static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

    private static final ObjectMapper OBJECT_MAPPER_CAMEL;

    private JacksonUtils() {}

    static {
        OBJECT_MAPPER_CAMEL = new ObjectMapper();
        OBJECT_MAPPER_CAMEL.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        OBJECT_MAPPER_CAMEL.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER_CAMEL.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Object转（驼峰）JSON
     *
     * @param <T>    the type parameter
     * @param object the object
     * @return string string
     * @return: return a string, if the object is null return null
     */
    public static <T> String objectToJsonCamel(T object) {
        if (null == object)
            return null;

        try {
            return OBJECT_MAPPER_CAMEL.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER_CAMEL.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T jsonToObjectWithCustomObjectMapper(String json, Class<T> clazz, ObjectMapper ob) {
        if (json == null) {
            return null;
        }

        Objects.requireNonNull(ob);

        try {
            return ob.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
