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
import android.widget.TextClock;
import android.widget.TextView;


import com.oh.time4play.maksuikkunaFragmentDirections;

import java.sql.SQLException;

public class maksuikkunaFragment extends Fragment {

    Asiakas_Muuttujat asiakas;
    LaskuMuuttujat lasku = new LaskuMuuttujat();


    private String kayttajatunnus;
    private String salasana;
    private String valittuToimipiste;
    private String valittuPVM;
    private int valittuKentta;
    private int valittuAika;
    private boolean varausOnnistui;
    private String valitutLisapalvelut;
    private String lisaPalvelutLaskulle = "";
    private String loppuSumma;
    private String kenttaHinta;
    private int lisapalveluTotHinta;
    private int [] pelivalineIDt;
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
        pelivalineIDt = maksuikkunaFragmentArgs.fromBundle(getArguments()).getValitutPelivalineIDt();

        View view = inflater.inflate(R.layout.fragment_maksuikkuna, container, false);

        RadioButton rbSahkopostiLasku = view.findViewById(R.id.rb_sahkopostiValinta_maksuikkuna);
        RadioButton rbPaperilasku = view.findViewById(R.id.rb_paperilaskuValinta_maksuikkuna);

        TextView tvSumma = view.findViewById(R.id.tv_summa_maksuikkuna);
        TextView tvEmailOsoite = view.findViewById(R.id.tv_sahkopostiAsiakkaan_maksuikkuna);
        TextView tvYhteenveto = view.findViewById(R.id.tvYhteenvetoMaksuikkuna);

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
        loppuSumma = laskeKokonaisSumma();
        tvSumma.setText(loppuSumma + "€");
        tvEmailOsoite.setText(kayttajatunnus);

        //Muodostetaan LaskuMuuttujat
        laskunMuodostus();

        String printti = "";
        printti = "Varattu kenttä: " + lasku.getKentanNimi() + " Hinta: " + lasku.getKentanHinta() + "€" + "\nVarauksen aika: " + valittuPVM + " klo: " + valittuAika + "\n" + lisaPalvelutLaskulle;
        tvYhteenveto.setText(printti);

        btVahvista.setOnClickListener(e ->
        {
            if (rbPaperilasku.isChecked() || rbSahkopostiLasku.isChecked()) {
                System.out.println("Lasku valittu");
                if (rbPaperilasku.isChecked()) {
                    lasku.setValittuMaksutapa(2);
                } else if (rbSahkopostiLasku.isChecked()) {
                    lasku.setValittuMaksutapa(3);
                }
                Thread t2 = new Thread(() -> {
                    try {
                        try {
                            varausOnnistui = Maksun_Kyselyt.teeVaraus(Tietokantayhteys.yhdistaSystemTietokantaan(), valittuPVM, valittuAika,valittuKentta,kayttajatunnus,pelivalineIDt,lasku.getValittuMaksutapa());
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
                if (varausOnnistui) {
                    System.out.println("Lasku tehty, siirrytään loppufragmenttiin");
                    com.oh.time4play.maksuikkunaFragmentDirections.ActionMaksuikkunaFragmentToLoppuikkunaFragment action = com.oh.time4play.maksuikkunaFragmentDirections.actionMaksuikkunaFragmentToLoppuikkunaFragment(kayttajatunnus);
                    Navigation.findNavController(view).navigate(action);
                } else {
                    System.out.println("VARAUS EPÄONNISTUI, TEE TÄNNE VIRHEENKÄSITTELY ELI VARMAAN PALUU ALKUUN");
                }
            }
        });

        btVahvistaJaTallenna.setOnClickListener(e -> {
            if (rbPaperilasku.isChecked() || rbSahkopostiLasku.isChecked()) {
                System.out.println("Lasku valittu");
                if (rbPaperilasku.isChecked()) {
                    lasku.setValittuMaksutapa(2);
                } else if (rbSahkopostiLasku.isChecked()) {
                    lasku.setValittuMaksutapa(3);
                }
                Thread t3 = new Thread(() -> {
                    try {
                        try {
                            Maksun_Kyselyt.updateAsiakas(Tietokantayhteys.yhdistaSystemTietokantaan(),kayttajatunnus,etOsoite.getText().toString(),etNimi.getText().toString());
                            varausOnnistui = Maksun_Kyselyt.teeVaraus(Tietokantayhteys.yhdistaSystemTietokantaan(), valittuPVM, valittuAika,valittuKentta,kayttajatunnus,pelivalineIDt,lasku.getValittuMaksutapa());
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

                t3.start();
                try {
                    t3.join();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                if (varausOnnistui) {
                    System.out.println("Varaus tehty, siirrytään loppufragmenttiin");
                    com.oh.time4play.maksuikkunaFragmentDirections.ActionMaksuikkunaFragmentToLoppuikkunaFragment action = com.oh.time4play.maksuikkunaFragmentDirections.actionMaksuikkunaFragmentToLoppuikkunaFragment(kayttajatunnus);
                    Navigation.findNavController(view).navigate(action);
                } else {
                    System.out.println("VARAUS EPÄONNISTUI");
                    com.oh.time4play.maksuikkunaFragmentDirections.ActionMaksuikkunaFragmentToLoppuikkunaFragment action = com.oh.time4play.maksuikkunaFragmentDirections.actionMaksuikkunaFragmentToLoppuikkunaFragment("Valitettavasti varaus epäonnistui. Yritä uudelleen.");
                    Navigation.findNavController(view).navigate(action);
                }
            }
        });

        return view;
    }

    private void laskunMuodostus() {
        lasku.setAsiakkaanNimi(asiakas.getAsiakasNimi());
        lasku.setLoppuSumma(loppuSumma);
        lasku.setValitutLisapalvelut(lisaPalvelutLaskulle);
        lasku.setAsiakkaanEmail(kayttajatunnus);
        lasku.setAsiakkaanOsoite(asiakas.getOsoite());
        lasku.setKentanHinta(Integer.parseInt(kenttaHinta));

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Kentta_Muuttujat kentta = th_kyselyt.getKentta(Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus,salasana),valittuKentta);
                    Toimip_hallintaMuuttujat tp = Toimip_hallinta_kyselyt.getToimipiste(Tietokantayhteys.yhdistaSystemTietokantaan(),valittuToimipiste);

                    lasku.setKentanNimi(kentta.nimi);
                    lasku.setToimipisteenNimi(tp.Nimi);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t3.start();
        try {
            t3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lasku muodostettu");
    }

    private void puraLisapalvelut(String valitutPelivalineet) {
        System.out.println("Lisäpalvelujen String purkuun tuli: " + valitutLisapalvelut);
        boolean readValine = false;
        boolean readHinta = false;
        String hinta = "";
        String valine = "";
        int total = 0;

        for (int i = 0; i < valitutPelivalineet.length(); i++) {
            switch (valitutLisapalvelut.charAt(i)) {
                case '*' -> {
                    readValine = true;
                }
                case '^' -> {
                    readValine = false;
                    readHinta = true;
                    System.out.println("Lisäpalvelun nimi: " + valine);
                    lisaPalvelutLaskulle += valine + " ";
                    valine = "";
                }
                case '€' -> {
                    readHinta = false;
                    System.out.println("Lisäpalvelun hinta: " + hinta);
                    lisaPalvelutLaskulle += hinta + "€\n";
                    total += Integer.parseInt(hinta);
                    hinta = "";
                }
                default -> {
                    if (readHinta) {
                        hinta += valitutLisapalvelut.charAt(i);
                    }
                    if (readValine) {
                        valine += valitutLisapalvelut.charAt(i);
                    }
                }
            }
        }
        lisapalveluTotHinta = total;
    }

    private String laskeKokonaisSumma() {
        int summa = 0;
        summa += Integer.parseInt(kenttaHinta);
        summa += lisapalveluTotHinta;
        return String.valueOf(summa);
    }
}