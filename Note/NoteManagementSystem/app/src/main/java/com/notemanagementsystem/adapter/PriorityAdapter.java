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

public class PriorityAdapter extends  RecyclerView.Adapter<PriorityAdapter.PriorityViewHolder> {


    private PriorityAdapter.IClickItemPriority iClickItemNote;



    public interface IClickItemPriority{
        void update(Priority priority);
        void delete(Priority priority);
    }

    public PriorityAdapter(PriorityAdapter.IClickItemPriority iClickItemNote) {
        this.iClickItemNote = iClickItemNote;
    }

    private List<Priority> mListPriority;
    public void setData(List<Priority> mListPriority){
        this.mListPriority = mListPriority;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_priority_status, parent, false);
        return new PriorityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriorityViewHolder holder, int position) {
        Priority priority = mListPriority.get(position);
        if(priority == null){
            return;
        }

        holder.nameCategoryPriorityStatus.setText(priority.getName());
        String formatedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(priority.getCreateDate());
        holder.createdDateCategoryPriorityStatus.setText(formatedDate);


        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.layout);
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



    public class PriorityViewHolder extends RecyclerView.ViewHolder {

        private TextView nameCategoryPriorityStatus;
        private TextView createdDateCategoryPriorityStatus;
        private LinearLayout layout;
        public PriorityViewHolder(@NonNull View itemView) {
            super(itemView);

            nameCategoryPriorityStatus = itemView.findViewById(R.id.txt_name_category_priority_status);
            createdDateCategoryPriorityStatus = itemView.findViewById(R.id.txt_created_category_priority_status);
            layout = itemView.findViewById(R.id.linear_item_category_priority_status);
        }
    }
}
