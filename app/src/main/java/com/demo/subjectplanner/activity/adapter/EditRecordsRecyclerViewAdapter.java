package com.demo.subjectplanner.activity.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Record;
import com.demo.subjectplanner.R;

import java.util.List;

public class EditRecordsRecyclerViewAdapter extends RecyclerView.Adapter<EditRecordsRecyclerViewAdapter.ViewHolder> {

    private List<Record> recordsList;
    private Context context;

    public EditRecordsRecyclerViewAdapter(List<Record> recordsList, Context context) {
        this.recordsList = recordsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.editsubjectrecord_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String recordTitle = recordsList.get(position).getName();

        // Static image
        holder.textViewTitle.setText(recordTitle);

        // Set click listener for the card
        holder.cardView.setOnClickListener(v -> Toast.makeText(context, recordTitle, Toast.LENGTH_LONG).show());
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        CardView cardView;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.recordTitleTextView);
            cardView = itemView.findViewById(R.id.editRecordsCard);
            deleteButton = itemView.findViewById(R.id.deleteRecordButton);

            // Set click listener for the delete button
            deleteButton.setOnClickListener(v -> showDeleteWarningDialog());
        }

        private void showDeleteWarningDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Video");
            builder.setMessage("Are you sure you want to delete this Video?");
            builder.setPositiveButton("Delete", (dialog, which) -> {
                int position = getAdapterPosition();
                Log.i("getAdapterPosition : ",""+ position );
                if (position != RecyclerView.NO_POSITION) {

                    Amplify.API.mutate(
                            ModelMutation.delete(recordsList.get(position)),
                            response -> Log.i("MyAmplifyApp", "Deleted record: " + response.getData().getId()),
                            error -> Log.e("MyAmplifyApp", "Delete failed", error)
                    );
                    recordsList.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();

                }
                dialog.dismiss();
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    private void openLink(String link) {
        // Open the link in a web browser
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        context.startActivity(intent);
    }
}





