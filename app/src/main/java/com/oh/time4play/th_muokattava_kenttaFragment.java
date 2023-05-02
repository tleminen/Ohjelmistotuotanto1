package com.oh.time4play;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.oh.time4play.th_muokattava_kenttaFragmentDirections;

import java.sql.SQLException;
import java.util.ArrayList;


public class th_muokattava_kenttaFragment extends Fragment {

    public ArrayList<Kentta_Muuttujat> itemArrayList; //

    public static int valittuKentta;

    public static int getValittuKentta() {
        return valittuKentta;
    }

    public static void setValittuKentta(int valinta) {
        th_muokattava_kenttaFragment.valittuKentta = valinta;
        System.out.println(valinta);
    }

    public th_muokattava_kenttaFragment() {super(R.layout.fragment_th_muokattava_kentta);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Haetaan navigaation actionista bundle joka sisältää käyttäjätunnuksen ja salasanan
        String kayttajatunnus = toimip_hallintaFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = toimip_hallintaFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();

        Button seuraava = view.findViewById(R.id.bt_seuraava_thKenttaMuok);

        //RecycleView Toimipisteiden listaamiseen
        RecyclerView myRecycleView = view.findViewById(R.id.rv_thMuokattavaKentta);

        //tasta
        Thread t1 = new Thread(() -> {
            try {
                try {
                    itemArrayList = th_kyselyt.getAllKentat(Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus,salasana), kayttajatunnus);

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


        myRecycleView.setAdapter(new th_kentta_muokkaus_ListAdapter(itemArrayList));
        myRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        seuraava.setOnClickListener(e -> {
            com.oh.time4play.th_muokattava_kenttaFragmentDirections.ActionThMuokattavaKenttaFragmentToThKenttaMuokkausFragment2 action = com.oh.time4play.th_muokattava_kenttaFragmentDirections.actionThMuokattavaKenttaFragmentToThKenttaMuokkausFragment2(kayttajatunnus,salasana,valittuKentta);
            System.out.println("seuraavaan fragmenttiin menee: " + getValittuKentta());
            Navigation.findNavController(view).navigate(action);
        });

        super.onViewCreated(view, savedInstanceState);
    }
}