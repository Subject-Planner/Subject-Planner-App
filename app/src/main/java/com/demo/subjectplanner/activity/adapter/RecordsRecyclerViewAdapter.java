package com.demo.subjectplanner.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Record;
import com.demo.subjectplanner.R;

import java.util.List;

public class RecordsRecyclerViewAdapter extends RecyclerView.Adapter<RecordsRecyclerViewAdapter.ViewHolder> {

    private List<Record> recordsList;
    private Context context;

    public RecordsRecyclerViewAdapter(List<Record> recordsList, Context context) {
        this.recordsList = recordsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subjectrecord_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String recordTitle = recordsList.get(position).getName();

        // Static image
        holder.textViewTitle.setText(recordTitle);

        // Set click listener for the card
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(recordsList.get(position).getLink());
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.recordTitleTextView);
            cardView = itemView.findViewById(R.id.editRecordsCard);
        }
    }

    private void openLink(String link) {
        // Open the link in a web browser
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        context.startActivity(intent);
    }
}





