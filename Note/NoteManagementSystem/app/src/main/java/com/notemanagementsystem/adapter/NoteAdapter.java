package com.notemanagementsystem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notemanagementsystem.R;
import com.notemanagementsystem.entity.Note;


import java.util.List;

public class NoteAdapter extends  RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> mListNote;
    public void setData(List<Note> list){
        this.mListNote = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = mListNote.get(position);
        if(note == null){
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("Name: " + note.getName().toString() + '\n');

        holder.tv_item_note.setText(sb);
    }

    @Override
    public int getItemCount() {
        if(mListNote != null){
            return mListNote.size();
        }
        return 0;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_item_note;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_note = itemView.findViewById(R.id.tv_item_note);

        }
    }
}
