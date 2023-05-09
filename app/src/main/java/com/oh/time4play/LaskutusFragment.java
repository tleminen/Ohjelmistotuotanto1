package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.oh.time4play.LaskutusFragmentDirections;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class LaskutusFragment extends Fragment {
    ArrayList<LaskuMuuttujat> laskuta = new ArrayList<>();
    ArrayList<LaskuMuuttujat> laskutaPosti = new ArrayList<>();

    public LaskutusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_laskutus, container, false);

        String kayttajatunnus = LaskutusFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = LaskutusFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();

        Button btPaluu = view.findViewById(R.id.bt_Paluu_Laskutus);
        Button btVahvistaLaskutetuiksi = view.findViewById(R.id.bt_Laskutus_Laskutettu);

        TextView tvPrintti = view.findViewById(R.id.tvLaskuttamattomat);

        //Ensin sähköpostitse laskutettavat

        Thread t1 = new Thread(() -> {
            Connection connection;
            try {
                connection = Tietokantayhteys.yhdistaSystemTietokantaan();
                laskuta = Raportti_kyselyt.getLocalSahkopostiLaskut(connection, kayttajatunnus);
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

        String printti = "\tSähköpostitse laskutettavat:\n";
        for (LaskuMuuttujat lasku: laskuta) {
            printti += "\n---------------------\n" + lasku.getVarauksenAjankohta() + "\nKentta: " + lasku.getKentanNimi() + "\nSähköpostiosoite: " + lasku.getAsiakkaanEmail() + lasku.getValitutLisapalvelut() + "Loppusumma: " + lasku.getLaskutettavaSumma() + "€\n";
        }

        //Seuraavaksi postitse laskutettavat
        Thread t2 = new Thread(() -> {
            Connection connection;
            try {
                connection = Tietokantayhteys.yhdistaSystemTietokantaan();
                laskutaPosti = Raportti_kyselyt.getLocalPostiLaskut(connection, kayttajatunnus);
                laskutaPosti = Raportti_kyselyt.addAsiakkaanTiedot(connection,laskutaPosti);
                Tietokantayhteys.katkaiseYhteysTietokantaan();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        t2.start();
        try {
            t2.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        printti += "\n\n\tPostitse laskutettavat:\n";
        for (LaskuMuuttujat lasku: laskutaPosti) {
            printti += "\n---------------------\n" + lasku.getVarauksenAjankohta() + "\nKentta: " + lasku.getKentanNimi() + "\nAsiakkaan nimi: " + lasku.getAsiakkaanNimi() + "\nSähköpostiosoite: " + lasku.getAsiakkaanEmail() + "\nOsoite: " + lasku.getAsiakkaanOsoite() + lasku.getValitutLisapalvelut() + "Loppusumma: " + lasku.getLaskutettavaSumma() + "€\n";
        }
        tvPrintti.setText(printti);

        //Lisää tähän maksujen muokkaus
        btVahvistaLaskutetuiksi.setOnClickListener(e -> {
            Thread t3 = new Thread(() -> {
                Connection connection;
                try {
                    connection = Tietokantayhteys.yhdistaSystemTietokantaan();
                    Raportti_kyselyt.setLaskutettu(connection, laskuta);
                    Raportti_kyselyt.setLaskutettu(connection, laskutaPosti);
                    Tietokantayhteys.katkaiseYhteysTietokantaan();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            t3.start();
            try {
                t3.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            com.oh.time4play.LaskutusFragmentDirections.ActionLaskutusFragmentToToimipisteenHallintaFragment action = com.oh.time4play.LaskutusFragmentDirections.actionLaskutusFragmentToToimipisteenHallintaFragment(kayttajatunnus,salasana);
            Navigation.findNavController(view).navigate(action);
        });

        btPaluu.setOnClickListener(e -> {
            com.oh.time4play.LaskutusFragmentDirections.ActionLaskutusFragmentToToimipisteenHallintaFragment action = com.oh.time4play.LaskutusFragmentDirections.actionLaskutusFragmentToToimipisteenHallintaFragment(kayttajatunnus,salasana);
            Navigation.findNavController(view).navigate(action);
        });
        return view;
    }
}