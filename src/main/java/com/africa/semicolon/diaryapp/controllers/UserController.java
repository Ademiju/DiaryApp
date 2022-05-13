package com.africa.semicolon.diaryapp.controllers;

import com.africa.semicolon.diaryapp.dtos.requests.CreateAccountRequest;
import com.africa.semicolon.diaryapp.dtos.responses.ApiResponse;
import com.africa.semicolon.diaryapp.exceptions.DiaryAppException;
import com.africa.semicolon.diaryapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createNewUserAccount(@RequestBody CreateAccountRequest accountRequest){
        try {
            ApiResponse response = ApiResponse.builder().message("Account Successfully Created id: "+userService.createAccount(accountRequest)).isSuccessful(true).build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }
        catch (DiaryAppException err){
//            return new ResponseEntity<>(err.getMessage(),HttpStatus.BAD_REQUEST);
            ApiResponse response = ApiResponse.builder()
                    .message("User already exist")
                    .isSuccessful(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }
}

