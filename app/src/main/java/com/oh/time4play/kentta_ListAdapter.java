package com.oh.time4play;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class kentta_ListAdapter extends RecyclerView.Adapter<kentta_ListAdapter.ViewHolder>  {

    public static ArrayList<Kentta_Muuttujat> localDataset;
    public kentta_ListAdapter(ArrayList<Kentta_Muuttujat> dataset) {localDataset = dataset;}

    View.OnClickListener Kentta_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            System.out.println(position);
            th_muokattava_kenttaFragment.setValittuKentta(kentta_ListAdapter.localDataset.get(position).kenttaID);
            System.out.println("valittu: "+ localDataset.get(position).kenttaID);
        }
    };

    @NonNull
    @Override
    public kentta_ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_kentta_item,parent,false);

        return new kentta_ListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull kentta_ListAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(Kentta_Listener);

        //TÄNNE JOTENKI PITÄIS SAADA SE KELLONAIKOJEN VÄRITYS ja se onnistunee ton localDatasetin kautta kun annetaan sinne kaikille kellon arvot onko varausta
        holder.t0.setBackgroundColor(Integer.parseInt(localDataset.get(position).lajitunnus));
        holder.t1.setText(localDataset.get(position).nimi);
    }

    @Override
    public int getItemCount() {return localDataset.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView t0;
        public final TextView t1;
        public final TextView t2;
        public final TextView t3;
        public final TextView t4;
        public final TextView t5;
        public final TextView t6;
        public final TextView t7;
        public final TextView t8;
        public final TextView t9;
        public final TextView t10;
        public final TextView t11;
        public final TextView t12;
        public final TextView t13;
        public final TextView t14;
        public final TextView t15;
        public final TextView t16;
        public final TextView t17;
        public final TextView t18;
        public final TextView t19;
        public final TextView t20;
        public final TextView t21;
        public final TextView t22;
        public final TextView t23;

        public ViewHolder(View itemView) {
            super(itemView);

            t0 = (TextView) itemView.findViewById(R.id.kellonaikaKLO0);
            t1 = (TextView) itemView.findViewById(R.id.kellonaikaKLO1);
            t2 = (TextView) itemView.findViewById(R.id.kellonaikaKLO2);
            t3 = (TextView) itemView.findViewById(R.id.kellonaikaKLO3);
            t4 = (TextView) itemView.findViewById(R.id.kellonaikaKLO4);
            t5 = (TextView) itemView.findViewById(R.id.kellonaikaKLO5);
            t6 = (TextView) itemView.findViewById(R.id.kellonaikaKLO6);
            t7 = (TextView) itemView.findViewById(R.id.kellonaikaKLO7);
            t8 = (TextView) itemView.findViewById(R.id.kellonaikaKLO8);
            t9 = (TextView) itemView.findViewById(R.id.kellonaikaKLO9);
            t10 = (TextView) itemView.findViewById(R.id.kellonaikaKLO10);
            t11 = (TextView) itemView.findViewById(R.id.kellonaikaKLO11);
            t12 = (TextView) itemView.findViewById(R.id.kellonaikaKLO12);
            t13 = (TextView) itemView.findViewById(R.id.kentta_itemsKLO13);
            t14 = (TextView) itemView.findViewById(R.id.kellonaikaKLO14);
            t15 = (TextView) itemView.findViewById(R.id.kellonaikaKLO15);
            t16 = (TextView) itemView.findViewById(R.id.kellonaikaKLO16);
            t17 = (TextView) itemView.findViewById(R.id.kellonaikaKLO17);
            t18 = (TextView) itemView.findViewById(R.id.kellonaikaKLO18);
            t19 = (TextView) itemView.findViewById(R.id.kellonaikaKLO19);
            t20 = (TextView) itemView.findViewById(R.id.kellonaikaKLO20);
            t21 = (TextView) itemView.findViewById(R.id.kellonaikaKLO21);
            t22 = (TextView) itemView.findViewById(R.id.kellonaikaKLO22);
            t23 = (TextView) itemView.findViewById(R.id.kellonaikaKLO23);
        }
    }
}
