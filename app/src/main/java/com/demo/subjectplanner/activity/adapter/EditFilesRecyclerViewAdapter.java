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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.File;
import com.amplifyframework.datastore.generated.model.Record;
import com.demo.subjectplanner.R;

import java.util.List;

public class EditFilesRecyclerViewAdapter extends RecyclerView.Adapter<EditFilesRecyclerViewAdapter.ViewHolder> {

    private List<File> filesList;
    private Context context;

    public EditFilesRecyclerViewAdapter(List<File> filesList, Context context) {
        this.filesList = filesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.editsubjectfile_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String FileTitle = filesList.get(position).getName();

        // Static image
        holder.textViewTitle.setText(FileTitle);

        // Set click listener for the card
        holder.cardView.setOnClickListener(v -> openLink((filesList.get(position).getLink())));
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        CardView cardView;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.FileTitleTextView);
            cardView = itemView.findViewById(R.id.editFilesCard);
            deleteButton = itemView.findViewById(R.id.deleteFileButton);

            // Set click listener for the delete button
            deleteButton.setOnClickListener(v -> showDeleteWarningDialog());
        }

        private void showDeleteWarningDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete File");
            builder.setMessage("Are you sure you want to delete this file?");
            builder.setPositiveButton("Delete", (dialog, which) -> {
                int position = getAdapterPosition();
                Log.i("getAdapterPosition : ",""+ position );
                if (position != RecyclerView.NO_POSITION) {

                    Amplify.API.mutate(
                            ModelMutation.delete(filesList.get(position)),
                            response -> Log.i("MyAmplifyApp", "Deleted File: " + response.getData().getId()),
                            error -> Log.e("MyAmplifyApp", "Delete failed", error)
                    );
                    filesList.remove(position);
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





