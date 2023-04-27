package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oh.time4play.toimip_muokkausFragmentDirections;

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

        Button tpVahvistaMuutos = view.findViewById(R.id.bt_tpMuokkausNappula);

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

        tpNimi.setHint(muokattavanTiedot.Nimi);
        tpKaupunki.setHint(muokattavanTiedot.Kaupunki);
        tpVastaava.setHint(muokattavanTiedot.ToimipisteVastaava);

        tpVahvistaMuutos.setOnClickListener(e ->{
            muokattavanTiedot.setNimi(tpNimi.getText().toString());
            muokattavanTiedot.setKaupunki(tpKaupunki.getText().toString());
            muokattavanTiedot.setToimipisteVastaava(tpVastaava.getText().toString());
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection connection = Tietokantayhteys.yhdistaTietokantaan(kirjautunutKayttaja,kirjautunutSalasana);
                        Toimip_hallinta_kyselyt.updateToimipiste(connection,muokattavanTiedot);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            t2.start();
            try {
                t2.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            com.oh.time4play.toimip_muokkausFragmentDirections.ActionToimipMuokkausFragmentToToimipHallintaFragment action = com.oh.time4play.toimip_muokkausFragmentDirections.actionToimipMuokkausFragmentToToimipHallintaFragment(kirjautunutKayttaja,kirjautunutSalasana);
            Navigation.findNavController(view).navigate(action);
        });

        return view;
    }
}