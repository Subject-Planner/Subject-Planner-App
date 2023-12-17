package com.demo.subjectplanner.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Grade;
import com.amplifyframework.datastore.generated.model.Subject;
import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.MainActivity;
import com.demo.subjectplanner.activity.SubjectDetailsActivity;

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

        TextView subjectDescTextView = holder.itemView.findViewById(R.id.textViewDescCards);

        Subject subject = subjects.get(position);



        subjectTitleTextView.setText(subject.getTitle());


        String subjectTitle = subjects.get(position).getTitle();
        String numberOfAbsents = "Number of Absents : " + String.valueOf(subjects.get(position).getNumberOfAbsents());



        Button btn = (Button) holder.itemView.findViewById(R.id.cardViewButton);
        btn.setOnClickListener(view -> {

            Intent goTOSubjectDetails = new Intent(callingActivity, SubjectDetailsActivity.class);
            goTOSubjectDetails.putExtra(MainActivity.SUBJECT_TITLE_TAG,  subjectTitle);
            goTOSubjectDetails.putExtra(MainActivity.NUMBER_OF_ABSENTS,  numberOfAbsents);
            callingActivity.startActivity(goTOSubjectDetails);
        });

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
