package com.africa.semicolon.diaryapp.MockTests;

import com.africa.semicolon.diaryapp.datas.models.Diary;
import com.africa.semicolon.diaryapp.datas.models.Entry;
import com.africa.semicolon.diaryapp.datas.models.User;
import com.africa.semicolon.diaryapp.datas.repositories.DiaryRepository;
import com.africa.semicolon.diaryapp.datas.repositories.UserRepository;
import com.africa.semicolon.diaryapp.dtos.requests.UpdateDiaryRequest;
import com.africa.semicolon.diaryapp.services.DiaryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DiaryServiceMockTest {
    @Mock
    private DiaryRepository diaryRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DiaryServiceImpl diaryService = new DiaryServiceImpl();
    @Captor
    private ArgumentCaptor <User> userArgumentCaptor;
    private ArgumentCaptor <Diary> diaryArgumentCaptor;

    @Test
    void diaryCanBeCreatedTest(){
        User user = new User("email.com","test");
        Diary diary = new Diary("New diary");
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        user.getDiary().add(diary);
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(diaryRepository.save(any(Diary.class))).thenReturn(diary);
        Diary returnedDiary = diaryService.createDiary(diary.getTitle(),"user id");
        verify(userRepository,times(1)).findById("user id");
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(returnedDiary.getEntries()).isEmpty();
        assertThat(user.getDiary()).hasSize(1);
        assertThat(new ArrayList<>(capturedUser.getDiary()).get(0).getTitle()).isEqualTo(diary.getTitle());
    }

    @Test
    void diaryCanBeUpdatedTest(){
        Diary diary = new Diary("dummy id","New diary");
        UpdateDiaryRequest updateDiaryRequest = new UpdateDiaryRequest("new diary");
        when(diaryRepository.findById(anyString())).thenReturn(Optional.of(diary));
        when(diaryRepository.save(any(Diary.class))).thenReturn(diary);
        String message = diaryService.updateDiary("dummy id",updateDiaryRequest);
        verify(diaryRepository,times(1)).save(diaryArgumentCaptor.capture());
        Diary capturedDiary = diaryArgumentCaptor.getValue();
        assertThat(capturedDiary.getTitle()).isEqualTo(updateDiaryRequest.getTitle());
    }
    @Test
    void multipleEntriesCanBeAddedToDiaryTest(){
        List<Entry> entries = List.of(
                new Entry("entry one"),
                new Entry("entry two")
        );
        Diary diary = new Diary("diaryId","new gist");
        when(diaryRepository.findById(anyString())).thenReturn(Optional.of(diary));
        when(diaryRepository.save(any(Diary.class))).thenReturn(diary);
        diaryService.addEntries(entries,diary.getId());
        verify(diaryRepository,times(1)).findById("diaryId");
        verify(diaryRepository, times(1)).save(diaryArgumentCaptor.capture());
        Diary capturedDiary = diaryArgumentCaptor.getValue();
        assertThat(capturedDiary.getId()).isEqualTo(diary.getId());
        assertThat(capturedDiary.getEntries()).hasSize(2);
        assertThat(capturedDiary.getEntries()).containsAll(entries);

    }

}
