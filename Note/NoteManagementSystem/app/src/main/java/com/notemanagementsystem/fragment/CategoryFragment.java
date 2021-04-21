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
import com.notemanagementsystem.adapter.CategoryAdapter;
import com.notemanagementsystem.entity.Category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryFragment extends Fragment implements View.OnClickListener {

    public FloatingActionButton fabAddCategory;
    public RecyclerView rcvCategory;
    public CategoryAdapter categoryAdapter;
    public List<Category> mListCategory;

    public CategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        fabAddCategory = view.findViewById(R.id.fab_add_category);
        fabAddCategory.setOnClickListener(this);

        rcvCategory = view.findViewById(R.id.rcv_category);
        categoryAdapter = new CategoryAdapter(new CategoryAdapter.IClickItemCategory() {
            @Override
            public void updateCategory(Category category) {
                clickUpdateCategory(view, category);
            }

            @Override
            public void deleteCategory(Category category) {
                clickDeleteCategory(view, category);
            }
        });

        SessionManager sessionManager = new SessionManager(getContext());
        int userId = sessionManager.getUserId();

        mListCategory = new ArrayList<>();
        mListCategory = AppDatabase.getAppDatabase(view.getContext()).categoryDAO().getAllCategoryById(userId);
        categoryAdapter.setData(mListCategory);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvCategory.setLayoutManager(linearLayoutManager);
        rcvCategory.setAdapter(categoryAdapter);

        return view;
    }

    private void clickDeleteCategory(View view, Category category) {
        new AlertDialog.Builder(view.getContext())
                .setTitle("Confirm")
                .setMessage("Are you sure to delete this category?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppDatabase.getAppDatabase(view.getContext()).categoryDAO().delete(category);
                        Toast.makeText(view.getContext(), "Delete category successfully!", Toast.LENGTH_SHORT).show();

                        //reload frm
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new CategoryFragment()).addToBackStack(null).commit();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clickUpdateCategory(View view, Category category) {
        openDialogEdit(Gravity.CENTER, view.getContext(), category);
    }

    private void openDialogEdit(int center, Context context, Category category) {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.layout_dialog);
        dialog.setCancelable(false);

        TextView title = dialog.findViewById(R.id.title);
        EditText edtNameCategory = dialog.findViewById(R.id.edt_name);
        Button btnUpdate = dialog.findViewById(R.id.btn_add);
        Button btnClose = dialog.findViewById(R.id.btn_close);

        if (category != null){
            edtNameCategory.setText(category.getName());
            btnUpdate.setText("Update");
            title.setText("Category Form");
        }

        btnClose.setOnClickListener(v -> {
            String nameCategory = edtNameCategory.getText().toString().trim();

            if(TextUtils.isEmpty(nameCategory)){
                return;
            }

            category.setName(nameCategory);
            category.setCreateDate(new Date());
            AppDatabase.getAppDatabase(v.getContext()).categoryDAO().update(category);

            showToast("Update category successfully");
            replaceFragment( new CategoryFragment());

            dialog.cancel();
        });

         btnClose.setOnClickListener(v -> {
             dialog.cancel();
         });

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab_add_category){
            openDialog(Gravity.CENTER, v.getContext());
        }
    }

    private void openDialog(int gravity, Context context) {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.layout_dialog);
        dialog.setCancelable(false);

        TextView title = dialog.findViewById(R.id.title);
        EditText edtNameCategory = dialog.findViewById(R.id.edt_name);
        Button btnAdd = dialog.findViewById(R.id.btn_add);
        Button btnClose = dialog.findViewById(R.id.btn_close);

        title.setText("Category Form");

        btnAdd.setOnClickListener(v -> {
            String nameCategory = edtNameCategory.getText().toString().trim();

            if(TextUtils.isEmpty(nameCategory)){
                return;
            }

            SessionManager sessionManager = new SessionManager(getContext());
            int userId = sessionManager.getUserId();

            Category category = new Category(nameCategory, new Date(), userId);
            AppDatabase.getAppDatabase(v.getContext()).categoryDAO().insert(category);

            showToast("Add category successfully");
            replaceFragment( new CategoryFragment());

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

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_SHORT).show();
    }
}