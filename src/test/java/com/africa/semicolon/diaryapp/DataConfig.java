package com.africa.semicolon.diaryapp;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableAutoConfiguration
@Configuration
@EnableMongoRepositories("com.africa.semicolon.diaryapp.datas.repositories")
public class DataConfig {

}
