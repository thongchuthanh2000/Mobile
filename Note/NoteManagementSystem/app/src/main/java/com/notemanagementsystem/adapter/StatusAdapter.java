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
import com.notemanagementsystem.entity.Status;

import java.text.SimpleDateFormat;
import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {

    private List<Status> mListStatus;

    public StatusAdapter(IClickItemStatus iClickItemStatus) {
        this.iClickItemStatus = iClickItemStatus;
    }

    public void setData(List<Status> list){
        this.mListStatus = list;
        notifyDataSetChanged();
    }

    private IClickItemStatus iClickItemStatus;
    public interface IClickItemStatus{
        void updateStatus(Status status);
        void deleteStatus(Status status);
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_priority_status, parent, false);
        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
        Status status = mListStatus.get(position);
        if (status == null){
            return;
        }

        holder.statusName.setText(status.getName());
        String formatedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(status.getCreateDate());
        holder.createdDate.setText(formatedDate);

        holder.statusName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.statusName);
                popupMenu.getMenuInflater().inflate(R.menu.option_popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.it_edit:
                                iClickItemStatus.updateStatus(status);
                                return true;
                            case R.id.it_delete:
                                iClickItemStatus.deleteStatus(status);
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
        if (mListStatus != null){
            return mListStatus.size();
        }
        return 0;
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder{
        private TextView statusName;
        private TextView createdDate;

        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);

            statusName = itemView.findViewById(R.id.txt_name_category_priority_status);
            createdDate = itemView.findViewById(R.id.txt_created_category_priority_status);
        }
    }
}
