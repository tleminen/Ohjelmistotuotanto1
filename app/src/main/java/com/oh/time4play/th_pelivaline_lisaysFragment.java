package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.sql.Connection;
import java.sql.SQLException;

public class th_pelivaline_lisaysFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String kayttajatunnus = th_pelivaline_lisaysFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = th_pelivaline_lisaysFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();

        View view = inflater.inflate(R.layout.fragment_th_pelivaline_lisays, container, false);

        EditText valinenimi = view.findViewById(R.id.et_valineNimi_th_pelivaline_lisays);
        EditText valinehinta = view.findViewById(R.id.et_valineHinta_th_pelivaline_lisays);

        Button lisaapelivaline = view.findViewById(R.id.bt_lisaaPelivalinePainike_th_pelivaline_lisays);
        Button poistu = view.findViewById(R.id.bt_poistuPainike_th_pelivaline_lisays);

        RadioButton rbTennis = view.findViewById(R.id.rbTennis_lisays_pelivaline);
        RadioButton rbSulis = view.findViewById(R.id.rbSulkapallo_lisays_pelivaline);

        lisaapelivaline.setOnClickListener(e->{
            if (rbSulis.isChecked() | rbTennis.isChecked() && !valinehinta.getText().toString().equals("") && !valinenimi.getText().toString().equals("")) {
                String valittuLaji = "";
                if (rbSulis.isChecked()) {
                    valittuLaji = "Sulkapallo";
                } else if (rbTennis.isChecked()) {
                    valittuLaji = "Tennis";
                }
                String nimi = valinenimi.getText().toString();
                String hinta = valinehinta.getText().toString();
                Pelivaline_muuttujat lisattavaValine = new Pelivaline_muuttujat(nimi, hinta, valittuLaji);

                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Connection connection = Tietokantayhteys.yhdistaSystemTietokantaan();
                            th_kyselyt.setLisaaUusiPelivaline(connection, lisattavaValine);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                try {
                    t1.start();
                    t1.join();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                th_pelivaline_lisaysFragmentDirections.ActionThPelivalineLisaysFragmentToToimipisteenHallintaFragment action = com.oh.time4play.th_pelivaline_lisaysFragmentDirections.actionThPelivalineLisaysFragmentToToimipisteenHallintaFragment(kayttajatunnus,salasana);
                Navigation.findNavController(view).navigate(action);
            }
        });

        poistu.setOnClickListener(e->{
            th_pelivaline_lisaysFragmentDirections.ActionThPelivalineLisaysFragmentToToimipisteenHallintaFragment action = com.oh.time4play.th_pelivaline_lisaysFragmentDirections.actionThPelivalineLisaysFragmentToToimipisteenHallintaFragment(kayttajatunnus,salasana);
            Navigation.findNavController(view).navigate(action);
        });




        return view;
    }
}