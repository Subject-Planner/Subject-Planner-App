package com.demo.subjectplanner.activity.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Grade;
import com.demo.subjectplanner.R;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {

    private List<Grade> gradeList;
    private Context context;

    public GradeAdapter(List<Grade> gradeList, Context context) {
        this.gradeList = gradeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grade_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Grade gradeEntity = gradeList.get(position);

        // Set the values from the Grade entity to the TextViews
        holder.textViewTerm.setText(gradeEntity.getTerm());
        holder.textViewWeight.setText(String.valueOf(gradeEntity.getWeight()));
        // Add other relevant data to your ViewHolder views
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Customize the views based on your grade_layout.xml file
        TextView textViewTerm;
        TextView textViewWeight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTerm = itemView.findViewById(R.id.textViewTerm);
            textViewWeight = itemView.findViewById(R.id.textViewWeight);
            // Add other relevant views based on your grade_layout.xml file
        }
    }
}
