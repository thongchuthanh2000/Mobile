package com.notemanagementsystem.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notemanagementsystem.AppDatabase;

import com.notemanagementsystem.R;
import com.notemanagementsystem.utils.SessionManager;
import com.notemanagementsystem.adapter.PriorityAdapter;
import com.notemanagementsystem.entity.Priority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 *StatusFragment
 *@author  Chu Thanh
 * @version 1.0
 * @since   2021-04-24
 */
public class PriorityFragment extends Fragment implements View.OnClickListener {


    public PriorityFragment() {
        // Required empty public constructor
    }

    private FloatingActionButton fabAddPriority;
    private RecyclerView rcvPriority;
    private PriorityAdapter priorityAdapter;
    private List<Priority> mListPriority;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_priority, container, false);
        fabAddPriority = view.findViewById(R.id.fab_add_priority);
        fabAddPriority.setOnClickListener(this);

        rcvPriority = view.findViewById(R.id.rcv_priority);
        priorityAdapter = new PriorityAdapter(new PriorityAdapter.IClickItemPriority() {
            @Override
            public void update(Priority priority) {
                clickUpdatePriority(view, priority);
            }

            @Override
            public void delete(Priority priority) {
                clickDeletePriority(view,priority);
            }
        });
        //Get userID by session
        int userId = SessionManager.getInstance().getUserId();

        //Set value adapter for Status Adapter
        mListPriority = new ArrayList<>();
        mListPriority = AppDatabase.getAppDatabase(view.getContext()).priorityDAO().getAllPriorityById(userId);
        priorityAdapter.setData(mListPriority);

        //Set layout manager -> recyclerView Status
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvPriority.setLayoutManager(linearLayoutManager);
        rcvPriority.setAdapter(priorityAdapter);

        return view;
    }
    //Update item Priority
    private void clickUpdatePriority(View v, Priority priority){
        openDialogEdit(Gravity.CENTER, v.getContext(), priority);
    }

    private void openDialogEdit(int gravity, Context context, Priority priority) {
        final Dialog dialog = new Dialog(context);
        //Set contentview dialog
        dialog.setContentView(R.layout.layout_dialog);
        dialog.setCancelable(false);

        //get information from views
        TextView title = dialog.findViewById(R.id.title);
        EditText edtNamePriority = dialog.findViewById(R.id.edt_name);
        Button btnUpdate = dialog.findViewById(R.id.btn_add);
        Button btnClose = dialog.findViewById(R.id.btn_close);

        if(priority != null){
            edtNamePriority.setText(priority.getName());
            btnUpdate.setText("Update");
            title.setText("Priority Form");
        }

        btnUpdate.setOnClickListener(v -> {
            String namePriority = edtNamePriority.getText().toString().trim();

            //Check null
            if(TextUtils.isEmpty(namePriority)){
                return;
            }
            //Change priority
            priority.setName(namePriority);
            //Update data into the database
            AppDatabase.getAppDatabase(v.getContext()).priorityDAO().update(priority);

            Toast.makeText(v.getContext(), "Update priority successfully!", Toast.LENGTH_SHORT).show();

            //Load fragment
            replaceFragment(new PriorityFragment());
            dialog.cancel();
        });

        //Cancenl Update Priority
        btnClose.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();
    }

    //Delete item staus
    private void clickDeletePriority(View v, Priority priority){
        new AlertDialog.Builder(v.getContext())
                .setTitle("Confirm")
                .setMessage("Are you sure to delete this Priority?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    //Delete item status
                    AppDatabase.getAppDatabase(v.getContext()).priorityDAO().delete(priority);
                    Toast.makeText(v.getContext(), "Delete priority successfully!", Toast.LENGTH_SHORT).show();

                    replaceFragment(new PriorityFragment());
                })
                .setNegativeButton("No", null)
                .show();
    }
    @Override
    public void onClick(View v) {
        //set even for btn_add_status -- Add status
        if(v.getId() == R.id.fab_add_priority){
            openDialog(Gravity.CENTER, v.getContext());
        }
    }

    private void openDialog(int gravity, Context context) {
        final Dialog dialog = new Dialog(context);
        //Set contentview dialog
        dialog.setContentView(R.layout.layout_dialog);
        dialog.setCancelable(false);

        //get information from views
        TextView title = dialog.findViewById(R.id.title);
        EditText edtNamePriority = dialog.findViewById(R.id.edt_name);
        Button btnAdd = dialog.findViewById(R.id.btn_add);
        Button btnClose = dialog.findViewById(R.id.btn_close);
        title.setText("Priority Form");


        btnAdd.setOnClickListener(v -> {
            String namePriority = edtNamePriority.getText().toString().trim();

            //Check null
            if(TextUtils.isEmpty(namePriority)){
                return;
            }

            //declare a session
            int userId = SessionManager.getInstance().getUserId();

            //create priority
            Priority priority = new Priority(namePriority, new Date(), userId);
            //Insert data into the database
            AppDatabase.getAppDatabase(v.getContext()).priorityDAO().insert(priority);

            showToast("Add priority successfully");

            //Load fragment
            replaceFragment(new PriorityFragment());

            dialog.cancel();
        });

        //Cancenl add priority
        btnClose.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();
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