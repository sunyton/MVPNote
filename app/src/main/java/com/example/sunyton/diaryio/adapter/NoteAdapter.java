package com.example.sunyton.diaryio.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sunyton.diaryio.R;
import com.example.sunyton.diaryio.model.Note;
import com.example.sunyton.diaryio.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private Context context;
    private List<Note> mNoteList;



    public NoteAdapter(Context context, List<Note> noteList) {
        this.context = context;
        mNoteList = noteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(context).inflate(R.layout.item_note, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.note.setText(mNoteList.get(i).getNote());
        viewHolder.time.setText(Utils.formatDate(mNoteList.get(i).getTime()));


        viewHolder.dot.setText(Html.fromHtml("&#8226;"));
        viewHolder.dot.setTextColor(getRandomColor("400"));

    }

    private int getRandomColor(String colorCount) {

        int defaultColor = Color.GRAY;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + colorCount, "array", context.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            defaultColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }

        return defaultColor;
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dot)
        TextView dot;
        @BindView(R.id.note)
        TextView note;
        @BindView(R.id.timestamp)
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
