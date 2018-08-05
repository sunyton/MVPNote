package com.example.sunyton.diaryio.http;

import com.example.sunyton.diaryio.model.MyResponse;
import com.example.sunyton.diaryio.model.Note;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {


    @POST("note")
    Single<MyResponse> createNote(@Body Note note);

    @GET("note")
    Single<List<Note>> fetchAll();

}
