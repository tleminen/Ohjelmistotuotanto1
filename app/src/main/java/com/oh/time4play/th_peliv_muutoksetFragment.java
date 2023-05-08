package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.oh.time4play.th_peliv_muutoksetFragmentDirections;

import java.sql.SQLException;
import java.util.ArrayList;

public class th_peliv_muutoksetFragment extends Fragment {

    public ArrayList<Pelivaline_muuttujat> itemArrayList; //

    private static int valittuPelivaline = -1;

    public static int getValittuPelivaline() {
        return valittuPelivaline;
    }

    public static void setValittuPelivaline(int valinta) {
        th_peliv_muutoksetFragment.valittuPelivaline = valinta;
        System.out.println(valinta);
    }


    public th_peliv_muutoksetFragment() {super(R.layout.fragment_th_peliv_muutokset);}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_th_peliv_muutokset, container, false);

        //Haetaan navigaation actionista bundle joka sisältää käyttäjätunnuksen ja salasanan
        String kayttajatunnus = th_peliv_muutoksetFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = th_peliv_muutoksetFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();
        String valittuLaji = th_peliv_muutoksetFragmentArgs.fromBundle(getArguments()).getValittuLaji();

        Button btSeuraava = view.findViewById(R.id.btSeuraava_th_peliv_muutokset);

        //RecycleView Toimipisteiden listaamiseen
        RecyclerView myRecycleView = view.findViewById(R.id.rv_th_peliv_muutokset);

        Thread t1 = new Thread(() -> {
            try {
                try {
                    itemArrayList = th_kyselyt.getLajinPelivalineet(Tietokantayhteys.yhdistaSystemTietokantaan(), valittuLaji);

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

        myRecycleView.setAdapter(new th_peliv_muutoksetListAdapter(itemArrayList));
        myRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        btSeuraava.setOnClickListener(e -> {
            if (valittuPelivaline != -1) {
                com.oh.time4play.th_peliv_muutoksetFragmentDirections.ActionThPelivMuutoksetFragmentToThPelivalineMuokkausFragment action = com.oh.time4play.th_peliv_muutoksetFragmentDirections.actionThPelivMuutoksetFragmentToThPelivalineMuokkausFragment(kayttajatunnus,salasana,valittuPelivaline);
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }
}