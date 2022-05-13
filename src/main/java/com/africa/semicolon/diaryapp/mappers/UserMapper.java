package com.africa.semicolon.diaryapp.mappers;

import com.africa.semicolon.diaryapp.datas.models.User;
import com.africa.semicolon.diaryapp.dtos.responses.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
}
