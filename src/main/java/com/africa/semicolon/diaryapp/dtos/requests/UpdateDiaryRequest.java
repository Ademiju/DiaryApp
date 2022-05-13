package com.africa.semicolon.diaryapp.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdateDiaryRequest {
    private String title;

    public UpdateDiaryRequest(String title) {
        this.title = title;
    }
}
