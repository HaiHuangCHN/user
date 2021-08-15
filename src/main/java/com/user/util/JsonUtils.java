package com.user.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

/**
 * JSON实用类（选型Jackson）
 *
 * @author haihuang95@zto.com
 * @date 2021-07-22 10:58:56
 * @since 1.42.0
 */
public class JsonUtils {

    /**
     * 默认objectMapper实例
     */
    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

    private static ObjectMapper objectMapperCamel;

    private JsonUtils() {}

    private static void initObjectMapperCamel() {
        objectMapperCamel = new ObjectMapper();
        objectMapperCamel.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapperCamel.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Object转（驼峰）JSON
     *
     * @param <T>    the type parameter
     * @param object the object
     * @return string string
     * @return: return a string, if the object is null return ""
     */
    public static <T> String objectToJsonCamel(T object) {
        if (null == object)
            return null;
        if (null == objectMapperCamel) {
            initObjectMapperCamel();
        }
        try {
            return objectMapperCamel.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        if (null == objectMapperCamel) {
            initObjectMapperCamel();
        }
        try {
            return (T) objectMapperCamel.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
