package com.oh.time4play;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Toimip_ListAdapter toteuttaa ToimipFragment RecycleViewin adapterin
 */
public class Toimip_ListAdapter extends RecyclerView.Adapter<Toimip_ListAdapter.ViewHolder> {
    private static ArrayList<Toimip_hallintaMuuttujat> localDataset;

    public Toimip_ListAdapter(ArrayList<Toimip_hallintaMuuttujat> dataset) {localDataset=dataset;}

    View.OnClickListener Toimip_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            CheckBox checkBox = v.findViewById(R.id.cb_Toimip_itemValinta);
           checkBox.setChecked(true);
           ToimipFragment.valittupositio = position;

            Toimip_ListAdapter.localDataset.get(position).valittu = checkBox.isChecked();
            System.out.println(position);
            ToimipFragment.setValittuToimipiste(localDataset.get(position).ToimipisteVastaava);
            System.out.println("valittu: "+ localDataset.get(position).ToimipisteVastaava);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public Toimip_ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_toimip_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Toimip_ListAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(Toimip_Listener);

        holder.checkBox.setChecked(true);
        holder.checkBox.setClickable(false);
        if (ToimipFragment.valittupositio != position) {
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

            checkBox = (CheckBox) itemView.findViewById(R.id.cb_Toimip_itemValinta);
            textView = (TextView) itemView.findViewById(R.id.twToimip_ItemPaikkaKu);
            textView2 = (TextView) itemView.findViewById(R.id.twToimip_ItemNimi);
        }
    }
}
