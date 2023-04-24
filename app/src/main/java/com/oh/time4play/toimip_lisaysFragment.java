package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.oh.time4play.toimip_lisaysFragmentDirections;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Aloitettu ohjelmointi, pitää tarkastella vielä se lisäyskysely, eli siis tehdä se ja katsoa, että toimii
 */
public class toimip_lisaysFragment extends Fragment {

    public toimip_lisaysFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Haetaan navigaation actionista bundle joka sisältää käyttäjätunnuksen ja salasanan
        String kayttajatunnus = toimip_lisaysFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = toimip_lisaysFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toimip_lisays, container, false);

        EditText tpNimi = view.findViewById(R.id.et_tpLisaysNimi);
        EditText tpKaupunki = view.findViewById(R.id.et_tpLisaysKaupunki);
        EditText tpVastaava = view.findViewById(R.id.et_tpLisaysVastaava);

        Button lisaatp = view.findViewById(R.id.bt_tpLisaysToimipiste);

        lisaatp.setOnClickListener(e -> {
            String nimi = tpNimi.getText().toString();
            String kaupunki = tpKaupunki.getText().toString();
            String vastaava = tpVastaava.getText().toString();
            Toimip_hallintaMuuttujat lisattavaTp = new Toimip_hallintaMuuttujat(nimi,kaupunki,vastaava);

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection connection = Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus,salasana);
                        Toimip_hallinta_kyselyt.setLisaaUusiToimipiste(connection, lisattavaTp);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            try {
                t1.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            com.oh.time4play.toimip_lisaysFragmentDirections.ActionToimipLisaysFragmentToToimipHallintaFragment action = com.oh.time4play.toimip_lisaysFragmentDirections.actionToimipLisaysFragmentToToimipHallintaFragment(kayttajatunnus,salasana);
            Navigation.findNavController(view).navigate(action);
        });

        return view;
    }
}