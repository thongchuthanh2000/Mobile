package com.notemanagementsystem.fragment;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notemanagementsystem.AppDatabase;
import com.notemanagementsystem.adapter.NoteAdapter;
import com.notemanagementsystem.R;
import com.notemanagementsystem.entity.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public FloatingActionButton fab_add_note;
    public RecyclerView rcvNote;
    public NoteAdapter noteAdapter;
    public List<Note> mListNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        fab_add_note = view.findViewById(R.id.fab_add_note);
        fab_add_note.setOnClickListener(this);

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
        mListNote = new ArrayList<>();

        mListNote = AppDatabase.getAppDatabase(view.getContext()).noteDAO().getListNote();

        noteAdapter.setData(mListNote);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvNote.setLayoutManager(linearLayoutManager);
        rcvNote.setAdapter(noteAdapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab_add_note){
            openDialog(Gravity.CENTER, v.getContext());
        }
    }

    private void openDialog(int gravity, Context context) {
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

        EditText et_name_note = dialog.findViewById(R.id.et_name_note);
        Button btn_add = dialog.findViewById(R.id.btn_add);
        Button btn_close = dialog.findViewById(R.id.btn_close);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_note = et_name_note.getText().toString().trim();

                if(TextUtils.isEmpty(name_note)){
                    return;
                }

                Note note = new Note(name_note);
                AppDatabase.getAppDatabase(v.getContext()).noteDAO().insertNote(note);

                Toast.makeText(v.getContext(), "Add note successfully!", Toast.LENGTH_SHORT).show();

                //AppDatabase.getAppDatabase(v.getContext()).noteDAO().getListNote();

                //reload frm
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new NoteFragment()).addToBackStack(null).commit();

                dialog.cancel();
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void clickUpdateNote(View v, Note note){
        openDialogEdit(Gravity.CENTER, v.getContext(), note);
    }

    private void openDialogEdit(int gravity, Context context, Note note){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_editnote);

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

        EditText et_name_note1 = dialog.findViewById(R.id.et_name_note1);
        Button btn_update = dialog.findViewById(R.id.btn_update);
        Button btn_close1 = dialog.findViewById(R.id.btn_close1);

        if(note != null){
            et_name_note1.setText(note.getName());
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_note = et_name_note1.getText().toString().trim();

                if(TextUtils.isEmpty(name_note)){
                    return;
                }

                note.setName(name_note);
                AppDatabase.getAppDatabase(v.getContext()).noteDAO().updateNote(note);

                Toast.makeText(v.getContext(), "Update note successfully!", Toast.LENGTH_SHORT).show();

                //reload frm
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new NoteFragment()).addToBackStack(null).commit();

                dialog.cancel();
            }
        });

        btn_close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void clickDeleteNote(View v, Note note){
        new AlertDialog.Builder(v.getContext())
                .setTitle("Confirm")
                .setMessage("Are you sure to delete this note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppDatabase.getAppDatabase(v.getContext()).noteDAO().deleteNote(note);
                        Toast.makeText(v.getContext(), "Update note successfully!", Toast.LENGTH_SHORT).show();

                        //reload frm
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new NoteFragment()).addToBackStack(null).commit();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}