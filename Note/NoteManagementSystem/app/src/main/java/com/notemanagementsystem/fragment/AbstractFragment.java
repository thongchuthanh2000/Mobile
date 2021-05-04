package com.notemanagementsystem.fragment;

import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notemanagementsystem.AppDatabase;
import com.notemanagementsystem.R;
import com.notemanagementsystem.adapter.GenericAdapter;
import com.notemanagementsystem.constant.SystemConstant;
import com.notemanagementsystem.entity.Category;
import com.notemanagementsystem.entity.Priority;
import com.notemanagementsystem.entity.Status;
import com.notemanagementsystem.utils.SessionManager;
import com.notemanagementsystem.entity.AbstractEntity;
import  com.notemanagementsystem.dao.AbstractDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.notemanagementsystem.AppDatabase.getAppDatabase;

public class AbstractFragment<T extends AbstractEntity> extends Fragment implements View.OnClickListener {

    private FloatingActionButton fabAddAbstract;
    private RecyclerView rcvAbstract;
    private GenericAdapter<T> adapter;
    private List<T> mList;


    @Override
    public void onClick(View v) {
        //set even for btn_add_status -- Add status
        if(v.getId() == R.id.fab_add_abstract){
            openDialogAddOrUpdate(Gravity.CENTER, v.getContext(),null);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_abstract, container, false);
        fabAddAbstract = view.findViewById(R.id.fab_add_abstract);
        fabAddAbstract.setOnClickListener(this);

        rcvAbstract = view.findViewById(R.id.rcv_abstract);
        adapter = new GenericAdapter<>(new GenericAdapter.IClickItem<T>() {
            @Override
            public void update(T item) {
                clickUpdateStatus(view,item);
            }

            @Override
            public void delete(T item) {
                clickDeleteAbstract(view,item);
            }
        });


        //Get userID by session
        int userId = SessionManager.getInstance().getUserId();

        //Set value adapter for Status Adapter
        mList = new ArrayList<>();
        mList = (List<T>) AppDatabase.getAppDatabase(view.getContext()).abstractDao().getAllById(userId);
        adapter.setData(mList);

        //Set layout manager -> recyclerView Status
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvAbstract.setLayoutManager(linearLayoutManager);
        rcvAbstract.setAdapter(adapter);

        return  view;
    }
    private void clickUpdateStatus(View view, T item) {
        openDialogAddOrUpdate(Gravity.CENTER, view.getContext(), item);
    }

    private void clickDeleteAbstract(View view, T item){
        new AlertDialog.Builder(view.getContext())
                .setTitle("Confirm")
                .setMessage("Are you sure to delete this ?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    //Delete item status
                    getAppDatabase(view.getContext()).abstractDao().delete(item);

                    showToast( "Delete successfully!");
                    replaceFragment(new AbstractFragment<T>());
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void    openDialogAddOrUpdate(int gravity,Context context,T item){
        final Dialog dialog = new Dialog(context);
        //Set contentview dialog
        dialog.setContentView(R.layout.layout_dialog);
        dialog.setCancelable(false);

        //get information from views
        TextView title = dialog.findViewById(R.id.title);
        EditText edtName = dialog.findViewById(R.id.edt_name);
        Button btnAddOrUpdate = dialog.findViewById(R.id.btn_add);
        Button btnClose = dialog.findViewById(R.id.btn_close);

        if (SystemConstant.CHOOSE.equals(SystemConstant.PRIORITY)) {
            title.setText(SystemConstant.PRIORITY+" From");
        }
        if (SystemConstant.CHOOSE.equals(SystemConstant.STATUS)) {
            title.setText(SystemConstant.STATUS+" From");
        }
        if (SystemConstant.CHOOSE.equals(SystemConstant.CATEGORY)) {
            title.setText(SystemConstant.CATEGORY+" From");
        }

        if (item==null){
            btnAddOrUpdate.setText("Add");

            btnAddOrUpdate.setOnClickListener(v -> {
                String name = edtName.getText().toString().trim();

                //Check null
                if(TextUtils.isEmpty(name)){
                    return;
                }

                //declare a session
                int userId = SessionManager.getInstance().getUserId();

                if (SystemConstant.CHOOSE.equals(SystemConstant.PRIORITY)) {
                    getAppDatabase(v.getContext()).abstractDao().insert(new Priority(name,new Date(),userId));
                }
                if (SystemConstant.CHOOSE.equals(SystemConstant.STATUS)) {
                    getAppDatabase(v.getContext()).abstractDao().insert(new Status(name,new Date(),userId));
                }
                if (SystemConstant.CHOOSE.equals(SystemConstant.CATEGORY)) {
                    getAppDatabase(v.getContext()).abstractDao().insert(new Category(name,new Date(),userId));
                }
                showToast("Add successfully");

                //Load fragment
                replaceFragment(new AbstractFragment<T>());
                dialog.cancel();

            });
            //Cancenl add priority
            btnClose.setOnClickListener(v -> {
                dialog.cancel();
            });

            dialog.show();

        }
        else{
            btnAddOrUpdate.setText("Update");
            edtName.setText(item.getName());

            btnAddOrUpdate.setOnClickListener(v -> {
                String name = edtName.getText().toString().trim();

                //Check null
                if(TextUtils.isEmpty(name)){
                    return;
                }

                //Change status
                item.setName(name);
                item.setCreateDate(new Date());
                //Update data into the database
                AppDatabase.getAppDatabase(v.getContext()).abstractDao().update(item);

                showToast("Update  successfully");

                //Load fragment
                replaceFragment(new AbstractFragment<T>());

                dialog.cancel();
            });

            //Cancenl Update status
            btnClose.setOnClickListener(v -> {
                dialog.cancel();
            });

            dialog.show();

        }

    }

    //switch to another fragment
    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_SHORT).show();
    }
}
