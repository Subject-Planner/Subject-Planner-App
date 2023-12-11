package com.demo.subjectplanner.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.SubjectDetailsActivity;
import com.demo.subjectplanner.activity.model.Subject;

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
        View subjectView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_view, parent , false);
        return new SubjectViewHolder(subjectView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        TextView subjectTitleTextView = holder.itemView.findViewById(R.id.textTitle);
        TextView subjectDescTextView = holder.itemView.findViewById(R.id.textDesc);

        Subject subject = subjects.get(position);

        // Set the data from the Subject object
        subjectTitleTextView.setText(subject.getTitle());
        // You can set other data as needed

        // For example, if you have a description field in your Subject class:
        // subjectDescTextView.setText(subject.getDescription());
   

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
