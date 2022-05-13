package com.africa.semicolon.diaryapp.datas.repositories;

import com.africa.semicolon.diaryapp.datas.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String>  {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(String email);


}
