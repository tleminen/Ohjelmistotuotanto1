package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.SQLException;

public class toimip_muokkausFragment extends Fragment {

    static Toimip_hallintaMuuttujat muokattavanTiedot;

    public toimip_muokkausFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Haetaan navigaation actionista bundle joka sisältää käyttäjätunnuksen, salasanan ja muokattavan toimipisteen ID:n
        String kirjautunutKayttaja = toimip_muokkausFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String kirjautunutSalasana = toimip_muokkausFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();
        int muokattavaToimipisteID = toimip_muokkausFragmentArgs.fromBundle(getArguments()).getMuokattavaToimipisteID();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toimip_muokkaus, container, false);

        TextView muokattavaTeksti = view.findViewById(R.id.tw_tpMuokkausTeksti);

        EditText tpNimi = view.findViewById(R.id.et_tpMuokkausNimi);
        EditText tpKaupunki = view.findViewById(R.id.et_tpMuokkausKaupunki);
        EditText tpVastaava = view.findViewById(R.id.et_tpMuokkausVastaava);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = Tietokantayhteys.yhdistaTietokantaan(kirjautunutKayttaja,kirjautunutSalasana);
                    muokattavanTiedot = Toimip_hallinta_kyselyt.getToimipiste(connection, muokattavaToimipisteID);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        return view;
    }
}