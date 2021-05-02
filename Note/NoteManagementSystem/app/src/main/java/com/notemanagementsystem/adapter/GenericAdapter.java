package com.notemanagementsystem.adapter;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notemanagementsystem.R;
import com.notemanagementsystem.entity.Priority;

import java.text.SimpleDateFormat;
import java.util.List;

/*
 *PriorityAdapter Of RecyclerView Priority
 *@author  Chu Thanh
 * @version 1.0
 * @since   2021-04-24
 *
 * Cua ca team a
 * Cua ca team a
 */
public abstract class GenericAdapter<T> extends RecyclerView.Adapter<GenericAdapter.GenericViewHolder> {

    private GenericAdapter.IClickItem iClickItem;

    public interface IClickItem{
        <T> void update(T item);
        <T> void delete(T item);
    }

    public GenericAdapter(GenericAdapter.IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    private List<T> mList;
    public void setData(List<T> mList){
        this.mList =mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_priority_status, parent, false);
        return new GenericAdapter.GenericViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull GenericAdapter.GenericViewHolder holder, int position) {
        //Get category to mListCategory
        T item = mList.get(position);
        //If none exist => Return
        if(item == null){
            return;
        }
//
//        //Set value text
//        holder.name.setText(item.getName());
//        //Format string yyyy-MM-dd
//        String formatedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(priority.getCreateDate());
//        holder.createdDate.setText(formatedDate);
//
//        //Open context menu -> Delete | Update item priority
//        holder.priorityName.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.priorityName);
//                popupMenu.getMenuInflater().inflate(R.menu.option_popup_menu, popupMenu.getMenu());
//
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.it_edit:
//                                iClickItemNote.update(priority);
//                                return true;
//                            case R.id.it_delete:
//                                iClickItemNote.delete(priority);
//                                return true;
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show();
//                return false;
//            }
//        });

    }
    public class GenericViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView createdDate;

        public GenericViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txt_name_category_priority_status);
            createdDate = itemView.findViewById(R.id.txt_created_category_priority_status);
        }
    }

}
