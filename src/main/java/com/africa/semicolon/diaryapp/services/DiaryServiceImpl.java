package com.africa.semicolon.diaryapp.services;

import com.africa.semicolon.diaryapp.datas.models.Diary;
import com.africa.semicolon.diaryapp.datas.models.Entry;
import com.africa.semicolon.diaryapp.datas.models.User;
import com.africa.semicolon.diaryapp.datas.repositories.DiaryRepository;
import com.africa.semicolon.diaryapp.datas.repositories.UserRepository;
import com.africa.semicolon.diaryapp.dtos.requests.UpdateDiaryRequest;
import com.africa.semicolon.diaryapp.exceptions.DiaryAppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryServiceImpl implements DiaryService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Override
    public Diary createDiary(String title, String id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new DiaryAppException("User Account does not exist"));
        Diary diary = new Diary(title);
        Diary savedDiary = diaryRepository.save(diary);
        user.getDiary().add(savedDiary);
        userRepository.save(user);
        return savedDiary;
    }

    @Override
    public String updateDiary(String diaryId, UpdateDiaryRequest updateDiaryRequest) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(()-> new DiaryAppException("Diary does not exist"));
        if(!(updateDiaryRequest.getTitle().trim().equals("") || updateDiaryRequest.getTitle() == null)){
            diary.setTitle((updateDiaryRequest.getTitle()));
            diaryRepository.save(diary);
        }
        return "Diary Successfully Updated";
    }

    @Override
    public Diary addEntries(List<Entry> entries, String diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(()-> new DiaryAppException("Diary does not exist"));
        diary.getEntries().addAll(entries);
        return diaryRepository.save(diary);
    }
}
