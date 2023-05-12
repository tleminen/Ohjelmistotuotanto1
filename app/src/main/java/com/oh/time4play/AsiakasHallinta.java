package com.oh.time4play;

import android.graphics.Color;
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
import java.util.ArrayList;

public class AsiakasHallinta extends Fragment {

    int muutettu = 0;
    ArrayList<Asiakas_Muuttujat> asiakasLista;

    public AsiakasHallinta() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_asiakas_hallinta, container, false);
        String kayttajatunnus = AsiakasHallintaArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = AsiakasHallintaArgs.fromBundle(getArguments()).getKirjautunutSalasana();

        Button btPoistaAsiakas = view.findViewById(R.id.bt_PoistaAsiakas_AsiakasHallinta);
        Button btPaluu = view.findViewById(R.id.bt_Paluu_AsiakasHallinta);

        EditText etPoistettavaAsiakas = view.findViewById(R.id.et_AsHallinta_poistettavaAsiakas2);

        TextView tvAsiakaslista = view.findViewById(R.id.tvAsiakasLista_AsiakasHallinta);
        TextView tvHuomio = view.findViewById(R.id.tvHuomioAsiakasHallinta2);

        tvHuomio.setVisibility(View.INVISIBLE);

        Thread t1 = new Thread(() -> {
            try {
                try {
                    asiakasLista = Toimip_hallinta_kyselyt.getAllAsiakkaat(Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus,salasana));
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

        String printti = "Asiakkaiden tiedot\n-------------\n";
        for (Asiakas_Muuttujat asiakas: asiakasLista) {
            printti += "Sähköpostiosoite: " + asiakas.getEmail() + "\nRooli: " + asiakas.getRooli() + "\t\tNimi: " + asiakas.getAsiakasNimi() + "\nOsoite: " + asiakas.getOsoite() + "\n-------------\n";
        }

        tvAsiakaslista.setText(printti);

        btPaluu.setOnClickListener(e -> {
            com.oh.time4play.AsiakasHallintaDirections.ActionAsiakasHallintaToToimipHallintaFragment action = com.oh.time4play.AsiakasHallintaDirections.actionAsiakasHallintaToToimipHallintaFragment(kayttajatunnus,salasana);
            Navigation.findNavController(view).navigate(action);
        });

        btPoistaAsiakas.setOnClickListener(e -> {
            tvHuomio.setVisibility(View.INVISIBLE);
            if (!etPoistettavaAsiakas.getText().toString().equals("")) {
                String poistettavaAsiakas = etPoistettavaAsiakas.getText().toString();

                Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Connection connection = Tietokantayhteys.yhdistaSystemTietokantaan();
                            muutettu = Toimip_hallinta_kyselyt.poistaAsiakas(connection, poistettavaAsiakas);
                            Tietokantayhteys.katkaiseYhteysTietokantaan();
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
                if (muutettu ==1 ){
                    tvHuomio.setText("Asiakas: " + poistettavaAsiakas + " poistettu!");
                    tvHuomio.setTextColor(Color.GREEN);
                } else {
                    tvHuomio.setText("Asiakasta: " + poistettavaAsiakas + " ei poistettu!\nTarkasta syöttämäsi tiedot");
                    tvHuomio.setTextColor(Color.RED);
                }

                tvHuomio.setVisibility(View.VISIBLE);
                etPoistettavaAsiakas.setText(null);
            }
        });

        return view;
    }
}