package com.oh.time4play;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class th_kentta_muokkaus_ListAdapter extends RecyclerView.Adapter<th_kentta_muokkaus_ListAdapter.ViewHolder> {
    public static ArrayList<Kentta_Muuttujat> localDataset;

    public th_kentta_muokkaus_ListAdapter(ArrayList<Kentta_Muuttujat> dataset) {localDataset=dataset;}

    View.OnClickListener Kentta_muokkaus_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            System.out.println(position);
            th_muokattava_kenttaFragment.setValittuKentta(th_kentta_muokkaus_ListAdapter.localDataset.get(position).kenttaID);
            System.out.println("valittu: "+ localDataset.get(position).kenttaID);
        }
    };

    @NonNull
    @Override
    public th_kentta_muokkaus_ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_th_muokattava_kentta_item,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull th_kentta_muokkaus_ListAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(Kentta_muokkaus_Listener);

        holder.textView.setText(localDataset.get(position).lajitunnus);
        holder.textView2.setText(localDataset.get(position).nimi);
    }

    @Override
    public int getItemCount() {return localDataset.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView textView;
        public final TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.tv_thMuokattavaKenttaItemLaji);
            textView2 = (TextView) itemView.findViewById(R.id.tv_thMuokattavaKenttaItemNimi);
        }
    }
}
