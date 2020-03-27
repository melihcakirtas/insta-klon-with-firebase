package com.melih.instaklon;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedReyclerAdapter extends RecyclerView.Adapter<FeedReyclerAdapter.PostHolder> {
    private ArrayList<String> useremaillist;
    private ArrayList<String> usercommentlist;
    private ArrayList<String> userimage;

    public FeedReyclerAdapter(ArrayList<String> useremaillist, ArrayList<String> usercommentlist, ArrayList<String> userimage) {
        this.useremaillist = useremaillist;
        this.usercommentlist = usercommentlist;
        this.userimage = userimage;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_lay,parent,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.useremailtext.setText(usercommentlist.get(position));
        holder.usercomment.setText(usercommentlist.get(position));
        Picasso.get().load(userimage.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return useremaillist.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView useremailtext;
        TextView usercomment;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView4);
            useremailtext = itemView.findViewById(R.id.textView);
            usercomment = itemView.findViewById(R.id.textView3);
        }
    }
}
