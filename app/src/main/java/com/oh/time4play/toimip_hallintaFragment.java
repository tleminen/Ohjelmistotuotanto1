package com.oh.time4play;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Connection;

//Luokan ohjelmointi aloitettu
public class toimip_hallintaFragment extends Fragment {

    public toimip_hallintaFragment() {super(R.layout.fragment_toimip_hallinta);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rvItemList = view.findViewById(R.id.rwToimipisteidenHallinnointi);

        Connection connection = Tietokantayhteys.yhdistaTietokantaan("ToimipisteidenHallinnoitsija","salasana");
        Toimip_hallintaMuuttujat[] dataset = Toimip_hallinta_kyselyt.getAllToimipisteet();
        Tietokantayhteys.katkaiseYhteysTietokantaan();
        rvItemList.setAdapter(new Toimip_hallintaListAdapter(dataset));
        rvItemList.setLayoutManager(new LinearLayoutManager(getContext()));

        super.onViewCreated(view, savedInstanceState);
    }
}