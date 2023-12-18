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

import com.amplifyframework.datastore.generated.model.DaysEnum;
import com.amplifyframework.datastore.generated.model.Grade;
import com.amplifyframework.datastore.generated.model.Subject;
import com.demo.subjectplanner.R;
import com.demo.subjectplanner.activity.MainActivity;
import com.demo.subjectplanner.activity.SubjectDetailsActivity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

      public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
          TextView subjectTitleTextView = holder.itemView.findViewById(R.id.textTitle);
          TextView subjectDescTextView = holder.itemView.findViewById(R.id.textViewDescCards);

          Subject subject = subjects.get(position);

          subjectTitleTextView.setText(subject.getTitle());

          String subjectId= subjects.get(position).getId();
          List<String> subjectDays = DaysEnum.toStringList(subject.getDays());
          StringBuilder result = new StringBuilder("Days: ");
          for (String str : subjectDays) {
              result.append(str.substring(0, 1).toUpperCase() + str.substring(1, 2).toLowerCase() + " ");
          }
          result.append("\n Time: " + timeModify(subject.getStartDate().toString().substring(45, 50)));
          subjectDescTextView.setText(result.toString());
          View subjectView = holder.itemView;
          subjectView.setOnClickListener(view -> {
              Intent goToSubDetails = new Intent(callingActivity, SubjectDetailsActivity.class);
              goToSubDetails.putExtra(MainActivity.SUBJECT_TITLE_TAG, subjectId);
              goToSubDetails.putExtra(MainActivity.SUBJECT_ID_TAG, subjectId);



              callingActivity.startActivity(goToSubDetails);
          });

      }
    private String timeModify(String inputTimeString){  //increase time by 3 hours when getting it from aws
        // Parse the input time string
        LocalTime inputTime = LocalTime.parse(inputTimeString, DateTimeFormatter.ofPattern("HH:mm"));

        // Subtract 3 hours
        LocalTime earlierTime = inputTime.plusHours(3);

        // Format the result back into a string
        String resultTimeString = earlierTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        return resultTimeString;
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
