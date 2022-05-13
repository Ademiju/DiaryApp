package com.africa.semicolon.diaryapp.services;

import com.africa.semicolon.diaryapp.datas.models.Diary;
import com.africa.semicolon.diaryapp.datas.models.Entry;
import com.africa.semicolon.diaryapp.dtos.requests.UpdateDiaryRequest;

import java.util.List;

public interface DiaryService {
    Diary createDiary(String title, String id);
    String updateDiary(String diaryId, UpdateDiaryRequest updateDiaryRequest);
    Diary addEntries(List<Entry> entry, String diaryId);
}
