package com.project.metacom.comments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.project.metacom.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataAdapterViewHolder> {
    public List<DataStructure> Dataset = new ArrayList<DataStructure>();
    private Context context;

    DataAdapter(Context context){
        this.context = context;
    }

    public Object getContext() {
        return context;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class DataAdapterViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView userImageView;
        public TextView nickTextView;
        public TextView creationDateTextView;
        public TextView contentTextView;
        public TextView likesTextView;
        public TextView dislikesTextView;

        public DataAdapterViewHolder(View itemView) {
            super(itemView);
            userImageView = itemView.findViewById(R.id.user_image_view);
            nickTextView = itemView.findViewById(R.id.user_name_text_view);
            creationDateTextView = itemView.findViewById(R.id.date_text_view);
            contentTextView = itemView.findViewById(R.id.commentary_text_view);
            likesTextView = itemView.findViewById(R.id.likes_text_view);
            dislikesTextView = itemView.findViewById(R.id.dislikes_text_view);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DataAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout)LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout_item, parent, false);
        DataAdapterViewHolder vh = new DataAdapterViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull DataAdapterViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        DataStructure comment = Dataset.get(position);

        holder.nickTextView.setText(comment.user_id);
        holder.creationDateTextView.setText(comment.time);
        holder.contentTextView.setText(comment.text);
        holder.likesTextView.setText(comment.likes);
        holder.dislikesTextView.setText(comment.dislikes);
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return Dataset.size();
    }
}