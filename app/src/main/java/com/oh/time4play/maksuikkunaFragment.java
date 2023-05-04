package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.sql.SQLException;


public class maksuikkunaFragment extends Fragment {

    Asiakas_Muuttujat asiakas;

    private String kayttajatunnus;
    private String salasana;
    private String valittuToimipiste;
    private String valittuPVM;
    private int valittuKentta;
    private int valittuAika;
    private int valittuLaskutyyppi;
    private boolean varausOnnistui;
    private String valitutLisapalvelut;
    private String kenttaHinta;
    private int lisapalveluTotHinta;

    public maksuikkunaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        kayttajatunnus = maksuikkunaFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        salasana = maksuikkunaFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();
        valittuToimipiste = maksuikkunaFragmentArgs.fromBundle(getArguments()).getValittuToimipiste();
        valittuPVM = maksuikkunaFragmentArgs.fromBundle(getArguments()).getValittuPVM();
        valittuKentta = maksuikkunaFragmentArgs.fromBundle(getArguments()).getValittuKentta();
        valittuAika = maksuikkunaFragmentArgs.fromBundle(getArguments()).getValittuKellonaika();
        valitutLisapalvelut = maksuikkunaFragmentArgs.fromBundle(getArguments()).getValitutLisapalvelut();
        kenttaHinta = maksuikkunaFragmentArgs.fromBundle(getArguments()).getValitunKentanHinta();

        View view = inflater.inflate(R.layout.fragment_maksuikkuna, container, false);

        RadioButton rbSahkopostiLasku = view.findViewById(R.id.rb_sahkopostiValinta_maksuikkuna);
        RadioButton rbPaperilasku = view.findViewById(R.id.rb_paperilaskuValinta_maksuikkuna);

        TextView tvSumma = view.findViewById(R.id.tv_summa_maksuikkuna);
        TextView tvEmailOsoite = view.findViewById(R.id.tv_sahkopostiAsiakkaan_maksuikkuna);

        EditText etNimi = view.findViewById(R.id.pt_nimiAsiakkaan_maksuikkuna);
        EditText etOsoite = view.findViewById(R.id.pt_osoiteAsiakkaan_maksuikkuna);

        Button btVahvista = view.findViewById(R.id.bt_vahvistaPainike_maksuikkuna);
        Button btVahvistaJaTallenna = view.findViewById(R.id.bt_vahvistaNappulaUudetTiedot_maksuikkuna);

        //Haetaan asiakkaan tiedot
        Thread t1 = new Thread(() -> {
            try {
                try {
                    asiakas = Maksun_Kyselyt.getAsiakas(Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus,salasana), kayttajatunnus);

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

        //Asetetaan asiakkaan tiedot näkyviin
        etNimi.setText(asiakas.getAsiakasNimi());
        etOsoite.setText(asiakas.getOsoite());

        //Asetetaan kokonaissumma näkyville

        puraLisapalvelut(valitutLisapalvelut);
        tvSumma.setText(laskeKokonaisSumma());
        tvEmailOsoite.setText(kayttajatunnus);


        btVahvista.setOnClickListener(e -> {
            if (rbPaperilasku.isSelected() | rbSahkopostiLasku.isSelected()) {
                if (rbPaperilasku.isSelected()) {
                    valittuLaskutyyppi = 1;
                } else if (rbSahkopostiLasku.isSelected()) {
                    valittuLaskutyyppi = 2;
                }
                Thread t2 = new Thread(() -> {
                    try {
                        try {
                            varausOnnistui = Maksun_Kyselyt.teeVaraus(Tietokantayhteys.yhdistaSystemTietokantaan(), valittuPVM, valittuAika,valittuKentta,kayttajatunnus);
                            if (varausOnnistui) {
                                teeLasku(valittuLaskutyyppi);
                            } else {

                                System.out.println("VARAUS EPÄONNISTUI, TEE TÄNNE VIRHEENKÄSITTELY ELI VARMAAN PALUU ALKUUN");
                            }
                        } catch (SQLException e1) {
                            throw new RuntimeException(e1);
                        }
                        try {
                            Tietokantayhteys.katkaiseYhteysTietokantaan();
                        } catch (SQLException e1) {
                            throw new RuntimeException(e1);
                        }
                    } catch (RuntimeException e1) {
                        throw new RuntimeException(e1);
                    }
                });

                t2.start();
                try {
                    t2.join();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        return view;
    }

    private void puraLisapalvelut(String valitutPelivalineet) {
        boolean readValine = false;
        boolean readHinta = false;
        String hinta = "";
        int total = 0;

        for (int i = 0; i < valitutPelivalineet.length(); i++) {
            switch (valitutLisapalvelut.charAt(i)) {
                case '*' -> readValine = true;
                case '^' -> {
                    readValine = false;
                    readHinta = true;
                }
                case '€' -> {
                    readHinta = false;
                    total += Integer.parseInt(hinta);
                    hinta = "";
                }
                default -> {
                    if (readHinta) {
                        hinta += i;
                    }
                }
            }
        }
        lisapalveluTotHinta = total;
    }

    private void teeLasku(int valittuLaskutyyppi) {

    }

    //TODO TEHTÄVÄ TÄÄ LASKURI LOPPUSUMMALLE
    private int laskeKokonaisSumma() {
        int summa = 0;
        summa += Integer.parseInt(kenttaHinta);
        summa += lisapalveluTotHinta;
        return summa;
    }
}