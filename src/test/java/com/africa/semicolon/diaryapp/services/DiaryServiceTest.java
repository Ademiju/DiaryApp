package com.africa.semicolon.diaryapp.services;

import com.africa.semicolon.diaryapp.datas.models.Diary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
public class DiaryServiceTest {
    @Autowired
    private DiaryService diaryService;
    @Test
    void aNewDiaryCanBeCreatedTest(){
        String title = "New Diary";
        String id = "626bde7270a63e1119ae92db";
        Diary diary = diaryService.createDiary(title,id);
        assertThat(diary.getTitle()).isEqualTo("New Diary");


    }


}
