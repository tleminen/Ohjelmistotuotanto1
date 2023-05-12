package com.oh.time4play;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Toimip_hallintaListAdapter extends RecyclerView.Adapter<Toimip_hallintaListAdapter.ViewHolder> {
    public static ArrayList<Toimip_hallintaMuuttujat> localDataset;

    public Toimip_hallintaListAdapter(ArrayList<Toimip_hallintaMuuttujat> dataset) {localDataset=dataset;}

    View.OnClickListener Toimip_hallintaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            CheckBox checkBox = v.findViewById(R.id.cbToimip_HallintaItemValinta);
            checkBox.setChecked(true);
            toimip_hallintaFragment.valittuPositio = position;

            Toimip_hallintaListAdapter.localDataset.get(position).valittu = checkBox.isChecked();
            toimip_hallintaFragment.setValittuToimipiste(Toimip_hallintaListAdapter.localDataset.get(position).ToimipisteVastaava);
            System.out.println("valittu: "+ localDataset.get(position).ToimipisteVastaava);
            notifyDataSetChanged();
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
        holder.checkBox.setChecked(true);
        holder.checkBox.setClickable(false);
        if (toimip_hallintaFragment.valittuPositio != position) {
            holder.checkBox.setChecked(false);
        }
        holder.textView.setText(localDataset.get(position).Kaupunki);
        holder.textView2.setText(localDataset.get(position).Nimi);
    }

    @Override
    public int getItemCount() {return localDataset.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView textView;
        public final TextView textView2;
        public final CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.cbToimip_HallintaItemValinta);
            textView = (TextView) itemView.findViewById(R.id.twToimip_HallintaItemPaikka);
            textView2 = (TextView) itemView.findViewById(R.id.twToimip_HallintaItemNimi);
        }
    }
}
