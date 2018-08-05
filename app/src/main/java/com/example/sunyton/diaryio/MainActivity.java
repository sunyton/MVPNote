package com.example.sunyton.diaryio;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.example.sunyton.diaryio.adapter.MyDividerItemDecoation;
import com.example.sunyton.diaryio.adapter.NoteAdapter;
import com.example.sunyton.diaryio.adapter.RecyclerTouchListener;
import com.example.sunyton.diaryio.http.ApiClient;
import com.example.sunyton.diaryio.http.ApiService;
import com.example.sunyton.diaryio.model.MyResponse;
import com.example.sunyton.diaryio.model.Note;
import com.example.sunyton.diaryio.mvp.presenter.IPresenter;
import com.example.sunyton.diaryio.mvp.presenter.Presenter;
import com.example.sunyton.diaryio.mvp.view.Iview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements Iview {

    private NoteAdapter mNoteAdapter;
    private List<Note> mNotes = new ArrayList<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiService apiService;
    private IPresenter mIPresenter;
    public static final int TYPE_ADD = 0;
    public static final int TYPE_DEL = 1;
    public static final int TYPE_ALL = 2;
    public static final int TYPE_UPDATE = 3;





    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_empty_notes_view)
    TextView emptTextView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_main);
//      初始化View
        initView();
//      初始化presenter
        mIPresenter = new Presenter(this);

    }

    private void initView() {
        ButterKnife.bind(this);

        mToolbar.setTitle(R.string.activity_title_home);
        setSupportActionBar(mToolbar);
//      设置recyclerview
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new MyDividerItemDecoation(this,MyDividerItemDecoation.VERTICAL_LIST, 16));
        mNoteAdapter = new NoteAdapter(this, mNotes);
        mRecyclerView.setAdapter(mNoteAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick() {

            }

            @Override
            public void onLongClick(int position) {
                chooseDialog();
            }
        }));


//      设置fab
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(false,null);
            }
        });

//        mIPresenter.fetAllNote(mNotes);



    }

    public void showDialog(boolean isUpdate, final Note note) {

        View dialogView = LayoutInflater.from(this).inflate(R.layout.note_dialog, null);
        final EditText editText = dialogView.findViewById(R.id.note);
        TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
        dialogTitle.setText(isUpdate ? "Edit Note" : "Add Note");
        editText.setText(isUpdate ? note.getNote() : null);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView).setNegativeButton("cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if (isUpdate) {
            dialogTitle.setText("Edit Note");
            editText.setText(note.getNote());
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    note.setNote(editText.getText().toString());
//                   上传note
                    mIPresenter.updateNote();
                }
            });
        } else {
            dialogTitle.setText("Add Note");
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                  添加操作
//                    Note note1 = new Note();
//                    note1.setNote(editText.getText().toString());
//                    note1.setId(1);
//                    note1.setTime("2018-02-21 00:15:42");
//                    createNote(note1);
                    mIPresenter.createNote(editText.getText().toString(), mNotes);


                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void chooseDialog() {
        CharSequence[] options = new CharSequence[]{"Edit","Delete"};
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("Choose Option").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                添加选择操作
                switch (which) {
                    case 0:
                        break;
                    case 1:
                        break;
                }
            }
        }).create();
        alertDialog.show();
    }


//    private void createNote(Note note) {
//        Log.d("TAG", note.getNote());
//        mNotes.add(0, note);
//        toggleEmptyNotes();
//        mNoteAdapter.notifyItemInserted(0);
//
//        disposable.add(
//                apiService.createNote(note)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<MyResponse>() {
//                    @Override
//                    public void onSuccess(MyResponse myResponse) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                })
//
//        );
//
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIPresenter.finishDispose();
    }

    private void toggleEmptyNotes() {
        if (mNotes != null) {
            emptTextView.setVisibility(View.GONE);
        } else {
            emptTextView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void refreshView(int type) {
        switch (type) {
            case TYPE_ADD:
                mNoteAdapter.notifyItemInserted(0);
                toggleEmptyNotes();
                break;
            case TYPE_ALL:
                mNoteAdapter.notifyDataSetChanged();
                toggleEmptyNotes();
                break;
        }
    }


}
