package com.oh.time4play;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class th_muokattava_kentta_ListAdapter extends RecyclerView.Adapter<th_muokattava_kentta_ListAdapter.ViewHolder> {
    public static ArrayList<Kentta_Muuttujat> localDataset;

    public th_muokattava_kentta_ListAdapter(ArrayList<Kentta_Muuttujat> dataset) {localDataset=dataset;}

    View.OnClickListener Kentta_muokkaus_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            CheckBox checkBox = v.findViewById(R.id.cb_thMuokattavaKenttaItem);
            checkBox.setChecked(true);
            th_muokattava_kenttaFragment.valittupositio = position;

            System.out.println(position);
            th_muokattava_kentta_ListAdapter.localDataset.get(position).valittu = checkBox.isChecked();
            th_muokattava_kenttaFragment.setValittuKentta(th_muokattava_kentta_ListAdapter.localDataset.get(position).kenttaID);
            System.out.println("valittu: "+ localDataset.get(position).kenttaID);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public th_muokattava_kentta_ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_th_muokattava_kentta_item,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull th_muokattava_kentta_ListAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(Kentta_muokkaus_Listener);

        holder.checkBox.setClickable(false);
        if (th_muokattava_kenttaFragment.valittupositio != position) {
            holder.checkBox.setChecked(false);
        }

        holder.textView.setText(localDataset.get(position).lajitunnus);
        holder.textView2.setText(localDataset.get(position).nimi);
    }

    @Override
    public int getItemCount() {return localDataset.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public final TextView textView;
        public final TextView textView2;
        public final CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.cb_thMuokattavaKenttaItem);
            textView = (TextView) itemView.findViewById(R.id.tv_thMuokattavaKenttaItemLaji);
            textView2 = (TextView) itemView.findViewById(R.id.tv_thMuokattavaKenttaItemNimi);
        }
    }
}
