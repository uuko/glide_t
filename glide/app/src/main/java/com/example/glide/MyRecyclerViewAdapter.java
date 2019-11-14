package com.example.glide;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private ArrayList<String> url;
        private LayoutInflater mInflater;
        private ItemClickListener mClickListener;
    private Context context;
        // data is passed into the constructor
        MyRecyclerViewAdapter(Context context, ArrayList<String> url) {
            this.context=context;
            this.mInflater = LayoutInflater.from(context);
            this.url = url;
        }

        // inflates the cell layout from xml when needed
        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each cell
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.myTextView.setText(url.get(position));
            //holder.imageView.setImageURI(Uri.parse(url.get(position)));
            Picasso.with(context).load(Uri.parse(url.get(position))).into(holder.imageView);
        }

        // total number of cells
        @Override
        public int getItemCount() {
            return url.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView myTextView;
            ImageView imageView;
            ViewHolder(View itemView) {
                super(itemView);
                myTextView = itemView.findViewById(R.id.info_text);
                imageView=itemView.findViewById(R.id.photo_img);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        // convenience method for getting data at click position
        String getItem(int id) {
            return url.get(id);
        }

        // allows clicks events to be caught
        void setClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

        // parent activity will implement this method to respond to click events
        public interface ItemClickListener {
            void onItemClick(View view, int position);
        }
    }

