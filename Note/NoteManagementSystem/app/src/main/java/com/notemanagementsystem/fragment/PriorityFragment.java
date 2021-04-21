package com.notemanagementsystem.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notemanagementsystem.AppDatabase;

import com.notemanagementsystem.R;
import com.notemanagementsystem.adapter.PriorityAdapter;
import com.notemanagementsystem.dao.NoteDAO;
import com.notemanagementsystem.entity.Note;
import com.notemanagementsystem.entity.Priority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PriorityFragment extends Fragment implements View.OnClickListener {


    public PriorityFragment() {

    }


    public FloatingActionButton fabAddPriority;
    public RecyclerView rcvPriority;
    public PriorityAdapter priorityAdapter;
    public List<Priority> mListPriority;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_priority, container, false);

        fabAddPriority = view.findViewById(R.id.fab_add_priority);
        fabAddPriority.setOnClickListener(this);

        rcvPriority = view.findViewById(R.id.rcv_Priority);
        priorityAdapter = new PriorityAdapter();
        mListPriority = new ArrayList<>();

        mListPriority = AppDatabase.getAppDatabase(view.getContext()).priorityDAO().getAllPriority();

        priorityAdapter.setData(mListPriority);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvPriority.setLayoutManager(linearLayoutManager);
        rcvPriority.setAdapter(priorityAdapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab_add_priority){
            openDialog(Gravity.CENTER, v.getContext());
        }
    }

    private void openDialog(int gravity, Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_priority);

        Window window = dialog.getWindow();
        if (window ==  null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);

        dialog.setCancelable(false);

        EditText edtNamePriority = dialog.findViewById(R.id.edt_name_priority);
        Button btnAdd = dialog.findViewById(R.id.btn_add);
        Button btnClose = dialog.findViewById(R.id.btn_close);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameNote = edtNamePriority.getText().toString().trim();

                if(TextUtils.isEmpty(nameNote)){
                    return;
                }


                Priority priority = new Priority(nameNote, new Date());
                AppDatabase.getAppDatabase(v.getContext()).priorityDAO().insert(priority);

                Toast.makeText(v.getContext(), "Add note successfully!", Toast.LENGTH_SHORT).show();

                mListPriority = AppDatabase.getAppDatabase(v.getContext()).priorityDAO().getAllPriority();

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new NoteFragment()).addToBackStack(null).commit();

                dialog.cancel();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}