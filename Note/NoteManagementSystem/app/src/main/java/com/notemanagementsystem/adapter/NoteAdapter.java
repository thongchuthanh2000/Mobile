package com.notemanagementsystem.adapter;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notemanagementsystem.AppDatabase;
import com.notemanagementsystem.R;
import com.notemanagementsystem.entity.Note;


import java.text.SimpleDateFormat;
import java.util.List;

public class NoteAdapter extends  RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> mListNote;
    public void setData(List<Note> list){
        this.mListNote = list;
        notifyDataSetChanged();
    }

    private IClickItemNote iClickItemNote;
    public interface IClickItemNote{
        void updateNote(Note note);
        void deleteNote(Note note);
    }

    public NoteAdapter(IClickItemNote iClickItemNote) {
        this.iClickItemNote = iClickItemNote;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        final Note note = mListNote.get(position);
        if(note == null){
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("Name: " + note.getName().toString() + '\n');
        sb.append("Category: " + note.getCategory() + '\n');
        String planDate = new SimpleDateFormat("yyyy-MM-dd").format(note.getPlanDate());
        sb.append("Plan Date: " + planDate + '\n');
        String createDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(note.getCreateDate());
        sb.append("Created Date: " + createDate);


        holder.tv_item_note.setText(sb);

        holder.tv_item_note.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.tv_item_note);
                popupMenu.getMenuInflater().inflate(R.menu.option_popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.it_edit:
                                iClickItemNote.updateNote(note);
                                return true;
                            case R.id.it_delete:
                                iClickItemNote.deleteNote(note);
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
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
