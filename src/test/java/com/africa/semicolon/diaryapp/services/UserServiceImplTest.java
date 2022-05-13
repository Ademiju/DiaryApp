package com.africa.semicolon.diaryapp.services;

import com.africa.semicolon.diaryapp.datas.models.Diary;
import com.africa.semicolon.diaryapp.datas.models.User;
import com.africa.semicolon.diaryapp.dtos.requests.UpdateUserRequest;
import com.africa.semicolon.diaryapp.dtos.responses.UserDTO;
import com.africa.semicolon.diaryapp.dtos.requests.CreateAccountRequest;
import com.africa.semicolon.diaryapp.exceptions.DiaryAppException;
import com.africa.semicolon.diaryapp.mappers.UserMapper;
import com.africa.semicolon.diaryapp.mappers.UserMapperImpl;
import lombok.NonNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class UserServiceImplTest {

    @Autowired
    private  UserService userService;
    private CreateAccountRequest accountRequest;
    UserMapper userMapper = new UserMapperImpl();

    @BeforeEach
    void setUp(){
        accountRequest = CreateAccountRequest.builder()
                .email("test@email.com")
                .password("test").build();
    }
    @Test
    void userCreateAccountTest(){

        UserDTO userDTO = userService.createAccount(accountRequest);
        assertThat(userDTO.getId()).isNotNull();
        assertThat(userDTO.getEmail()).isEqualTo("test@email.com");
    }

    @Test
    void createNewUserWithExistingEmailThrowsExceptionTest(){
        userService.createAccount(accountRequest);
        CreateAccountRequest accountRequest = CreateAccountRequest.builder().email("test@email.com").password("best").build();
        assertThatThrownBy(()->userService.createAccount(accountRequest)).isInstanceOf(DiaryAppException.class).hasMessage("User already exists");
    }

    @Test
    void userCanBeSearchedTest(){
        UserDTO userDTO = userService.createAccount(accountRequest);
        UserDTO userFromDatabase = userService.findUser(userDTO.getId());
        assertThat(userDTO.getId()).isEqualTo(userFromDatabase.getId());

    }

    @Test
    void updateUserInformationTest(){
        UserDTO userDTO = userService.createAccount(accountRequest);
        UpdateUserRequest updateRequest = new UpdateUserRequest("","testing");
        String result = userService.updateUser(userDTO.getId(),updateRequest);
        assertThat(result).isEqualTo("User details updated Successfully");
        UserDTO userFromDatabase = userService.findUser(userDTO.getId());
        assertThat(userFromDatabase.getEmail()).isEqualTo("test@email.com");
    }

    @Test
    void updateUserThrowsExceptionWhenUserIdIsNotFound(){
        userService.createAccount(accountRequest);
        String id = "null id";
        UpdateUserRequest updateRequest = UpdateUserRequest.builder()
                .password("new password")
                .email("test@gmail.com")
                .build();
        assertThatThrownBy(()-> userService.updateUser(id,updateRequest))
                .isInstanceOf(DiaryAppException.class).hasMessage("User Account does not exist");

    }

    @Test
    void diaryCanBeAddedToUserAccountTest(){
        UserDTO userDTO = userService.createAccount(accountRequest);
//        UserDTO userDTO = userService.findUser(id);
        String diaryTitle = "New Diary";
        Diary diary = new Diary(diaryTitle);
        Diary savedDiary = userService.addNewDiary(userDTO.getId(), diary);
        assertThat(savedDiary.getId()).isNotNull();
        assertThat(savedDiary.getTitle()).isEqualTo("New Diary");

    }
    @AfterEach
    void tearDown(){
        userService.deleteByEmail("test@email.com");
    }
}