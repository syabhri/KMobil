package com.example.ozi.kmobil.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ozi.kmobil.Model.Mobil;
import com.example.ozi.kmobil.Model.Review;
import com.example.ozi.kmobil.R;
import com.example.ozi.kmobil.Rest.ApiClient;

import java.util.List;

public class ReviewMobilAdapter extends RecyclerView.Adapter<ReviewMobilAdapter.ReviewViewHolder> {
    TextView tvNama, tvIsiReview;
    List<Review> listReview;

    public ReviewMobilAdapter(List<Review> listReview) {
        this.listReview = listReview;
    }

    @Override
    public ReviewMobilAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_review, parent, false);
        ReviewMobilAdapter.ReviewViewHolder mHolder = new ReviewMobilAdapter.ReviewViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, final int position) {

        holder.tvIdReview.setText(listReview.get(position).getId_review());
        holder.tvNama.setText(listReview.get(position).getNama_user());
        holder.tvRating.setText("Rating : "+String.valueOf(listReview.get(position).getRating()));
        holder.tvIsiReview.setText(listReview.get(position).getIsi_review());

        SharedPreferences handler = holder.itemView.getContext().getSharedPreferences("data_login", Context.MODE_PRIVATE);

        String id_user = handler.getString("id_user","");


    }

    @Override
    public int getItemCount() {
        return listReview.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdReview, tvNama, tvTanggal, tvRating, tvIsiReview;
        Button editButton;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            tvIdReview = (TextView) itemView.findViewById(R.id.tvIdMyReview);
            tvNama = (TextView) itemView.findViewById(R.id.tvNamaKatalog);
            tvRating = (TextView) itemView.findViewById(R.id.tvRating);
            tvIsiReview = (TextView) itemView.findViewById(R.id.tvIsiReview);

        }
    }
}
