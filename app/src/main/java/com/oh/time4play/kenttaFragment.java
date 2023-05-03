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

import com.oh.time4play.kenttaFragmentDirections;

import java.sql.SQLException;
import java.util.ArrayList;

public class kenttaFragment extends Fragment {

    public kenttaFragment() {
        super(R.layout.fragment_kentta);
    }

    public ArrayList<Kentta_Muuttujat> itemArrayList; //

    private static int valittuKentta = -1;

    public static int getValittuKentta() {
        return valittuKentta;
    }

    public static void setValittuKentta(int valinta) {
        kenttaFragment.valittuKentta = valinta;
        System.out.println(valinta);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Haetaan navigaation actionista bundle joka sisältää käyttäjätunnuksen ja salasanan
        String kayttajatunnus = kenttaFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = kenttaFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();
        String valittuToimipiste = kenttaFragmentArgs.fromBundle(getArguments()).getValittuToimipiste();
        String valittuPVM = kenttaFragmentArgs.fromBundle(getArguments()).getValittuPVM();

        Button btSeuraava = view.findViewById(R.id.bt_kenttaVahvistanappi);

        //RecycleView kenttien listaamiseen
        RecyclerView myRecycleView = view.findViewById(R.id.rv_kenttaValinnat);

        Thread t1 = new Thread(() -> {
            try {
                try {
                    itemArrayList = th_kyselyt.getAllKentat(Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus, salasana), kayttajatunnus);

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

        myRecycleView.setAdapter(new kentta_ListAdapter(itemArrayList));
        myRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        btSeuraava.setOnClickListener(e -> {
            if (valittuKentta != -1) {
                com.oh.time4play.kenttaFragmentDirections.ActionKenttaFragmentToKellonaikaValintaFragment action = com.oh.time4play.kenttaFragmentDirections.actionKenttaFragmentToKellonaikaValintaFragment(kayttajatunnus,salasana,valittuToimipiste,valittuPVM,valittuKentta);
                Navigation.findNavController(view).navigate(action);
            }
        });

    }
}