package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.oh.time4play.toimip_poistoFragmentDirections;

import java.sql.Connection;
import java.sql.SQLException;

public class toimip_poistoFragment extends Fragment {

    public toimip_poistoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toimip_poisto, container, false);

        String kayttajatunnus = toimip_poistoFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = toimip_poistoFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();
        String poistettava = toimip_poistoFragmentArgs.fromBundle(getArguments()).getPoistettavaToimipisteNimi();

        Button poista = view.findViewById(R.id.bt_PoistoKylla);
        Button palaa = view.findViewById(R.id.bt_PoistoTakaisinNappi);

        TextView poistettavanNimi = view.findViewById(R.id.tvPoistettavanNimi);

        poistettavanNimi.setText(poistettava);

        poista.setOnClickListener(e -> {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Connection connection = null;
                    try {
                        connection = Tietokantayhteys.yhdistaSystemTietokantaan();
                        Toimip_hallinta_kyselyt.poistaToimipiste(connection,poistettava);
                        connection.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            t1.start();
            try {
                t1.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

            com.oh.time4play.toimip_poistoFragmentDirections.ActionToimipPoistoFragmentToToimipHallintaFragment action = com.oh.time4play.toimip_poistoFragmentDirections.actionToimipPoistoFragmentToToimipHallintaFragment(kayttajatunnus,salasana);
            Navigation.findNavController(view).navigate(action);
        });

        palaa.setOnClickListener(e -> {
            com.oh.time4play.toimip_poistoFragmentDirections.ActionToimipPoistoFragmentToToimipHallintaFragment action = com.oh.time4play.toimip_poistoFragmentDirections.actionToimipPoistoFragmentToToimipHallintaFragment(kayttajatunnus,salasana);
            Navigation.findNavController(view).navigate(action);
        });

        return view;
    }
}