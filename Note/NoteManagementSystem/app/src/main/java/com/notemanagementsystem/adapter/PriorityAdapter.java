package com.notemanagementsystem.adapter;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notemanagementsystem.R;
import com.notemanagementsystem.entity.Note;
import com.notemanagementsystem.entity.Priority;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;
/*
 *PriorityAdapter Of RecyclerView Priority
 *@author  Chu Thanh
 * @version 1.0
 * @since   2021-04-24
 */
public class PriorityAdapter extends  RecyclerView.Adapter<PriorityAdapter.PriorityViewHolder> {

    private PriorityAdapter.IClickItemPriority iClickItemNote;
    /*
    Update - Delete Priority
    @Param: Priority
    @Return Nothing
    */
    public interface IClickItemPriority{
        void update(Priority priority);
        void delete(Priority priority);
    }

    public PriorityAdapter(PriorityAdapter.IClickItemPriority iClickItemNote) {
        this.iClickItemNote = iClickItemNote;
    }

    private List<Priority> mListPriority;
    /*
     *  @param List<Priority>
     * assigns data to the list mListPriority
     * @return Nothing.
     */
    public void setData(List<Priority> mListPriority){
        this.mListPriority = mListPriority;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_priority_status, parent, false);
        return new PriorityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriorityViewHolder holder, int position) {
        //Get category to mListCategory
        Priority priority = mListPriority.get(position);
        //If none exist => Return
        if(priority == null){
            return;
        }

        //Set value text
        holder.priorityName.setText(priority.getName());
        //Format string yyyy-MM-dd
        String formatedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(priority.getCreateDate());
        holder.createdDate.setText(formatedDate);

        //Open context menu -> Delete | Update item priority
        holder.priorityName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.priorityName);
                popupMenu.getMenuInflater().inflate(R.menu.option_popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.it_edit:
                                iClickItemNote.update(priority);
                                return true;
                            case R.id.it_delete:
                                iClickItemNote.delete(priority);
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
        if(mListPriority != null){
            return mListPriority.size();
        }
        return 0;
    }

    //Create viewHolder
    public class PriorityViewHolder extends RecyclerView.ViewHolder {

        private TextView priorityName;
        private TextView createdDate;

        public PriorityViewHolder(@NonNull View itemView) {
            super(itemView);

            priorityName = itemView.findViewById(R.id.txt_name_category_priority_status);
            createdDate = itemView.findViewById(R.id.txt_created_category_priority_status);
        }
    }
}
