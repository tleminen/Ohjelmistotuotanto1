package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;


public class th_kentta_muokkausFragment extends Fragment {

    Kentta_Muuttujat kentta;

    public th_kentta_muokkausFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_th_kentta_muokkaus, container, false);

        String kayttajatunnus = th_kentta_muokkausFragmentArgs.fromBundle(getArguments()).getKirjautunuKayttaja();
        String salasana = th_kentta_muokkausFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();
        int valittukentta = th_kentta_muokkausFragmentArgs.fromBundle(getArguments()).getValittuKenttaID();

        EditText et_lajitunnus = view.findViewById(R.id.et_thKenttaMuokkausLaji);
        EditText tuntihinta = view.findViewById(R.id.et_thKenttaMuokkausTuntihinta);
        EditText kentannimi = view.findViewById(R.id.et_thKenttaMuokkausNimi);

        Button btPoista = view.findViewById(R.id.bt_thKenttaMuokkausPoista);
        Button btVahvista = view.findViewById(R.id.bt_thKenttaMuokkausVahvista);

        Thread t1 = new Thread(() -> {
            try {
                try {
                    kentta = th_kyselyt.getKentta(Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus, salasana), valittukentta);
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
        et_lajitunnus.setText(kentta.lajitunnus);
        tuntihinta.setText(kentta.kentanHinta);
        kentannimi.setText(kentta.nimi);

        btPoista.setOnClickListener(e -> {
            Thread t2 = new Thread(() -> {
                try {
                    try {
                        th_kyselyt.poistaKentta(Tietokantayhteys.yhdistaSystemTietokantaan(), valittukentta);
                    } catch (SQLException e2) {
                        throw new RuntimeException(e2);
                    }
                    try {
                        Tietokantayhteys.katkaiseYhteysTietokantaan();
                    } catch (SQLException e2) {
                        throw new RuntimeException(e2);
                    }
                } catch (RuntimeException e2) {
                    throw new RuntimeException(e2);
                }
            });
            t2.start();
            try {
                t2.join();
            } catch (InterruptedException e2) {
                throw new RuntimeException(e2);
            }
        });

        btVahvista.setOnClickListener(e -> {
            Thread t3 = new Thread(() -> {
                try {
                    try {
                        th_kyselyt.updateKentta(Tietokantayhteys.yhdistaSystemTietokantaan(), kentta);
                    } catch (SQLException e3) {
                        throw new RuntimeException(e3);
                    }
                    try {
                        Tietokantayhteys.katkaiseYhteysTietokantaan();
                    } catch (SQLException e3) {
                        throw new RuntimeException(e3);
                    }
                } catch (RuntimeException e3) {
                    throw new RuntimeException(e3);
                }
            });
            t3.start();
            try {
                t3.join();
            } catch (InterruptedException e3) {
                throw new RuntimeException(e3);
            }
        });

        return view;
    }
}