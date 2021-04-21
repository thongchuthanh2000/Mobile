package com.notemanagementsystem.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notemanagementsystem.AppDatabase;
import com.notemanagementsystem.SessionManager;
import com.notemanagementsystem.adapter.NoteAdapter;
import com.notemanagementsystem.R;
import com.notemanagementsystem.entity.Category;
import com.notemanagementsystem.entity.Note;
import com.notemanagementsystem.entity.Priority;
import com.notemanagementsystem.entity.Status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NoteFragment extends Fragment implements View.OnClickListener {

    public NoteFragment() {
    }

    //
    private FloatingActionButton fabAddNote;
    private RecyclerView rcvNote;
    private NoteAdapter noteAdapter;
    private List<Note> mListNote;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    private String category = "_";
    private String priority = "_";
    private String status = "_";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        fabAddNote = view.findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(this);

        rcvNote = view.findViewById(R.id.rcv_Note);
        noteAdapter = new NoteAdapter(new NoteAdapter.IClickItemNote() {
            @Override
            public void updateNote(Note note) {
                clickUpdateNote(view, note);
            }

            @Override
            public void deleteNote(Note note) {
                clickDeleteNote(view, note);
            }
        });

        SessionManager sessionManager = new SessionManager(getContext());
        int userId = sessionManager.getUserId();

        mListNote = new ArrayList<>();
        mListNote = AppDatabase.getAppDatabase(view.getContext()).noteDAO().getAllNoteById(userId);
        noteAdapter.setData(mListNote);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvNote.setLayoutManager(linearLayoutManager);
        rcvNote.setAdapter(noteAdapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab_add_note){
            openDialogAdd(Gravity.CENTER, v.getContext());
        }
    }

    private void openDialogAdd(int gravity, Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_note);

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

        EditText edtNameNote = dialog.findViewById(R.id.edt_name_note);
        Button btnAdd = dialog.findViewById(R.id.btn_add_note);
        Button btnClose = dialog.findViewById(R.id.btn_close_note);
        Button btnPlanDate = dialog.findViewById(R.id.btn_plan_date);
        TextView tvPlanDate = dialog.findViewById(R.id.tv_plan_date);

        //add a new note
        btnAdd.setOnClickListener(v -> {
            String nameNote = edtNameNote.getText().toString().trim();
            String planDate = tvPlanDate.getText().toString().trim();

            if(TextUtils.isEmpty(nameNote) || TextUtils.isEmpty(planDate)){
                return;
            }

            Date date;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(planDate);
            } catch (ParseException e) {
                date = new Date();
            }

            SessionManager sessionManager = new SessionManager(getContext());
            int userId = sessionManager.getUserId();

            Note note = new Note(nameNote, date, new Date(), category, priority, status, userId);
            AppDatabase.getAppDatabase(v.getContext()).noteDAO().insert(note);

            Toast.makeText(v.getContext(), "Add note successfully!", Toast.LENGTH_SHORT).show();

            replaceFragment(new NoteFragment());

            dialog.cancel();
        });

        //Close dialog
        btnClose.setOnClickListener(v -> {
            dialog.cancel();
        });

        //Select plan date
        btnPlanDate.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                    tvPlanDate.setText(mYear + "-" + (mMonth+1) + "-" + mDayOfMonth);
                }
            }, year, month, day);

            datePickerDialog.show();
        });

        SessionManager sessionManager = new SessionManager(getContext());
        int userId = sessionManager.getUserId();

        //Select category
        List<String> liCate = getList(context, 0, userId);
        ArrayAdapter<String> dataAdapterCate = new ArrayAdapter(context, android.R.layout.simple_spinner_item, liCate);
        dataAdapterCate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spnCategory = dialog.findViewById(R.id.spn_category);
        spnCategory.setAdapter(dataAdapterCate);

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    category = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Select priority
        List<String> liPrio = getList(context, 1, userId);
        ArrayAdapter<String> dataAdapterPrio = new ArrayAdapter(context, android.R.layout.simple_spinner_item, liPrio);
        dataAdapterPrio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spnPriority = dialog.findViewById(R.id.spn_priority);
        spnPriority.setAdapter(dataAdapterPrio);

        spnPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0){
                    priority = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Select status
        List<String> liStt = getList(context, 2, userId);
        ArrayAdapter<String> dataAdapterStt = new ArrayAdapter(context, android.R.layout.simple_spinner_item, liStt);
        dataAdapterStt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spnStatus = dialog.findViewById(R.id.spn_status);
        spnStatus.setAdapter(dataAdapterStt);

        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    status = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog.show();
    }

    //Update note
    private void clickUpdateNote(View v, Note note){
        openDialogEdit(Gravity.CENTER, v.getContext(), note);
    }

    private void openDialogEdit(int gravity, Context context, Note note){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_edit_note);

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

        EditText edtNameNote = dialog.findViewById(R.id.edt_name_note);
        Button btnUpdate = dialog.findViewById(R.id.btn_update_note);
        Button btnClose = dialog.findViewById(R.id.btn_close_note);
        Button btnPlanDate = dialog.findViewById(R.id.btn_plan_date);
        TextView tvPlanDate = dialog.findViewById(R.id.tv_plan_date);

        if (note==null){
            return;
        }

            edtNameNote.setText(note.getName());
            String formatPlanDate = new SimpleDateFormat("yyyy-MM-dd").format(note.getPlanDate());
            tvPlanDate.setText(formatPlanDate);
            category = note.getCategory();
            priority = note.getPriority();
            status = note.getStatus();


        //update note
        btnUpdate.setOnClickListener(v -> {
            String nameNote = edtNameNote.getText().toString().trim();
            String planDate = tvPlanDate.getText().toString().trim();

            if(TextUtils.isEmpty(nameNote) || TextUtils.isEmpty(planDate)){
                return;
            }

            //convert plan date
            Date date = new Date();
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                date = formatter.parse(planDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            note.setName(nameNote);
            note.setPlanDate(date);
            note.setCategory(category);
            note.setPriority(priority);
            note.setStatus(status);

            AppDatabase.getAppDatabase(v.getContext()).noteDAO().update(note);

            Toast.makeText(v.getContext(), "Update note successfully!", Toast.LENGTH_SHORT).show();
            replaceFragment(new NoteFragment());

            dialog.cancel();
        });

        //close dialog
        btnClose.setOnClickListener(v -> {
            dialog.cancel();
        });

        //update plan date
        btnPlanDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                        tvPlanDate.setText(mYear + "-" + (mMonth+1) + "-" + mDayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        SessionManager sessionManager = new SessionManager(getContext());
        int userId = sessionManager.getUserId();

        //update  category
        List<String> li = getList(context, 0, userId);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, li);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spnCategory = dialog.findViewById(R.id.spn_category);
        spnCategory.setAdapter(dataAdapter);

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(position!=0){
                    category = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Update priority
        List<String> liPrio = getList(context, 1, userId);
        ArrayAdapter<String> dataAdapterPrio = new ArrayAdapter(context, android.R.layout.simple_spinner_item, liPrio);
        dataAdapterPrio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spnPriority = dialog.findViewById(R.id.spn_priority);
        spnPriority.setAdapter(dataAdapterPrio);

        spnPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(position!=0){
                    priority = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Update status
        List<String> liStt = getList(context, 2, userId);
        ArrayAdapter<String> dataAdapterStt = new ArrayAdapter(context, android.R.layout.simple_spinner_item, liStt);
        dataAdapterStt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spnStatus = dialog.findViewById(R.id.spn_status);
        spnStatus.setAdapter(dataAdapterStt);

        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    status = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog.show();
    }

    //delete note
    private void clickDeleteNote(View v, Note note){
        new AlertDialog.Builder(v.getContext())
                .setTitle(note.getName().trim())
                .setMessage("Are you sure to delete this note?")
                .setPositiveButton("Yes",((dialog, which) -> {
                    AppDatabase.getAppDatabase(v.getContext()).noteDAO().delete(note);
                    Toast.makeText(v.getContext(), "Deleted " + note.getName().toString().trim() + "!", Toast.LENGTH_SHORT).show();

                    replaceFragment(new NoteFragment());
                }))
                .setNegativeButton("No", null)
                .show();
    }

    //get list for selecting category, priority and status
    private List<String> getList(Context context, int i, int userId){
        List<String> list = new ArrayList<>();
        switch (i) {
            case 0:
                list.add(0, "Select category...");
                for(Category c : AppDatabase.getAppDatabase(context).categoryDAO().getAllCategoryById(userId)){
                    list.add(c.getName().toString().trim());
                }
                return list;
            case 1:
                list.add(0, "Select priority...");
                for(Priority p : AppDatabase.getAppDatabase(context).priorityDAO().getAllPriorityById(userId)){
                    list.add(p.getName().toString().trim());
                }
                return list;
            case 2:
                list.add(0, "Select status...");
                for(Status s : AppDatabase.getAppDatabase(context).statusDAO().getAllStatusById(userId)){
                    list.add(s.getName().toString().trim());
                }
                return list;
        }
        return list;
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}