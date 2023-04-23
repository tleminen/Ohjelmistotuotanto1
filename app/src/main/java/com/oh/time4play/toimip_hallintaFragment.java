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
import android.widget.TextView;

import java.sql.Connection;
import java.sql.SQLException;

//Luokan ohjelmointi aloitettu
public class toimip_hallintaFragment extends Fragment {

    public toimip_hallintaFragment() {super(R.layout.fragment_toimip_hallinta);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Haetaan navigaation actionista bundle joka sisältää käyttäjätunnuksen ja salasanan
        String kayttajatunnus = toimip_hallintaFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = toimip_hallintaFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();
        System.out.println("\t\t\t\t!!Tänne päästiin!!");

        RecyclerView rvItemList = view.findViewById(R.id.rwToimipisteidenHallinnointi);

        Connection connection = null;
        try {
            connection = Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus,salasana);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Toimip_hallintaMuuttujat[] dataset;
        try {
            dataset = Toimip_hallinta_kyselyt.getAllToimipisteet(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Tietokantayhteys.katkaiseYhteysTietokantaan();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rvItemList.setAdapter(new Toimip_hallintaListAdapter(dataset));
        rvItemList.setLayoutManager(new LinearLayoutManager(getContext()));

        super.onViewCreated(view, savedInstanceState);
    }
}