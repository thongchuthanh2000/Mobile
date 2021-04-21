package com.notemanagementsystem.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.notemanagementsystem.SessionManager;
import com.notemanagementsystem.adapter.StatusAdapter;
import com.notemanagementsystem.entity.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatusFragment extends Fragment implements View.OnClickListener {

    private FloatingActionButton fabAddStatus;
    private RecyclerView rcvStatus;
    private StatusAdapter statusAdapter;
    private List<Status> mListStatus;

    public StatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        fabAddStatus = view.findViewById(R.id.fab_add_status);
        fabAddStatus.setOnClickListener(this);

        rcvStatus = view.findViewById(R.id.rcv_status);

        statusAdapter = new StatusAdapter(new StatusAdapter.IClickItemStatus() {
            @Override
            public void updateStatus(Status status) {
                clickUpdateStatus(view, status);
            }
            @Override
            public void deleteStatus(Status status) {
                clickDeleteStatus(view, status);
            }
        });

        SessionManager sessionManager = new SessionManager(getContext());
        int userId = sessionManager.getUserId();

        mListStatus = new ArrayList<>();
        mListStatus = AppDatabase.getAppDatabase(view.getContext()).statusDAO().getAllStatusById(userId);
        statusAdapter.setData(mListStatus);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvStatus.setLayoutManager(linearLayoutManager);
        rcvStatus.setAdapter(statusAdapter);

        return view;
    }

    private void clickDeleteStatus(View view, Status status) {
        new AlertDialog.Builder(view.getContext())
                .setTitle("Confirm")
                .setMessage("Are you sure to delete this status?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    AppDatabase.getAppDatabase(view.getContext()).statusDAO().delete(status);
                    Toast.makeText(view.getContext(), "Delete status successfully!", Toast.LENGTH_SHORT).show();

                    replaceFragment(new StatusFragment());
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clickUpdateStatus(View view, Status status) {
        openDialogEdit(Gravity.CENTER, view.getContext(), status);
    }

    private void openDialogEdit(int center, Context context, Status status) {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.layout_dialog);
        dialog.setCancelable(false);

        TextView title = dialog.findViewById(R.id.title);
        EditText edtNameStatus = dialog.findViewById(R.id.edt_name);
        Button btnUpdate = dialog.findViewById(R.id.btn_add);
        Button btnClose = dialog.findViewById(R.id.btn_close);

        if (status != null){
            edtNameStatus.setText(status.getName());
            btnUpdate.setText("Update");
            title.setText("Status Form");
        }

        btnUpdate.setOnClickListener(v -> {
            String nameStatus = edtNameStatus.getText().toString().trim();

            if(TextUtils.isEmpty(nameStatus)){
                return;
            }

            status.setName(nameStatus);
            status.setCreateDate(new Date());
            AppDatabase.getAppDatabase(v.getContext()).statusDAO().update(status);

            Toast.makeText(v.getContext(), "Update status successfully", Toast.LENGTH_LONG).show();
            replaceFragment(new StatusFragment());

            dialog.cancel();
        });

        btnClose.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_add_status){
            openDialog(Gravity.CENTER, v.getContext());
        }
    }

    private void openDialog(int gravity, Context context) {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.layout_dialog);
        dialog.setCancelable(false);

        TextView title = dialog.findViewById(R.id.title);
        EditText edtNameStatus = dialog.findViewById(R.id.edt_name);
        Button btnAdd = dialog.findViewById(R.id.btn_add);
        Button btnClose = dialog.findViewById(R.id.btn_close);

        title.setText("Status Form");

        btnAdd.setOnClickListener(v -> {
            String nameStatus = edtNameStatus.getText().toString().trim();

            if(TextUtils.isEmpty(nameStatus)){
                return;
            }

            SessionManager sessionManager = new SessionManager(getContext());
            int userId = sessionManager.getUserId();

            Status status = new Status(nameStatus, new Date(), userId);
            AppDatabase.getAppDatabase(v.getContext()).statusDAO().insert(status);

            Toast.makeText(v.getContext(), "Add status successfully", Toast.LENGTH_LONG).show();

            replaceFragment(new StatusFragment());
            dialog.cancel();
        });

        btnClose.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

}