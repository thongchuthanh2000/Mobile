package com.notemanagementsystem.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notemanagementsystem.AppDatabase;
import com.notemanagementsystem.entity.AbstractEntity;
import com.notemanagementsystem.utils.SessionManager;
import com.notemanagementsystem.adapter.NoteAdapter;
import com.notemanagementsystem.R;
import com.notemanagementsystem.entity.Category;
import com.notemanagementsystem.entity.Note;
import com.notemanagementsystem.entity.Priority;
import com.notemanagementsystem.entity.Status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
/*
 *StatusFragment
 *@author  Van Nghia
 * @version 1.0
 * @since   2021-04-24
 */
public class NoteFragment extends Fragment {
    public NoteFragment() { }

    private View view;

    private FloatingActionButton fabAddNote;

    private RecyclerView rcvNote;
    private NoteAdapter noteAdapter;
    private List<Note> mListNote;
    LinearLayoutManager linearLayoutManager;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private Date planDate;

    private Category category;
    private Priority priority;
    private Status status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_note, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fabAddNote = view.findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddOrUpdate(Gravity.CENTER, v.getContext(), null);
            }
        });

        rcvNote = view.findViewById(R.id.rcv_Note);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvNote.setLayoutManager(linearLayoutManager);

        noteAdapter = new NoteAdapter(new NoteAdapter.IClickItemNote() {
            @Override
            public void updateNote(Note note) {
                clickUpdateNote(note);
            }
            @Override
            public void deleteNote(Note note) {
                clickDeleteNote(note);
            }
        });

        int userId = SessionManager.getInstance().getUserId();
        mListNote = new ArrayList<>();
        mListNote = AppDatabase.getAppDatabase(view.getContext()).noteDAO().getAllById(userId);

        noteAdapter.setData(mListNote);
        rcvNote.setAdapter(noteAdapter);
    }

    //Update note
    private void clickUpdateNote(Note note){
        openDialogAddOrUpdate(Gravity.CENTER, this.getContext(), note);
    }

    //delete note
    private void clickDeleteNote(Note note){
        new AlertDialog.Builder(this.getContext())
                .setTitle(note.getName().trim())
                .setMessage("Are you sure to delete this note?")
                .setPositiveButton("Yes",((dialog, which) -> {
                    AppDatabase.getAppDatabase(this.getContext()).noteDAO().delete(note);
                    Toast.makeText(this.getContext(), "Deleted " + note.getName().toString().trim() + "!", Toast.LENGTH_SHORT).show();

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
                String sPlanDate = tvPlanDate.getText().toString().trim();

                if(TextUtils.isEmpty(nameNote) || TextUtils.isEmpty(sPlanDate) || category.getId()==-1 || priority.getId()==-1  || status.getId()==-1 ){
                    Toast.makeText(v.getContext(), "Note's name, category, priority, status and plan date can't be empty!\nCheck to see if you have added categories, priorities and status before!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int userId = SessionManager.getInstance().getUserId();

                Note newNote = new Note(nameNote, planDate, new Date(), category.getId(), priority.getId(), status.getId(), userId);
                AppDatabase.getAppDatabase(v.getContext()).noteDAO().insert(newNote);
                Toast.makeText(v.getContext(), "Add note successfully!", Toast.LENGTH_SHORT).show();

                replaceFragment(new NoteFragment());

                dialog.cancel();
            });
        }
        else {

            btnAddOrUpdate.setText("Update");

            edtNameNote.setText(note.getName());
            tvPlanDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(note.getPlanDate()));
            category = AppDatabase.getAppDatabase(context).categoryDAO().getById(note.getCategoryId());
            priority = AppDatabase.getAppDatabase(context).priorityDAO().getById(note.getPriorityId());
            status = AppDatabase.getAppDatabase(context).statusDAO().getById(note.getStatusId());

            //update note
            btnAddOrUpdate.setOnClickListener(v -> {
                String nameNote = edtNameNote.getText().toString().trim();
                String sPlanDate = tvPlanDate.getText().toString().trim();

                if(TextUtils.isEmpty(nameNote) || TextUtils.isEmpty(sPlanDate) || category.getId()==-1 || priority.getId()==-1  || status.getId()==-1 ){
                    Toast.makeText(v.getContext(), "Note's name, category, priority, status and plan date can't be empty!\nCheck to see if you have added categories, priorities and status before!", Toast.LENGTH_SHORT).show();
                    return;
                }

                note.setName(nameNote);
                note.setPlanDate(planDate);
                note.setCategoryId(category.getId());
                note.setPriorityId(priority.getId());
                note.setStatusId(status.getId());

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
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {

                    timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int mHourOfDay, int mMinute) {

                            calendar.set(mYear, mMonth, mDayOfMonth, mHourOfDay, mMinute, 0);
                            planDate = calendar.getTime();

                            tvPlanDate.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(planDate));

                        }
                    }, hour, minute, true);
                    timePickerDialog.show();
                }
            }, year, month, day);
            datePickerDialog.show();
        });

        int userId = SessionManager.getInstance().getUserId();

        //select cate, priority, status
        setSpiner(context,dialog,0,userId,note);
        setSpiner(context,dialog,1,userId,note);
        setSpiner(context,dialog,2,userId,note);

        dialog.show();
    }

    private <T extends AbstractEntity> void setSpiner (Context context,Dialog dialog,int i,int userId,Note note){
        ArrayList<T> list = (ArrayList<T>) getList(context,i,userId);

        ArrayAdapter<T> adapter = new ArrayAdapter<T>(context, android.R.layout.simple_spinner_item,list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spn =null;
        if (i==0){
            spn = dialog.findViewById(R.id.spn_category);
        }
        if (i==1){
            spn = dialog.findViewById(R.id.spn_priority);
        }
        if (i==2){
            spn = dialog.findViewById(R.id.spn_status);
        }

        spn.setAdapter(adapter);

        if (note != null) {
            int spinnerPosition=-1;
            if (i ==0) {
                Category categoryNote = AppDatabase.getAppDatabase(context).categoryDAO().getById(note.getCategoryId());
                spinnerPosition = adapter.getPosition((T) categoryNote);
                spn.setSelection(spinnerPosition,true);
            }
            if (i ==1) {
                Priority priorityNote = AppDatabase.getAppDatabase(context).priorityDAO().getById(note.getPriorityId());
                spinnerPosition = adapter.getPosition((T) priorityNote);
                spn.setSelection(spinnerPosition,true);
            }
            if (i ==2) {
                Status statusNote = AppDatabase.getAppDatabase(context).statusDAO().getById(note.getStatusId());
                spinnerPosition = adapter.getPosition((T) statusNote);
                spn.setSelection(spinnerPosition,true);
            }

        };

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    T aClazz = (T) parent.getSelectedItem();

                    if (i ==0) {
                        category = (Category) aClazz;
                    }
                    if (i ==1) {
                        priority = (Priority) aClazz;
                    }
                    if (i ==2) {
                        status = (Status) aClazz;
                    }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    //get list for selecting category, priority and status
    private <T extends AbstractEntity> List<T> getList(Context context, int i, int userId){
        List<T> list = new ArrayList<>();
        list.add(getTempValue(context,i,userId));
        switch (i) {
            case 0:
                list.addAll((Collection<? extends T>) AppDatabase.getAppDatabase(context).categoryDAO().getAllById(userId));
                return list;
            case 1:
                list.addAll((Collection<? extends T>) AppDatabase.getAppDatabase(context).priorityDAO().getAllById(userId));
                return list;
            case 2:
                list.addAll((Collection<? extends T>) AppDatabase.getAppDatabase(context).statusDAO().getAllById(userId));
                return list;
        }
        return list;
    }

    private <T extends AbstractEntity> T getTempValue(Context context, int i, int userId){
        switch (i) {
            case 0:
               Category category = new Category();
               category.setId(-1);
               category.setName("Select category...");
               category.setIsDeleted(1);
               category.setUserId(userId);

               return (T) category;
            case 1:
                Priority priority = new Priority();
                priority.setId(-1);
                priority.setName("Select priority...");
                priority.setIsDeleted(1);
                priority.setUserId(userId);

                return (T) priority;
            case 2:
                Status status = new Status();
                status.setId(-1);
                status.setName("Select status...");
                status.setIsDeleted(1);
                status.setUserId(userId);

                return (T) status;
        }
        return  null;
    }
    //switch to another fragment
    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}