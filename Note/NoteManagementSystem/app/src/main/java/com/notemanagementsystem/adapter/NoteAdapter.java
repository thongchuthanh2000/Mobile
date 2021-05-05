package com.notemanagementsystem.adapter;

import android.app.Activity;
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
import com.notemanagementsystem.entity.Category;
import com.notemanagementsystem.entity.Note;
import com.notemanagementsystem.entity.Priority;
import com.notemanagementsystem.entity.Status;


import java.text.SimpleDateFormat;
import java.util.List;

public class NoteAdapter extends  RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> mListNote;
    private IClickItemNote iClickItemNote;

    public void setData(List<Note> list){
        this.mListNote = list;
        notifyDataSetChanged();
    }

    public interface IClickItemNote{
        void updateNote(Note note);
        void deleteNote(Note note);
    }

    public NoteAdapter(IClickItemNote iClickItemNote) {
        this.iClickItemNote = iClickItemNote;
    }

    //Create viewHolder
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemNote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemNote = itemView.findViewById(R.id.tv_item_note);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    PopupMenu popupMenu = new PopupMenu(v.getContext(), tvItemNote);
                    popupMenu.getMenuInflater().inflate(R.menu.option_popup_menu, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.it_edit:
                                    iClickItemNote.updateNote((Note) v.getTag());
                                    return true;
                                case R.id.it_delete:
                                    iClickItemNote.deleteNote((Note) v.getTag());
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
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        //Get Note to mListNote
        final Note note = mListNote.get(position);

        if(note == null){
            return;
        }
        else {
            holder.itemView.setTag((Note) note);
        }

        //Set value text
        StringBuffer sb = new StringBuffer();
        sb.append("Name: " + note.getName().toString() + '\n');

        Category category = AppDatabase.getAppDatabase(holder.itemView.getContext()).categoryDAO().getCategoryById(note.getCategoryId());
        sb.append("Category: " + category.getName() + '\n');

        Priority priority = AppDatabase.getAppDatabase(holder.itemView.getContext()).priorityDAO().getPriorityById(note.getPriorityId());
        sb.append("Priority: " + priority.getName() + '\n');

        Status status = AppDatabase.getAppDatabase(holder.itemView.getContext()).statusDAO().getStatusById(note.getStatusId());
        sb.append("Status: " + status.getName() + '\n');

        String planDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(note.getPlanDate());
        sb.append("Plan Date: " + planDate + '\n');

        String createDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(note.getCreateDate());
        sb.append("Created Date: " + createDate);

        holder.tvItemNote.setText(sb);
    }

    @Override
    public int getItemCount() {
        return mListNote.size();
    }
}