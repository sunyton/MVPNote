package com.example.sunyton.diaryio.mvp.presenter;

import com.example.sunyton.diaryio.MainActivity;
import com.example.sunyton.diaryio.http.ApiClient;
import com.example.sunyton.diaryio.http.ApiService;
import com.example.sunyton.diaryio.model.MyResponse;
import com.example.sunyton.diaryio.model.Note;
import com.example.sunyton.diaryio.mvp.view.Iview;
import com.example.sunyton.diaryio.utils.Utils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Presenter implements IPresenter{


    private ApiService apiService;
    private Iview mIview;
    private CompositeDisposable disposable;

    public Presenter(Iview iview) {
        mIview = iview;
        disposable = new CompositeDisposable();
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void createNote(String noteBody, List<Note> mNotes) {

        Note note = new Note();
        note.setNote(noteBody);
        note.setId(1);
        note.setTime(Utils.getToday());
        mNotes.add(0, note);
        mIview.refreshView(MainActivity.TYPE_ADD);

        disposable.add(
                apiService.createNote(note)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<MyResponse>() {
                            @Override
                            public void onSuccess(MyResponse myResponse) {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        })

        );

    }



    public void fetAllNote(final List<Note> mNotes) {

        disposable.add(apiService.fetchAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Note>>() {
                    @Override
                    public void onSuccess(List<Note> notes) {
                        mNotes.clear();
                        mNotes.addAll(notes);
                        mIview.refreshView(MainActivity.TYPE_ALL);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));

    }

    public void finishDispose() {
        mIview = null;
        disposable.dispose();

    }

    @Override
    public void updateNote() {

    }


}
