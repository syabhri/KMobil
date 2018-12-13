package com.example.ozi.kmobil.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ozi.kmobil.MobilDetailActivity;
import com.example.ozi.kmobil.Model.Mobil;
import com.example.ozi.kmobil.R;
import com.example.ozi.kmobil.Rest.ApiClient;

import java.util.List;

public class MobilListAdapter extends RecyclerView.Adapter<MobilListAdapter.MobilViewHolder> {
    List<Mobil> listMobil;

    public MobilListAdapter(List<Mobil> listMobil) {
        this.listMobil = listMobil;
    }

    @Override
    public MobilListAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mobil, pa rent, false);
        MobilListAdapter.MobilViewHolder mHolder = new MobilListAdapter.MobilViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(MobilListAdapter.MobilViewHolder holder, final int position) {

        holder.tvIdMobil.setText(listMobil.get(position).getId_mobil());
        holder.tvNama.setText(listMobil.get(position).getNama_mobil());
        holder.tvMerekMobil.setText(listMobil.get(position).getMerek_mobil());
        final String photo_mobil_url = ApiClient.BASE_URL + "uploads/" + listMobil.get(position).getPhoto_mobil();
        if (listMobil.get(position).getPhoto_mobil() != null ){
            Glide.with(holder.itemView.getContext()).load(photo_mobil_url)
                    .into(holder.mPhotoURL);
        } else {
            Glide.with(holder.itemView.getContext()).load(R.drawable.default_mobil).into(holder
                    .mPhotoURL);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MobilDetailActivity.class);
                intent.putExtra("id_mobil", listMobil.get(position).getId_mobil());
                intent.putExtra("nama_mobil", listMobil.get(position).getNama_mobil());
                intent.putExtra("merek_mobil", listMobil.get(position).getMerek_mobil());
                intent.putExtra("photo_mobil_url", photo_mobil_url);
                intent.putExtra("photo_mobil", listMobil.get(position).getPhoto_Mobil());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMobil.size();
    }

    public class MobilViewHolder extends RecyclerView.ViewHolder {
        ImageView mPhotoURL;
        TextView tvIdKatalog, tvNama;

        public MobilViewHolder(View itemView) {
            super(itemView);
            mPhotoURL = (ImageView) itemView.findViewById(R.id.mobilPhoto);
            tvIdKatalog = (TextView) itemView.findViewById(R.id.tvIdKatalog);
            tvNama = (TextView) itemView.findViewById(R.id.tvNama);
         }
    }
}
