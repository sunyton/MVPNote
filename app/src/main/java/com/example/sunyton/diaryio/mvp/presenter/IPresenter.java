package com.example.sunyton.diaryio.mvp.presenter;

import com.example.sunyton.diaryio.model.Note;

import java.util.List;

public interface IPresenter {

    void createNote(String noteBody, List<Note> mNotes);

    void fetAllNote(List<Note> mNotes);

    void finishDispose();


    void updateNote();
}
