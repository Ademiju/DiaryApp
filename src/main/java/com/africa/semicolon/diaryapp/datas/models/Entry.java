package com.africa.semicolon.diaryapp.datas.models;

import com.africa.semicolon.diaryapp.config.EntryDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = EntryDeserializer.class)
public class Entry {
    @Id
    private String id;
    private String text;
    private LocalDateTime entryTime;
//    private String imageUrl;

    public Entry( String text) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.entryTime = LocalDateTime.now();

    }

}
