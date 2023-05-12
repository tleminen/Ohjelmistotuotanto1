package com.oh.time4play;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


public class ToimipFragment extends Fragment {

    public ArrayList<Toimip_hallintaMuuttujat> itemArrayList; //

    public static String valittuToimipiste = "ei";
    public static int valittupositio = -1;

    public static String getValittuToimipiste() {
        return valittuToimipiste;
    }

    public static void setValittuToimipiste(String valittu) {
        ToimipFragment.valittuToimipiste = valittu;

    }

    public ToimipFragment() {super(R.layout.fragment_toimip);}

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Haetaan navigaation actionista bundle joka sisältää käyttäjätunnuksen ja salasanan
        String kayttajatunnus = toimip_hallintaFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = toimip_hallintaFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();

        Button btVahvista = view.findViewById(R.id.btVahvista_toimipFrag);

        //RecycleView Toimipisteiden listaamiseen
        RecyclerView myRecycleView = view.findViewById(R.id.rwToimiP);

        Thread t1 = new Thread(() -> {
            try {
                try {
                    itemArrayList = Toimip_hallinta_kyselyt.getAllToimipisteet(Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus,salasana));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Tietokantayhteys.katkaiseYhteysTietokantaan();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        myRecycleView.setAdapter(new Toimip_ListAdapter(itemArrayList));
        myRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        btVahvista.setOnClickListener(e -> {
            if (!Objects.equals(valittuToimipiste, "ei")) {
            com.oh.time4play.ToimipFragmentDirections.ActionToimipFragmentToPvmFragment action = com.oh.time4play.ToimipFragmentDirections.actionToimipFragmentToPvmFragment(kayttajatunnus,salasana,ToimipFragment.valittuToimipiste);
            Navigation.findNavController(view).navigate(action); }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}