package com.notemanagementsystem.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import com.notemanagementsystem.utils.SessionManager;
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
/*
 *StatusFragment
 *@author  Van Nghia
 * @version 1.0
 * @since   2021-04-24
 */
public class NoteFragment extends Fragment implements View.OnClickListener {
    public NoteFragment() { }

    private FloatingActionButton fabAddNote;
    private RecyclerView rcvNote;
    private NoteAdapter noteAdapter;
    private List<Note> mListNote;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    private int category = -1;
    private int priority = -1;
    private int status = -1;

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

        int userId = SessionManager.getInstance().getUserId();
        mListNote = new ArrayList<>();
        mListNote = AppDatabase.getAppDatabase(view.getContext()).noteDAO().getAllById(userId);
        noteAdapter.setData(mListNote);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvNote.setLayoutManager(linearLayoutManager);
        rcvNote.setAdapter(noteAdapter);

        return view;
    }

    //add new note
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab_add_note){
            openDialogAddOrUpdate(Gravity.CENTER, v.getContext(), null);
        }
    }

    //Update note
    private void clickUpdateNote(View v, Note note){
        openDialogAddOrUpdate(Gravity.CENTER, v.getContext(), note);
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

    private void openDialogAddOrUpdate(int gravity, Context context, Note note) {
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
        Button btnAddOrUpdate = dialog.findViewById(R.id.btn_add_or_update_note);
        Button btnClose = dialog.findViewById(R.id.btn_close_note);
        Button btnPlanDate = dialog.findViewById(R.id.btn_plan_date);
        TextView tvPlanDate = dialog.findViewById(R.id.tv_plan_date);

        if(note == null) {
            btnAddOrUpdate.setText("Add");

            //add a new note
            btnAddOrUpdate.setOnClickListener(v -> {

                String nameNote = edtNameNote.getText().toString().trim();
                String planDate = tvPlanDate.getText().toString().trim();

                if(TextUtils.isEmpty(nameNote) || TextUtils.isEmpty(planDate) || category<0 || priority<0 || status<0){
                    Toast.makeText(v.getContext(), "Note's name, category, priority, status and plan date can't be empty!\nCheck to see if you have added categories, priorities and status before!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Date date = new Date();;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(planDate);
                }
                catch (ParseException e) { }

                if(date.before(new Date())){
                    Toast.makeText(v.getContext(), "Plan date can't be in the past!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int userId = SessionManager.getInstance().getUserId();

                Note newNote = new Note(nameNote, date, new Date(), category, priority, status, userId);
                AppDatabase.getAppDatabase(v.getContext()).noteDAO().insert(newNote);
                Toast.makeText(v.getContext(), "Add note successfully!", Toast.LENGTH_SHORT).show();

                replaceFragment(new NoteFragment());

                dialog.cancel();
            });
        }
        else {

            btnAddOrUpdate.setText("Update");

            edtNameNote.setText(note.getName());
            String formatPlanDate = new SimpleDateFormat("yyyy-MM-dd").format(note.getPlanDate());
            tvPlanDate.setText(formatPlanDate);
            category = note.getCategoryId();
            priority = note.getPriorityId();
            status = note.getStatusId();

            //update note
            btnAddOrUpdate.setOnClickListener(v -> {
                String nameNote = edtNameNote.getText().toString().trim();
                String planDate = tvPlanDate.getText().toString().trim();

                if(TextUtils.isEmpty(nameNote) || TextUtils.isEmpty(planDate) || category<0 || priority<0 || status<0){
                    Toast.makeText(v.getContext(), "Note's name, category, priority, status and plan date can't be empty!\nCheck to see if you have added categories, priorities and status before!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Date date = new Date();
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    date = formatter.parse(planDate);
                }
                catch (ParseException e) { }

                note.setName(nameNote);
                note.setPlanDate(date);
                note.setCategoryId(category);
                note.setPriorityId(priority);
                note.setStatusId(status);

                AppDatabase.getAppDatabase(v.getContext()).noteDAO().update(note);

                Toast.makeText(v.getContext(), "Update note successfully!", Toast.LENGTH_SHORT).show();
                replaceFragment(new NoteFragment());

                dialog.cancel();
            });
        }

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

        int userId = SessionManager.getInstance().getUserId();

        //select cate, priority, status
        for(int i = 0; i <= 2; i++){
            List<String> li = getList(context, i, userId);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, li);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            Spinner spn;
            switch (i){
                case 0:
                    spn = dialog.findViewById(R.id.spn_category);
                    spn.setAdapter(dataAdapter);
                    if (note != null) {
                        String value = AppDatabase.getAppDatabase(context).categoryDAO().getCategoryById(note.getCategoryId()).getName();
                        int spinnerPosition = dataAdapter.getPosition(value);
                        spn.setSelection(spinnerPosition);
                    };
                    spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position!=0){
                                String name = parent.getItemAtPosition(position).toString();
                                Category cate = AppDatabase.getAppDatabase(view.getContext()).categoryDAO().getCategoryByName(name);
                                category = cate.getId();
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                case 1:
                    spn = dialog.findViewById(R.id.spn_priority);
                    spn.setAdapter(dataAdapter);
                    if (note != null) {
                        String value = AppDatabase.getAppDatabase(context).priorityDAO().getPriorityById(note.getPriorityId()).getName();
                        int spinnerPosition = dataAdapter.getPosition(value);
                        spn.setSelection(spinnerPosition);
                    };
                    spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position!=0){
                                String name = parent.getItemAtPosition(position).toString();
                                Priority prio = AppDatabase.getAppDatabase(view.getContext()).priorityDAO().getPriorityByName(name);
                                priority = prio.getId();
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                case 2:
                    spn = dialog.findViewById(R.id.spn_status);
                    spn.setAdapter(dataAdapter);
                    if (note != null) {
                        String value = AppDatabase.getAppDatabase(context).statusDAO().getStatusById(note.getStatusId()).getName();
                        int spinnerPosition = dataAdapter.getPosition(value);
                        spn.setSelection(spinnerPosition);
                    };
                    spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position!=0){
                                String name = parent.getItemAtPosition(position).toString();
                                Status stt = AppDatabase.getAppDatabase(view.getContext()).statusDAO().getStatusByName(name);
                                status = stt.getId();
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
            }
        }
        dialog.show();
    }

    //get list for selecting category, priority and status
    private List<String> getList(Context context, int i, int userId){
        List<String> list = new ArrayList<>();
        switch (i) {
            case 0:
                list.add(0, "Select category...");
                for(Category c : AppDatabase.getAppDatabase(context).categoryDAO().getAllById(userId)){
                    list.add(c.getName().trim());
                }
                return list;
            case 1:
                list.add(0, "Select priority...");
                for(Priority p : AppDatabase.getAppDatabase(context).priorityDAO().getAllById(userId)){
                    list.add(p.getName().trim());
                }
                return list;
            case 2:
                list.add(0, "Select status...");
                for(Status s : AppDatabase.getAppDatabase(context).statusDAO().getAllById(userId)){
                    list.add(s.getName().trim());
                }
                return list;
        }
        return list;
    }

    //switch to another fragment
    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}