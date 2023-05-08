package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;


public class th_pelivaline_muokkausFragment extends Fragment {

    Pelivaline_muuttujat pelivaline;



    public th_pelivaline_muokkausFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_th_pelivaline_muokkaus, container, false);

        String kayttajatunnus = th_pelivaline_muokkausFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = th_pelivaline_muokkausFragmentArgs.fromBundle(getArguments()).getKirjautunuSalasana();
        int valittuPelivaline = th_pelivaline_muokkausFragmentArgs.fromBundle(getArguments()).getValittuPelivalineID();


        EditText pelivalineNimi = view.findViewById(R.id.et_pelivNimi_th_pelivaline_muokkaus);
        EditText pelivalineHinta = view.findViewById(R.id.et_pelivHinta_th_pelivaline_muokkaus);

        Button btPoistaPelivaline = view.findViewById(R.id.bt_poistaPeliv_th_pelivaline_muokkaus);
        Button btVahvista = view.findViewById(R.id.bt_vahvista_th_pelivaline_muokkaus);
        Button btPaluu = view.findViewById(R.id.bt_paluu_th_pelivalinemuokkaus);

        Thread t1 = new Thread(() -> {
            try {
                try {
                    pelivaline = th_kyselyt.getPelivaline(Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus, salasana), valittuPelivaline);
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

        pelivalineNimi.setText(pelivaline.pelivalineNimi);
        pelivalineHinta.setText(pelivaline.valineHinta);

        btPoistaPelivaline.setOnClickListener(e -> {
            Thread t2 = new Thread(() -> {
                try {
                    try {
                        th_kyselyt.poistaPelivaline(Tietokantayhteys.yhdistaSystemTietokantaan(), valittuPelivaline );
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
            th_kentta_muokkausFragmentDirections.ActionThKenttaMuokkausFragment2ToToimipisteenHallintaFragment action = th_kentta_muokkausFragmentDirections.actionThKenttaMuokkausFragment2ToToimipisteenHallintaFragment(kayttajatunnus, salasana);
            Navigation.findNavController(view).navigate(action);
        });

        btVahvista.setOnClickListener(e -> {

            pelivaline.pelivalineNimi = pelivalineNimi.getText().toString();
            pelivaline.valineHinta = pelivalineHinta.getText().toString();

            Thread t3 = new Thread(() -> {
                try {
                    try {
                        th_kyselyt.updatePelivaline(Tietokantayhteys.yhdistaSystemTietokantaan(), pelivaline);
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
            th_pelivaline_muokkausFragmentDirections.ActionThPelivalineMuokkausFragmentToToimipisteenHallintaFragment action = com.oh.time4play.th_pelivaline_muokkausFragmentDirections.actionThPelivalineMuokkausFragmentToToimipisteenHallintaFragment(kayttajatunnus, salasana);
            Navigation.findNavController(view).navigate(action);
        });

        btPaluu.setOnClickListener(e-> {
            th_pelivaline_muokkausFragmentDirections.ActionThPelivalineMuokkausFragmentToToimipisteenHallintaFragment action = com.oh.time4play.th_pelivaline_muokkausFragmentDirections.actionThPelivalineMuokkausFragmentToToimipisteenHallintaFragment(kayttajatunnus, salasana);
            Navigation.findNavController(view).navigate(action);
        });


        return view;
    }
}