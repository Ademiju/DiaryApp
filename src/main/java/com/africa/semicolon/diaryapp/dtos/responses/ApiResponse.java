package com.africa.semicolon.diaryapp.dtos.responses;

import com.africa.semicolon.diaryapp.datas.models.Diary;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {

    private Object payLoad;
    private String message;
    private boolean isSuccessful;

}
