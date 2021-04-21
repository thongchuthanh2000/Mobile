package com.notemanagementsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notemanagementsystem.R;
import com.notemanagementsystem.entity.Category;

import java.text.SimpleDateFormat;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> mListCategory;

    public CategoryAdapter(IClickItemCategory iClickItemCategory) {
        this.iClickItemCategory = iClickItemCategory;
    }

    public void setData(List<Category> mListCategory){
        this.mListCategory = mListCategory;
        notifyDataSetChanged();
    }

    private CategoryAdapter.IClickItemCategory iClickItemCategory;
    public interface IClickItemCategory{
        void updateCategory(Category category);
        void deleteCategory(Category category);
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

        holder.categoryName.setText(category.getName());
        String formatedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(category.getCreateDate());
        holder.createdDate.setText(formatedDate);


        holder.categoryName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.categoryName);
                popupMenu.getMenuInflater().inflate(R.menu.option_popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.it_edit:
                                iClickItemCategory.updateCategory(category);
                                return true;
                            case R.id.it_delete:
                                iClickItemCategory.deleteCategory(category);
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
        if (mListCategory != null){
            return mListCategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView categoryName;
        private TextView createdDate;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.txt_name_category_priority_status);
            createdDate = itemView.findViewById(R.id.txt_created_category_priority_status);
        }
    }
}
