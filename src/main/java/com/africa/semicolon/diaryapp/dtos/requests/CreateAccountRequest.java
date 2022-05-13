package com.africa.semicolon.diaryapp.dtos.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {
    private String email;
    private String password;
}
