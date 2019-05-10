package com.example.newsapiapp.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import java.lang.reflect.Type;

public class DateTimeAdapter implements JsonDeserializer<Long> {

    private final DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'");

    @Override
    public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return LocalDateTime.parse(json.getAsString(), dateTime).atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
        } catch (Exception ignore) {
            return 0L;
        }
    }
}