package com.notemanagementsystem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notemanagementsystem.R;
import com.notemanagementsystem.entity.Category;
import com.notemanagementsystem.entity.Note;

import java.text.SimpleDateFormat;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> mListCategory;

    public void setData(List<Category> list){
        this.mListCategory = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_priority_status, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        if (category == null){
            return;
        }

        holder.catName.setText(category.getName());
        String formatedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(category.getCreateDate());
        holder.createdDate.setText(formatedDate);
    }

    @Override
    public int getItemCount() {
        if (mListCategory != null){
            return mListCategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView catName;
        private TextView createdDate;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.txt_name_category_priority_status);
            createdDate = itemView.findViewById(R.id.txt_created_category_priority_status);
        }
    }
}
