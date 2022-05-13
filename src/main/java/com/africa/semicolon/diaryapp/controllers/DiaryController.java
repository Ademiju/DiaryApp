package com.africa.semicolon.diaryapp.controllers;

import com.africa.semicolon.diaryapp.datas.models.Diary;
import com.africa.semicolon.diaryapp.datas.models.Entry;
import com.africa.semicolon.diaryapp.dtos.responses.ApiResponse;
import com.africa.semicolon.diaryapp.exceptions.DiaryAppException;
import com.africa.semicolon.diaryapp.services.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/diaries")
public class DiaryController {
    @Autowired
    DiaryService diaryService;

    @PostMapping("/create/{userId}")
    ResponseEntity<?> createNewDiary(@RequestParam String diaryTitle, @PathVariable("userId") String userId){
        try{
            Diary diary = diaryService.createDiary(diaryTitle,userId);
            ApiResponse apiResponse = new ApiResponse(diary,"Successfully created",true);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

        }catch(DiaryAppException error){
            ApiResponse response = ApiResponse.builder()
                    .message("User already exist")
                    .isSuccessful(false)
                    .build();
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/addEntries/{diaryId}")
    public ResponseEntity<?> addNewEntries(@PathVariable("diaryId") String diaryId, @RequestBody List<Entry> entryList){
        try{
            Diary diary = diaryService.addEntries(entryList, diaryId);
            ApiResponse apiResponse = new ApiResponse(diary, "diary successfully", true);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (DiaryAppException exception){
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(exception.getMessage())
                    .isSuccessful(false)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
