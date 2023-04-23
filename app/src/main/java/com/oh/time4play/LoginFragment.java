package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.ThemedSpinnerAdapter;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button btRegister = view.findViewById(R.id.btRekisteroidy);

        Button btLogin = view.findViewById(R.id.btKirjaudu);

        EditText etKayttajatunnus = view.findViewById(R.id.etSahkoposti);
        EditText etSalasana = view.findViewById(R.id.etSalasana);

        btLogin.setOnClickListener(e -> {
            btLogin.setEnabled(false);
            System.out.println("Button login painettu");
            String kayttaja = etKayttajatunnus.getText().toString();
            String salasana = etSalasana.getText().toString();


            //Ensin kokeillaan onko rooli asiakas ja jos on niin siirrytään toimip-fragmenttiin
            System.out.println("Kokeillaan asiakasrooli");
            System.out.println("Roolin tila: " + KirjautumisKyselyt.getRooli());
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        KirjautumisKyselyt.getOnkoRooliAsiakas(Tietokantayhteys.yhdistaTietokantaan(kayttaja, salasana),kayttaja);
                        Tietokantayhteys.katkaiseYhteysTietokantaan();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
            System.out.println("Roolin tila: " + KirjautumisKyselyt.getRooli());

            //Seuraavaksi kokeillaan onko rooli toimipistevastaava, ja jos on niin siirrytään toimipisteen hallintaan
            System.out.println("Kokeillaan rooli toimipistevastaava");
            System.out.println("Roolin tila: " + KirjautumisKyselyt.getRooli());
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        KirjautumisKyselyt.getOnkoRooliToimipistevastaava(Tietokantayhteys.yhdistaTietokantaan(kayttaja, salasana),kayttaja);
                        Tietokantayhteys.katkaiseYhteysTietokantaan();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
            System.out.println("Roolin tila: " + KirjautumisKyselyt.getRooli());

            //Lopuksi testataan onko rooli toimipisteiden hallinnoitsija ja jos on niin siirrytään toimip_hallinta fragmenttiin
            System.out.println("Kokeillaan onko rooli toimipisteidenhallinnoitsija");
            System.out.println("Roolin tila: " + KirjautumisKyselyt.getRooli());
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        KirjautumisKyselyt.getOnkoRooliToimipisteidenHallinnoitsija(Tietokantayhteys.yhdistaTietokantaan(kayttaja, salasana), kayttaja);
                        Tietokantayhteys.katkaiseYhteysTietokantaan();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
            System.out.println("Roolin tila: " + KirjautumisKyselyt.getRooli());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            switch (KirjautumisKyselyt.getRooli()) {
                case 1 -> {
                    KirjautumisKyselyt.setRooli(0);
                    com.oh.time4play.LoginFragmentDirections.ActionLoginFragmentToToimipFragment action = LoginFragmentDirections.actionLoginFragmentToToimipFragment(kayttaja,salasana);
                    Navigation.findNavController(view).navigate(action);
                }
                case 2 -> {
                    KirjautumisKyselyt.setRooli(0);
                    com.oh.time4play.LoginFragmentDirections.ActionLoginFragmentToToimipisteenHallintaFragment action = LoginFragmentDirections.actionLoginFragmentToToimipisteenHallintaFragment(kayttaja,salasana);
                    Navigation.findNavController(view).navigate(action);
                }
                case 3 -> {
                    KirjautumisKyselyt.setRooli(0);
                    com.oh.time4play.LoginFragmentDirections.ActionLoginFragmentToToimipHallintaFragment action = LoginFragmentDirections.actionLoginFragmentToToimipHallintaFragment(kayttaja,salasana);
                    Navigation.findNavController(view).navigate(action);
                }
                case 0 -> {
                    System.out.println("Login failed... Tee tänne errorMessage homma");
                    btLogin.setEnabled(true);
                }
            }
        });

        //Rekisteröidy buttonin toiminta
        btRegister.setOnClickListener(e -> {
            System.out.println("Register painettu...");
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        });

        return view;
    }
}