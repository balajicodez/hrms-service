package com.simplerp.hrms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Converter
public class JpaJsonConverter implements AttributeConverter<Map<String, String>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, String> map) {
        try {
            return (map == null) ? null : objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting map to JSON", e);
        }
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String json) {
        try {
            return (json == null || json.isEmpty()) ? new HashMap<>() :
                    objectMapper.readValue(json, HashMap.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading JSON string", e);
        }
    }
}

