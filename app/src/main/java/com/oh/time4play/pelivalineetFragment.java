package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.oh.time4play.pelivalineetFragmentDirections;

import java.sql.SQLException;
import java.util.ArrayList;

public class pelivalineetFragment extends Fragment {

    private ArrayList<Pelivaline_muuttujat> itemArrayList;
    public static String valitutPelivalineet = "";

    public pelivalineetFragment() {super(R.layout.fragment_pelivalineet);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pelivalineet, container, false);

        //Haetaan navigaation actionista bundle joka sisältää käyttäjätunnuksen ja salasanan
        String kayttajatunnus = pelivalineetFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = pelivalineetFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();
        String valittuLaji = pelivalineetFragmentArgs.fromBundle(getArguments()).getValittuLaji();
        String valittuToimipiste = pelivalineetFragmentArgs.fromBundle(getArguments()).getValittuToimipiste();
        String valittuPvm = pelivalineetFragmentArgs.fromBundle(getArguments()).getValittuPVM();
        int valittuKentta = pelivalineetFragmentArgs.fromBundle(getArguments()).getValittuKentta();
        int valittuAika = pelivalineetFragmentArgs.fromBundle(getArguments()).getValittuKellonaika();
        String kentanHinta = pelivalineetFragmentArgs.fromBundle(getArguments()).getValitunKentanHinta();
        valitutPelivalineet = "";

        Button btSeuraava = view.findViewById(R.id.bt_Pelivaline_Seuraava);

        //RecycleView Toimipisteiden listaamiseen
        RecyclerView myRecycleView = view.findViewById(R.id.rv_pelivalineet);

        Thread t1 = new Thread(() -> {
            try {
                try {
                    itemArrayList = th_kyselyt.getLajinPelivalineet(Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus,salasana), valittuLaji);

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

        myRecycleView.setAdapter(new PelivalineetListAdapter(itemArrayList));
        myRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        btSeuraava.setOnClickListener(e -> {
            for (Pelivaline_muuttujat valine: PelivalineetListAdapter.localDataset) {
                if (valine.valittu) {
                    valitutPelivalineet += "*" + valine.pelivalineNimi + "^"+ valine.valineHinta + "€";
                    System.out.println("Lisätään siirrettäväksi rivi: " + valitutPelivalineet);
                }
            }
            if (valitutPelivalineet.length() == 0) {
                valitutPelivalineet = "-";
            }
            com.oh.time4play.pelivalineetFragmentDirections.ActionPelivalineetFragmentToMaksuikkunaFragment action = com.oh.time4play.pelivalineetFragmentDirections.actionPelivalineetFragmentToMaksuikkunaFragment(kayttajatunnus,salasana,valittuToimipiste,valittuPvm,valittuKentta,valittuAika,valitutPelivalineet,kentanHinta);
            Navigation.findNavController(view).navigate(action);
        });

        return view;
    }
}