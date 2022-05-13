package com.africa.semicolon.diaryapp.MockTests;

import com.africa.semicolon.diaryapp.DataConfig;
import com.africa.semicolon.diaryapp.DiaryAppApplication;
import com.africa.semicolon.diaryapp.datas.models.Diary;
import com.africa.semicolon.diaryapp.datas.models.User;
import com.africa.semicolon.diaryapp.datas.repositories.DiaryRepository;
import com.africa.semicolon.diaryapp.datas.repositories.UserRepository;
import com.africa.semicolon.diaryapp.dtos.requests.CreateAccountRequest;
import com.africa.semicolon.diaryapp.dtos.requests.UpdateUserRequest;
import com.africa.semicolon.diaryapp.dtos.responses.UserDTO;
import com.africa.semicolon.diaryapp.exceptions.DiaryAppException;
import com.africa.semicolon.diaryapp.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import javax.lang.model.util.Types;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataMongoTest
@ComponentScan(basePackages = "com.africa.semicolon.diaryapp.**")
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
@ContextConfiguration(classes = {DataConfig.class})
public class UserServiceMockTest {
    @Mock
    UserRepository userRepository;
    @Mock
    DiaryRepository diaryRepository;

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();
    @Captor
    ArgumentCaptor<User> userArgumentCaptor;
    @Captor
    ArgumentCaptor<Diary> diaryArgumentCaptor;

    @Test
    void userAccountCanBeCreated(){
        CreateAccountRequest accountRequest = new CreateAccountRequest("test@email.com","password");
        User user = new User("dummy id","test@email.com","password");
        when(userRepository.findUserByEmail(anyString()))
                .thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO userDTO = userService.createAccount(accountRequest);
        verify(userRepository,times(1)).save(userArgumentCaptor.capture());
        verify(userRepository,times(1)).findUserByEmail(accountRequest.getEmail());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser.getPassword()).isEqualTo(accountRequest.getPassword());
        assertThat(capturedUser.getEmail()).isEqualTo(accountRequest.getEmail());
        assertThat(userDTO.getId()).isEqualTo("dummy id");
        assertThat(userDTO.getEmail()).isEqualTo(accountRequest.getEmail());

    }
    @Test
    void createNewUserWithExistingEmailThrowsExceptionTest(){
        CreateAccountRequest accountRequest = new CreateAccountRequest("test@email.com","password");
        when(userRepository.findUserByEmail("test@email.com")).thenReturn(Optional.of(new User()));
        assertThatThrownBy(()->userService.createAccount(accountRequest)).isInstanceOf(DiaryAppException.class).hasMessage("User already exists");
    }
    @Test
    void userCanBeSearchedTest(){
        User user = new User("dummy id","test@email.com","password");
        when(userRepository.findById("dummy id")).thenReturn(Optional.of(user));
        UserDTO userDTO = userService.findUser("dummy id");
        assertThat(userDTO).isNotNull();

    }
    @Test
    void updateUserInformationTest(){
        UpdateUserRequest updateRequest = new UpdateUserRequest("new email","newPassword");
        String id = "user id";
        User user = new User(id,"test@email.com","password");
        User updatedUser = new User(id,"new email","newPassword");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        String expected = userService.updateUser(id,updateRequest);
        verify(userRepository,times(1)).findById(id);
        verify(userRepository,times(1)).save(user);
        assertThat(expected).isEqualTo("User details updated Successfully");
    }
    @Test
    void updateUserThrowsExceptionWhenUserIdIsNotFound(){
        UpdateUserRequest updateRequest = new UpdateUserRequest("new email","newPassword");
        String id = "user id";
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(()->userService.updateUser(id,updateRequest)).isInstanceOf(DiaryAppException.class).hasMessage("User Account does not exist");
    }

    @Test
    void diaryCanBeAddedToUserAccountTest() {
        String id = "user id";
        Diary diary = new Diary("diary title");
        diary.setId("diary id");
        User user = new User(id,"test@email.com","password");
        user.setDiary(new HashSet<>());
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(diaryRepository.save(any(Diary.class))).thenReturn(diary);
        when(userRepository.save(any(User.class))).thenReturn(user);
        Diary savedDiary = userService.addNewDiary(id,diary);
        verify(diaryRepository, times(1)).save(diaryArgumentCaptor.capture());
        Diary capturedValue = diaryArgumentCaptor.getValue();
        assertThat(capturedValue.getTitle()).isEqualTo(diary.getTitle());
        verify(userRepository,times(1)).save(userArgumentCaptor.capture());
        User savedUser = userArgumentCaptor.getValue();
        assertThat(diary).isIn(savedUser.getDiary());

    }
}
