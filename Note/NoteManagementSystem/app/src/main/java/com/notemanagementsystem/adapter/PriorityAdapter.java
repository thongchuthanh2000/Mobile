package com.notemanagementsystem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notemanagementsystem.R;
import com.notemanagementsystem.entity.Note;
import com.notemanagementsystem.entity.Priority;

import java.util.List;

public class PriorityAdapter extends  RecyclerView.Adapter<PriorityAdapter.PriorityViewHolder> {

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

        StringBuffer sb = new StringBuffer();
        sb.append("Name: " + priority.name.toString() + '\n');

        holder.itemCategoryPriorityStatus.setText(sb);
    }

    @Override
    public int getItemCount() {
        if(mListPriority != null){
            return mListPriority.size();
        }
        return 0;
    }

    public class PriorityViewHolder extends RecyclerView.ViewHolder {

        private TextView itemCategoryPriorityStatus;

        public PriorityViewHolder(@NonNull View itemView) {
            super(itemView);

            itemCategoryPriorityStatus = itemView.findViewById(R.id.tv_item_category_priority_status);

        }
    }
}
