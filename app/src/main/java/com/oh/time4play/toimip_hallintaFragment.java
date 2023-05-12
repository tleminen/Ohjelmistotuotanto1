package com.oh.time4play;

import android.annotation.SuppressLint;
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


public class toimip_hallintaFragment extends Fragment {
    public ArrayList<Toimip_hallintaMuuttujat> itemArrayList; //

    public static String valittuToimipiste;
    public static int valittuPositio = -1;

    public static String getValittuToimipiste() {
        return valittuToimipiste;
    }

    public static void setValittuToimipiste(String valittuToimipiste) {
        toimip_hallintaFragment.valittuToimipiste = valittuToimipiste;
    }

    public toimip_hallintaFragment() {super(R.layout.fragment_toimip_hallinta);}

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Haetaan navigaation actionista bundle joka sisältää käyttäjätunnuksen ja salasanan
        String kayttajatunnus = toimip_hallintaFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = toimip_hallintaFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();

        Button btLisaaTp = view.findViewById(R.id.btLisaaToimipiste);

        Button btMuokkaaTp = view.findViewById(R.id.btMuokkaaToimipiste);

        Button btPoistaTp = view.findViewById(R.id.btPoistaToimipiste);

        Button btPoistuTp = view.findViewById(R.id.btPoistuToimip_hallinta);

        Button btAsiakashallinta = view.findViewById(R.id.bt_AsiakasHallinta_Toimip_Hallinta);

        //RecycleView Toimipisteiden listaamiseen
        RecyclerView myRecycleView = view.findViewById(R.id.rwToimipisteidenHallinnointi);

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

        myRecycleView.setAdapter(new Toimip_hallintaListAdapter(itemArrayList));
        myRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Siirtymät nappuloiden mukaan
        btAsiakashallinta.setOnClickListener(e -> {
            com.oh.time4play.toimip_hallintaFragmentDirections.ActionToimipHallintaFragmentToAsiakasHallinta action = com.oh.time4play.toimip_hallintaFragmentDirections.actionToimipHallintaFragmentToAsiakasHallinta(kayttajatunnus,salasana);
            Navigation.findNavController(view).navigate(action);
        });

        btLisaaTp.setOnClickListener(e -> {
            com.oh.time4play.toimip_hallintaFragmentDirections.ActionToimipHallintaFragmentToToimipLisaysFragment action = com.oh.time4play.toimip_hallintaFragmentDirections.actionToimipHallintaFragmentToToimipLisaysFragment(kayttajatunnus,salasana);
            Navigation.findNavController(view).navigate(action);
        });

        btMuokkaaTp.setOnClickListener(e -> {
            if (valittuToimipiste != null) {
                com.oh.time4play.toimip_hallintaFragmentDirections.ActionToimipHallintaFragmentToToimipMuokkausFragment action = com.oh.time4play.toimip_hallintaFragmentDirections.actionToimipHallintaFragmentToToimipMuokkausFragment(kayttajatunnus, salasana, valittuToimipiste);
                System.out.println("seuraavaan fragmenttiin menee: " + getValittuToimipiste());
                valittuToimipiste = null;
                Navigation.findNavController(view).navigate(action);
            }
        });

        btPoistaTp.setOnClickListener(e -> {
            if (valittuToimipiste != null) {
                com.oh.time4play.toimip_hallintaFragmentDirections.ActionToimipHallintaFragmentToToimipPoistoFragment action = com.oh.time4play.toimip_hallintaFragmentDirections.actionToimipHallintaFragmentToToimipPoistoFragment(valittuToimipiste, kayttajatunnus, salasana);
                valittuToimipiste = null;
                Navigation.findNavController(view).navigate(action);
            }
        });

        btPoistuTp.setOnClickListener(e -> {
            com.oh.time4play.toimip_hallintaFragmentDirections.ActionToimipHallintaFragmentToLoppuikkunaFragment action = com.oh.time4play.toimip_hallintaFragmentDirections.actionToimipHallintaFragmentToLoppuikkunaFragment("Toimipisteen Hallinnoitsija",true);
            Navigation.findNavController(view).navigate(action);
        });

        super.onViewCreated(view, savedInstanceState);
    }
}