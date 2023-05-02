package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.oh.time4play.th_kentta_lisaysFragmentDirections;

import java.sql.Connection;
import java.sql.SQLException;


public class th_kentta_lisaysFragment extends Fragment {


    public th_kentta_lisaysFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String kayttajatunnus = toimip_lisaysFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = toimip_lisaysFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_th_kentta_lisays, container, false);

        EditText keNimi = view.findViewById(R.id.etKentanNimi_kenttaLisa);
        EditText keLajitunnus = view.findViewById(R.id.etLajitunnus_kenttaLisa);
        EditText keHinta = view.findViewById(R.id.etTuntihinta_kenttaLisa);

        Button lisaaKentta = view.findViewById(R.id.btLisaaKentta);

        lisaaKentta.setOnClickListener(e -> {
            String nimi = keNimi.getText().toString();
            String lajitunnus = keLajitunnus.getText().toString();
            String hinta = keHinta.getText().toString();
            Kentta_Muuttujat lisattavaKe = new Kentta_Muuttujat(nimi,lajitunnus,hinta,kayttajatunnus);

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection connection = Tietokantayhteys.yhdistaSystemTietokantaan();
                        th_kyselyt.setLisaaUusiKentta(connection, lisattavaKe);
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
            com.oh.time4play.th_kentta_lisaysFragmentDirections.ActionThKenttaLisaysFragmentToToimipisteenHallintaFragment action = com.oh.time4play.th_kentta_lisaysFragmentDirections.actionThKenttaLisaysFragmentToToimipisteenHallintaFragment(kayttajatunnus,salasana);
            Navigation.findNavController(view).navigate(action);
        });


        return view;
    }
}