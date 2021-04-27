package com.example.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.adapter.AssignmentAdapter;
import com.example.project.model.Assignment;

import java.util.ArrayList;

public class AssignmentFragment extends Fragment implements View.OnClickListener{

    public AssignmentFragment(){

    }

    private ArrayList<Assignment> listAssignment;
    private RecyclerView recyclerView;
    private AssignmentAdapter assignmentAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_assignment, container, false);

        recyclerView = view.findViewById(R.id.rcv_assignment);
        listAssignment = new ArrayList<>();
        createModelList();

        assignmentAdapter = new AssignmentAdapter(listAssignment);

        recyclerView.setAdapter(assignmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    private void createModelList(){
        for(int i=0;i<5;i++){
            listAssignment.add(new Assignment(1,"java","a1","kai","a123"));
        }
    }

    @Override
    public void onClick(View v) {

    }
}