package com.oh.time4play;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PelivalineetListAdapter extends RecyclerView.Adapter<PelivalineetListAdapter.ViewHolder>{
    public static ArrayList<Pelivaline_muuttujat> localDataset;
    public PelivalineetListAdapter(ArrayList<Pelivaline_muuttujat> dataset) {localDataset=dataset;}

    View.OnClickListener pelivListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            CheckBox checkBox = v.findViewById(R.id.cbOsta_pelivaline_items);
            if (checkBox.isChecked()) { checkBox.setChecked(false);
            } else {
                checkBox.setChecked(true);
            }
            th_peliv_muutoksetListAdapter.localDataset.get(position).valittu = checkBox.isChecked();
        }
    };

    @NonNull
    @Override
    public PelivalineetListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_pelivalineet_items,parent,false);

        return new PelivalineetListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PelivalineetListAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(pelivListener);

        holder.textView.setText(localDataset.get(position).pelivalineNimi);
        holder.textView2.setText(localDataset.get(position).valineHinta);

    }

    @Override
    public int getItemCount() {return localDataset.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView textView;
        public final TextView textView2;
        public final ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.tv_pelivaline_pelivalineet);
            textView2 = (TextView) itemView.findViewById(R.id.tv_pelivaline_valineHinta);
            imageView = (ImageView) itemView.findViewById(R.id.iv_pelivaline_kuva);
        }
    }
}
