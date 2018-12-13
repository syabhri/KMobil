 package com.example.ozi.kmobil.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ozi.kmobil.Model.Review;
import com.example.ozi.kmobil.R;
import com.example.ozi.kmobil.ReviewEditActivity;

import java.util.List;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ReviewViewHolder> {
    List<Review> listReview;

    public MyReviewAdapter(List<Review> listReview) {
        this.listReview = listReview;
    }

    @Override
    public MyReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_review, parent, false);
        MyReviewAdapter.ReviewViewHolder mHolder = new MyReviewAdapter.ReviewViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(MyReviewAdapter.ReviewViewHolder holder, final int position) {

        holder.tvIdReview.setText(listReview.get(position).getId_review());
        holder.tvNama.setText(listReview.get(position).getNama_Katalog());
        holder.tvRating.setText("Rating : "+String.valueOf(listReview.get(position).getRating()));
        holder.tvIsiReview.setText(listReview.get(position).getDeskripsi());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent2 = new Intent(v.getContext(), ReviewEditActivity.class);
                mIntent2.putExtra("id_review", listReview.get(position).getId_review());
                mIntent2.putExtra("rating", String.valueOf(listReview.get(position).getRating()));
                mIntent2.putExtra("Deskripsi", listReview.get(position).getDeskripsi());

                v.getContext().startActivity(mIntent2);
            }
        });



    }

    @Override
    public int getItemCount() {
        return listReview.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdReview, tvNama, tvRating, tvIsiReview;
        Button editButton;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            tvIdReview = (TextView) itemView.findViewById(R.id.tvIdMyReview);
            tvNama = (TextView) itemView.findViewById(R.id.tvNama);
            tvRating = (TextView) itemView.findViewById(R.id.tvRating);
            tvIsiReview = (TextView) itemView.findViewById(R.id.tvDeskripsi);

        }
    }
}
