package com.example.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.Assignment;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private List<Assignment> mListAssignment;

    public AssignmentAdapter(List<Assignment> mListAssignment) {
        this.mListAssignment = mListAssignment;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context =parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment, parent, false);

        AssignmentViewHolder assignmentViewHolder = new AssignmentViewHolder(view);

        return assignmentViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {

        Assignment assignment =mListAssignment.get(position);

        holder.AssignmentNo.setText(assignment.getId());

    }

    @Override
    public int getItemCount() {
        if (mListAssignment != null){
            return mListAssignment.size();
        }
        return 0;
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder{

        private TextView AssignmentNo, AssignmentCourseName, AssignmentClassName, AssignmentTrainerName, AssignmentRegistrationCode;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);

            AssignmentNo =itemView.findViewById(R.id.assignment_no);
            AssignmentCourseName =itemView.findViewById(R.id.assignment_course_name);
            AssignmentClassName =itemView.findViewById(R.id.assignment_class_name);
            AssignmentTrainerName =itemView.findViewById(R.id.assignment_trainer_name);
            AssignmentRegistrationCode =itemView.findViewById(R.id.assignment_registration_code);

        }
    }
}
