package com.demo.subjectplanner.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.model.Subject;

import java.util.Date;
import java.util.List;

public class HomePageRecyclerViewAdapter extends RecyclerView.Adapter<HomePageRecyclerViewAdapter.SubjectViewHolder> {

    List<Subject> subjects;
    Context callingActivity;

    public HomePageRecyclerViewAdapter(List<Subject> subjects , Context callingActivity) {
        this.subjects = subjects;
        this.callingActivity = callingActivity;
    }
    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View subjectFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_subject, parent , false);
        return new SubjectViewHolder(subjectFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        TextView subjectFragmentTextView =holder.itemView.findViewById(R.id.textViewFragment);
        String title = subjects.get(position).getTitle();
//        String image = subjects.get(position).getImages();
//        String records = subjects.get(position).getRecords();
//        String files = subjects.get(position).getFiles();
//        String notes = subjects.get(position).getNotes();
//        Date startTime = subjects.get(position).getStartTime();
//        Date endTime = subjects.get(position).getEndTime();
//        Date startDate = subjects.get(position).getStartDate();
//        Date endDate = subjects.get(position).getEndDate();
//        Integer grades = subjects.get(position).getGrades();
//        Integer numberOfAbsents = subjects.get(position).getNumberOfAbsents();

        subjectFragmentTextView.setText(title);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
