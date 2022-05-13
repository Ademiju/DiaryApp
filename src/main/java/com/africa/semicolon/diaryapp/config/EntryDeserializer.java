package com.africa.semicolon.diaryapp.config;

import com.africa.semicolon.diaryapp.datas.models.Entry;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

/*
 * @author Ehis Edemakhiota
 *
 * */
public class EntryDeserializer extends StdDeserializer<Entry> {

    public EntryDeserializer(){
        this(null);
    }
    protected EntryDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Entry deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String text = node.get("text").asText();
        String id = UUID.randomUUID().toString();
        LocalDateTime createdTime = LocalDateTime.now();
        return new Entry(id, text, createdTime);
    }
}


