package com.oh.time4play;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class th_peliv_muutoksetListAdapter extends RecyclerView.Adapter<th_peliv_muutoksetListAdapter.ViewHolder> {
    public static ArrayList<Pelivaline_muuttujat> localDataset;
    public th_peliv_muutoksetListAdapter(ArrayList<Pelivaline_muuttujat> dataset) {localDataset=dataset;}

    View.OnClickListener peliv_muutoksetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            CheckBox checkBox = v.findViewById(R.id.cb_pelivMuutoksetValinta);
            if (checkBox.isChecked()) { checkBox.setChecked(false);
            } else {
                checkBox.setChecked(true);
            }
            th_peliv_muutoksetListAdapter.localDataset.get(position).valittu = checkBox.isChecked();

            System.out.println(position);
            th_peliv_muutoksetFragment.setValittuPelivaline(th_peliv_muutoksetListAdapter.localDataset.get(position).pelivalineID);
            System.out.println("valittu: "+ localDataset.get(position).pelivalineID);
        }
    };

    @NonNull
    @Override
    public th_peliv_muutoksetListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_th_peliv_muutokset_items,parent,false);

        return new th_peliv_muutoksetListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull th_peliv_muutoksetListAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(peliv_muutoksetListener);

        holder.checkBox.setClickable(false);

        holder.textView.setText(localDataset.get(position).pelivalineNimi);
    }

    @Override
    public int getItemCount() {return localDataset.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView textView;
        public final CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.cb_pelivMuutoksetValinta);

            textView = (TextView) itemView.findViewById(R.id.tv_pelivnimi_th_peliv_muutokset);
        }
    }
}
