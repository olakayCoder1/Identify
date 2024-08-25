package com.identify.identify.helper;

import java.util.Base64;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataEncoder {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String encode(Object data) throws Exception {
        String json = objectMapper.writeValueAsString(data);
        return Base64.getEncoder().encodeToString(json.getBytes());
    }

    public static <T> T decode(String encodedData, Class<T> clazz) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedData);
        String json = new String(decodedBytes);
        return objectMapper.readValue(json, clazz);
    }
}