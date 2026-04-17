package com.khaled_amin.book_social_network.core.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class SnakeCaseMapSerializer extends JsonSerializer<Map<String, Object>> {

    @Override
    public void serialize(Map<String, Object> value,
                          JsonGenerator gen,
                          SerializerProvider serializers
    ) throws IOException {

        gen.writeStartObject();

        for (Map.Entry<String, Object> entry : value.entrySet()) {
            String snakeKey = toSnakeCase(entry.getKey());
            gen.writeObjectField(snakeKey, entry.getValue());
        }

        gen.writeEndObject();
    }

    private String toSnakeCase(String input) {
        if (input == null) return null;

        return input
                .replaceAll("([a-z])([A-Z]+)", "$1_$2")
                .toLowerCase();
    }
}