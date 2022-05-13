package com.africa.semicolon.diaryapp.datas.repositories;

import com.africa.semicolon.diaryapp.datas.models.Diary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiaryRepository extends MongoRepository<Diary, String> {
}
