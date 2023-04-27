package com.oh.time4play;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Toimip_hallintaListAdapter extends RecyclerView.Adapter<Toimip_hallintaListAdapter.ViewHolder> {
    Toimip_hallintaMuuttujat[] localDataset;
    Bundle bundle = new Bundle();

    public Toimip_hallintaListAdapter(Toimip_hallintaMuuttujat[] dataset) {localDataset=dataset;}

    View.OnClickListener Toimip_hallintaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            toimip_hallintaFragment.setValittuToimipiste(localDataset[position].ToimipisteID);
        }
    };

    @NonNull
    @Override
    public Toimip_hallintaListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_toimip_hallinta_item,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Toimip_hallintaListAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(Toimip_hallintaListener);

        holder.textView.setText(localDataset[position].Kaupunki);
        holder.textView2.setText(localDataset[position].Nimi);
    }

    @Override
    public int getItemCount() {return localDataset.length;}

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView textView;
        public final TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.twToimip_HallintaItemPaikka);
            textView2 = (TextView) itemView.findViewById(R.id.twToimip_HallintaItemNimi);
        }
    }
}
