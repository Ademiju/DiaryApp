package com.africa.semicolon.diaryapp.services;

import com.africa.semicolon.diaryapp.datas.models.Diary;
import com.africa.semicolon.diaryapp.dtos.requests.UpdateUserRequest;
import com.africa.semicolon.diaryapp.dtos.responses.UserDTO;
import com.africa.semicolon.diaryapp.dtos.requests.CreateAccountRequest;

public interface UserService {
    UserDTO createAccount(CreateAccountRequest createAccountRequest);

    UserDTO findUser(String id);

    String updateUser(String id, UpdateUserRequest request);

    Diary addNewDiary(String id, Diary diary);

    void deleteByEmail(String email);
}
