package com.example.sunyton.diaryio.test;

import com.example.sunyton.diaryio.http.ApiClient;
import com.example.sunyton.diaryio.http.ApiService;
import com.example.sunyton.diaryio.model.Note;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class TestClient {


    public static void main(String[] args) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Note note = new Note();
        note.setId(3);
        note.setTime("3333");
        note.setNote("39fjdsfjksfj");



    }


}
