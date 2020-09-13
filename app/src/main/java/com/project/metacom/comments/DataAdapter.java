package com.project.metacom.comments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.neovisionaries.ws.client.WebSocket;
import com.project.metacom.R;
import com.project.metacom.data.Comment;
import com.project.metacom.data.User;
import com.project.metacom.fragment.user_profile_fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.project.metacom.config.me;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataAdapterViewHolder> {
    public List<Comment> CommentBase = new ArrayList<Comment>();
    //public List<User> UserBase = new ArrayList<User>();
    private activity context;

    DataAdapter(activity context){
        this.context = context;
    }

    public Object getContext() {
        return context;
    }

    public void like(String id) {
        Comment comment = search_comment_by_id(id);
        assert comment != null;
        try {
            Integer likes = Integer.parseInt(comment.likes);
            likes += 1;
            comment.likes = Integer.toString(likes);
        }
        catch (NumberFormatException e)
        {
            comment.likes = "0";
        }
    }

    public void dislike(String id) {
        Comment comment = search_comment_by_id(id);
        assert comment != null;
        try {
            Integer dislikes = Integer.parseInt(comment.dislikes);
            dislikes += 1;
            comment.dislikes = Integer.toString(dislikes);
        }
        catch (NumberFormatException e)
        {
            comment.dislikes = "0";
        }
    }

    private Comment search_comment_by_id(String id) {
        for (Comment comment : CommentBase) {
            if(comment.id.equals(id)){
                return comment;
            }
        }
        return null;
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
        //public TextView likesTextView;
        //public TextView dislikesTextView;
        public TextView likesDislikesTextView;
        public ImageButton commentaryLike;
        public ImageButton commentaryDislike;

        public DataAdapterViewHolder(View itemView) {
            super(itemView);
            userImageView = itemView.findViewById(R.id.user_image_view);
            nickTextView = itemView.findViewById(R.id.user_name_text_view);
            creationDateTextView = itemView.findViewById(R.id.date_text_view);
            contentTextView = itemView.findViewById(R.id.commentary_text_view);
            likesDislikesTextView = itemView.findViewById(R.id.likes_dislikes_text_view);
            //likesTextView = itemView.findViewById(R.id.likes_text_view);
            //dislikesTextView = itemView.findViewById(R.id.dislikes_text_view);
            commentaryLike  = itemView.findViewById(R.id.commentary_like);
            commentaryDislike  = itemView.findViewById(R.id.commentary_dislike);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DataAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout)LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout_item, parent, false);
        return new DataAdapterViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull DataAdapterViewHolder holder, int position) {
        try {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final Comment comment = CommentBase.get(position);

            if (comment.likes.equals("null")) comment.likes = "0";
            if (comment.dislikes.equals("null")) comment.dislikes = "0";

            holder.nickTextView.setText(comment.user.username);
            holder.creationDateTextView.setText(comment.time);
            holder.contentTextView.setText(comment.text);
            holder.likesDislikesTextView.setText(Integer.toString(Integer.parseInt(comment.likes) - Integer.parseInt(comment.dislikes)));
            holder.userImageView.setImageBitmap(comment.user.image);



            View.OnClickListener like_event = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.data_receiver_comment.ws.sendText("{\"action\": \"like\", \"post_id\": \"" + comment.id + "\", \"token\": \"" + me.token + "\"}");

                    AlphaAnimation animation = new AlphaAnimation(0f, 1.0f);
                    animation.setDuration(500);
                    v.startAnimation(animation);
                    //holder.commentaryLike.setColorFilter(Color.parseColor("#AE6118"));
                }
            };

            View.OnClickListener dislike_event = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.data_receiver_comment.ws.sendText("{\"action\": \"dislike\", \"post_id\": \"" + comment.id + "\", \"token\": \"" + me.token + "\"}");

                    AlphaAnimation animation = new AlphaAnimation(0f, 1.0f);
                    animation.setDuration(500);
                    v.startAnimation(animation);
                    //holder.commentaryLike.setColorFilter(Color.parseColor("#AE6118"));
                }
            };

            holder.commentaryLike.setOnClickListener(like_event);
            //holder.likesTextView.setOnClickListener(like_event);
            holder.commentaryDislike.setOnClickListener(dislike_event);
            //holder.dislikesTextView.setOnClickListener(dislike_event);

            holder.userImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("user_id", comment.user_id );

                    user_profile_fragment fragInfo = new user_profile_fragment();
                    fragInfo.setArguments(bundle);
                    FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.user_profile_conteiner, fragInfo);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    context.findViewById(R.id.user_profile_conteiner).setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception e){
            holder = null;
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return CommentBase.size();
    }
}