package com.oh.time4play;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class kentta_ListAdapter extends RecyclerView.Adapter<kentta_ListAdapter.ViewHolder>  {

    public static ArrayList<Kentta_Muuttujat> localDataset;
    public kentta_ListAdapter(ArrayList<Kentta_Muuttujat> dataset) {localDataset = dataset;}

    private int variValinta(int aika) {
        if (aika == 0) {
            return 0xFF00FF00;
        } else {
            return 0xFF883200;
        }
    }

    View.OnClickListener Kentta_Listener = new View.OnClickListener() {
        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();

            kenttaFragment.valittuPositio = position;

            System.out.println(position);
            kenttaFragment.setValittuKentta(kentta_ListAdapter.localDataset.get(position).kenttaID);
            kenttaFragment.setValitunKentanHinta(kentta_ListAdapter.localDataset.get(position).kentanHinta);
            System.out.println("valittu: "+ localDataset.get(position).kenttaID);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public kentta_ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_kentta_item,parent,false);

        return new kentta_ListAdapter.ViewHolder(view);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull kentta_ListAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(Kentta_Listener);
        holder.tKenttaHinta.setText(localDataset.get(position).kentanHinta + "€");
        holder.tKenttaNimi.setText(localDataset.get(position).nimi);

        holder.linearLayout.setBackgroundColor(R.color.vihrea_valittu);
        if (kenttaFragment.valittuPositio != position) {
            holder.linearLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.t0.setBackgroundColor(variValinta(localDataset.get(position).getKlo00()));
        holder.t1.setBackgroundColor(variValinta(localDataset.get(position).getKlo01()));
        holder.t2.setBackgroundColor(variValinta(localDataset.get(position).getKlo02()));
        holder.t3.setBackgroundColor(variValinta(localDataset.get(position).getKlo03()));
        holder.t4.setBackgroundColor(variValinta(localDataset.get(position).getKlo04()));
        holder.t5.setBackgroundColor(variValinta(localDataset.get(position).getKlo05()));
        holder.t6.setBackgroundColor(variValinta(localDataset.get(position).getKlo06()));
        holder.t7.setBackgroundColor(variValinta(localDataset.get(position).getKlo07()));
        holder.t8.setBackgroundColor(variValinta(localDataset.get(position).getKlo08()));
        holder.t9.setBackgroundColor(variValinta(localDataset.get(position).getKlo09()));
        holder.t10.setBackgroundColor(variValinta(localDataset.get(position).getKlo10()));
        holder.t11.setBackgroundColor(variValinta(localDataset.get(position).getKlo11()));
        holder.t12.setBackgroundColor(variValinta(localDataset.get(position).getKlo12()));
        holder.t13.setBackgroundColor(variValinta(localDataset.get(position).getKlo13()));
        holder.t14.setBackgroundColor(variValinta(localDataset.get(position).getKlo14()));
        holder.t15.setBackgroundColor(variValinta(localDataset.get(position).getKlo15()));
        holder.t16.setBackgroundColor(variValinta(localDataset.get(position).getKlo16()));
        holder.t17.setBackgroundColor(variValinta(localDataset.get(position).getKlo17()));
        holder.t18.setBackgroundColor(variValinta(localDataset.get(position).getKlo18()));
        holder.t19.setBackgroundColor(variValinta(localDataset.get(position).getKlo19()));
        holder.t20.setBackgroundColor(variValinta(localDataset.get(position).getKlo20()));
        holder.t21.setBackgroundColor(variValinta(localDataset.get(position).getKlo21()));
        holder.t22.setBackgroundColor(variValinta(localDataset.get(position).getKlo22()));
        holder.t23.setBackgroundColor(variValinta(localDataset.get(position).getKlo23()));
    }

    @Override
    public int getItemCount() {return localDataset.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView tKenttaNimi;
        public final TextView tKenttaHinta;
        public final LinearLayout linearLayout;
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

            linearLayout = (LinearLayout) itemView.findViewById(R.id.ll_kentta);
            tKenttaNimi = (TextView) itemView.findViewById(R.id.tv_kenttaItem_KenttaNimi);
            tKenttaHinta = (TextView) itemView.findViewById(R.id.tv_KenttaItem_Hinta);
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
