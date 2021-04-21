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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notemanagementsystem.AppDatabase;
import com.notemanagementsystem.R;
import com.notemanagementsystem.adapter.CategoryAdapter;
import com.notemanagementsystem.entity.Category;
import com.notemanagementsystem.entity.Priority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryFragment extends Fragment implements View.OnClickListener {

    public FloatingActionButton fabAddCategory;
    public RecyclerView rcvCategory;
    public CategoryAdapter categoryAdapter;
    public List<Category> mListCategory;

    public CategoryFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        fabAddCategory = view.findViewById(R.id.fab_add_category);
        fabAddCategory.setOnClickListener(this);

        rcvCategory = view.findViewById(R.id.rcv_category);
        categoryAdapter = new CategoryAdapter();
        mListCategory = new ArrayList<>();

        mListCategory = AppDatabase.getAppDatabase(view.getContext()).categoryDAO().getAllCategory();

        categoryAdapter.setData(mListCategory);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvCategory.setLayoutManager(linearLayoutManager);
        rcvCategory.setAdapter(categoryAdapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab_add_category){
            openDialog(Gravity.CENTER, v.getContext());
        }
    }

    private void openDialog(int gravity, Context context) {
        final Dialog dialog = new Dialog(context);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog);

//        Window window = dialog.getWindow();
//        if (window ==  null){
//            return;
//        }
//
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        WindowManager.LayoutParams windowAttribute = window.getAttributes();
//        windowAttribute.gravity = gravity;
//        window.setAttributes(windowAttribute);

        dialog.setCancelable(false);

        EditText edtNameCategory = dialog.findViewById(R.id.edt_name);
        Button btnAdd = dialog.findViewById(R.id.btn_add);
        Button btnClose = dialog.findViewById(R.id.btn_close);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameCategory = edtNameCategory.getText().toString().trim();

                if(TextUtils.isEmpty(nameCategory)){
                    return;
                }

                Category category = new Category(nameCategory, new Date());
                AppDatabase.getAppDatabase(v.getContext()).categoryDAO().insert(category);

                Toast.makeText(v.getContext(), "Add category successfully", Toast.LENGTH_LONG).show();

                //reload frm
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, new CategoryFragment()).addToBackStack(null).commit();

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