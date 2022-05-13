package com.africa.semicolon.diaryapp.services;

import com.africa.semicolon.diaryapp.datas.models.Diary;
import com.africa.semicolon.diaryapp.datas.models.User;
import com.africa.semicolon.diaryapp.datas.repositories.DiaryRepository;
import com.africa.semicolon.diaryapp.datas.repositories.UserRepository;
import com.africa.semicolon.diaryapp.dtos.requests.UpdateUserRequest;
import com.africa.semicolon.diaryapp.dtos.responses.UserDTO;
import com.africa.semicolon.diaryapp.dtos.requests.CreateAccountRequest;
import com.africa.semicolon.diaryapp.exceptions.DiaryAppException;
import com.africa.semicolon.diaryapp.mappers.UserMapper;
import com.africa.semicolon.diaryapp.mappers.UserMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DiaryRepository diaryRepository;

    private UserMapper userMapper = new UserMapperImpl();

    @Override
    public UserDTO createAccount(CreateAccountRequest createAccountRequest) {
        Optional<User> optionalUser = userRepository.findUserByEmail(createAccountRequest.getEmail());
        if(optionalUser.isPresent())
            throw new DiaryAppException("User already exists");
        User user = new User();
        user.setEmail(createAccountRequest.getEmail());
        user.setPassword(createAccountRequest.getPassword());
        user.setDiary(new HashSet<>());
        User savedUser = userRepository.save(user);

        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public UserDTO findUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow( ()-> new DiaryAppException("User does not exist"));
        return userMapper.userToUserDTO(user);
    }

    @Override
    public String updateUser(String id, UpdateUserRequest updateRequest) {
        User user = userRepository.findById(id).orElseThrow(()-> new DiaryAppException("User Account does not exist"));
        boolean isUpdated = false;
        if(!(updateRequest.getEmail()==null || updateRequest.getEmail().trim().equals(""))){
            user.setEmail(updateRequest.getEmail());
            isUpdated = true;
        }
        if(!(updateRequest.getPassword()==null || updateRequest.getPassword().trim().equals(""))){
            user.setPassword(updateRequest.getPassword());
            isUpdated = true;
        }
        if(isUpdated) {
            userRepository.save(user);
        }
        return "User details updated Successfully";
    }

    @Override
    public Diary addNewDiary(String id, Diary diary) {
        User user = userRepository.findById(id).orElseThrow(()-> new DiaryAppException("User does not exists"));
        Diary savedDiary = diaryRepository.save(diary);
        user.getDiary().add(diary);
        userRepository.save(user);
        return savedDiary;
    }

    @Override
    public void deleteByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(()-> new DiaryAppException("User not found"));
        userRepository.delete(user);

    }
}
