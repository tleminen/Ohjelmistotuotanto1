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

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * RaporttiFragment toteuttaa raportin hakemisen halutulta aikaväliltä tai kuluvalta päivältä
 * näkymässä myös avoimien laskujen hakeminen
 * @version 1.0
 */
public class Raportti extends Fragment {

    public String laskut;

    public String raportti;

    public Raportti() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_raportti, container, false);

        String kayttajatunnus = RaporttiArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = RaporttiArgs.fromBundle(getArguments()).getKirjautunutSalasana();

        EditText etalkupvm = view.findViewById(R.id.et_raporttiAsetaPVM);
        EditText etloppupvm = view.findViewById(R.id.et_raporttiLoppuPVM);

        Button btVahvista = view.findViewById(R.id.bt_raportti_Vahvista);
        Button btPaluu = view.findViewById(R.id.bt_raportti_Loppu);
        Button btAvoimet = view.findViewById(R.id.btRaportti_AvoimetLaskut);
        Button btTanaan = view.findViewById(R.id.btTamaPaivaRaportti);

        TextView tvRaporttiTeksti = view.findViewById(R.id.tv_raporttiTulostus);

        btTanaan.setOnClickListener(e -> {
            String tanaan = String.valueOf(LocalDate.now());
            System.out.println(tanaan);
            Thread t1 = new Thread(() -> {
                Connection connection;
                try {
                    connection = Tietokantayhteys.yhdistaSystemTietokantaan();
                    raportti = Raportti_kyselyt.getLocalVarausraportti(connection, kayttajatunnus, tanaan, tanaan);
                    Tietokantayhteys.katkaiseYhteysTietokantaan();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            t1.start();
            try {
                t1.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

            tvRaporttiTeksti.setText(raportti);
        });

        btVahvista.setOnClickListener(e->{
            String alkupvm = etalkupvm.getText().toString();
            String loppupvm = etloppupvm.getText().toString();

            Thread t1 = new Thread(() -> {
                Connection connection;
                try {
                    connection = Tietokantayhteys.yhdistaSystemTietokantaan();
                    raportti = Raportti_kyselyt.getLocalVarausraportti(connection, kayttajatunnus, alkupvm, loppupvm);
                    Tietokantayhteys.katkaiseYhteysTietokantaan();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            t1.start();
            try {
                t1.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

            tvRaporttiTeksti.setText(raportti);

        });

        btAvoimet.setOnClickListener(e -> {

            Thread t1 = new Thread(() -> {
                Connection connection;
                try {
                    connection = Tietokantayhteys.yhdistaSystemTietokantaan();
                    laskut = Raportti_kyselyt.getAvoimetLaskut(connection, kayttajatunnus);
                    Tietokantayhteys.katkaiseYhteysTietokantaan();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            t1.start();
            try {
                t1.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            tvRaporttiTeksti.setText(laskut);
        });

        btPaluu.setOnClickListener(e -> {
            RaporttiDirections.ActionRaporttiToToimipisteenHallintaFragment action = com.oh.time4play.RaporttiDirections.actionRaporttiToToimipisteenHallintaFragment(kayttajatunnus,salasana);
            Navigation.findNavController(view).navigate(action);
        });

        // Inflate the layout for this fragment
        return view;
    }
}