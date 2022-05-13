package com.africa.semicolon.diaryapp.datas.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class Diary {
    @Id
    private String id;
    @NonNull
    private String title;
    private LocalDateTime creationTime;
    private Set<Entry> entries;

    public Diary( String title, LocalDateTime creationTime) {
        this.title = title;
        this.creationTime = LocalDateTime.now();
    }

    public Diary(String id, @NonNull String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }
}
