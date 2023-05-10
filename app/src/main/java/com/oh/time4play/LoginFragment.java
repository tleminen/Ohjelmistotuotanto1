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

/**
 * Login fragment sisältää kirjautumisnäkymän
 * V1.0
 */
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

        TextView tvVaroitus = view.findViewById(R.id.tvLogin_Varoitus);

        tvVaroitus.setVisibility(View.INVISIBLE);

        //Painetaan login:
        btLogin.setOnClickListener(e -> {
            tvVaroitus.setVisibility(View.INVISIBLE);
            if (!etKayttajatunnus.getText().toString().equals("") && !etSalasana.getText().toString().equals("")) {
                btLogin.setEnabled(false);
                System.out.println("Button login painettu");
                String kayttaja = etKayttajatunnus.getText().toString();
                String salasana = etSalasana.getText().toString();


                //Ensin kokeillaan onko rooli asiakas
                System.out.println("Kokeillaan asiakasrooli");
                System.out.println("Roolin tila: " + KirjautumisKyselyt.getRooli());

                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            KirjautumisKyselyt.getOnkoRooliAsiakas(Tietokantayhteys.yhdistaTietokantaan(kayttaja, salasana), kayttaja);
                            Tietokantayhteys.katkaiseYhteysTietokantaan();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                t1.start();

                //Seuraavaksi kokeillaan onko rooli toimipistevastaava
                System.out.println("Kokeillaan rooli toimipistevastaava");
                Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            KirjautumisKyselyt.getOnkoRooliToimipistevastaava(Tietokantayhteys.yhdistaTietokantaan(kayttaja, salasana), kayttaja);
                            Tietokantayhteys.katkaiseYhteysTietokantaan();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                t2.start();

                //Lopuksi testataan onko rooli toimipisteiden hallinnoitsija
                System.out.println("Kokeillaan onko rooli toimipisteidenhallinnoitsija");
                Thread t3 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            KirjautumisKyselyt.getOnkoRooliToimipisteidenHallinnoitsija(Tietokantayhteys.yhdistaTietokantaan(kayttaja, salasana), kayttaja);
                            Tietokantayhteys.katkaiseYhteysTietokantaan();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                t3.start();

                try {
                    t1.join();
                    t2.join();
                    t3.join();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                switch (KirjautumisKyselyt.getRooli()) {
                    case 1 -> {
                        KirjautumisKyselyt.setRooli(0);
                        com.oh.time4play.LoginFragmentDirections.ActionLoginFragmentToToimipFragment action = LoginFragmentDirections.actionLoginFragmentToToimipFragment(kayttaja, salasana);
                        Navigation.findNavController(view).navigate(action);
                    }
                    case 2 -> {
                        KirjautumisKyselyt.setRooli(0);
                        com.oh.time4play.LoginFragmentDirections.ActionLoginFragmentToToimipisteenHallintaFragment action = LoginFragmentDirections.actionLoginFragmentToToimipisteenHallintaFragment(kayttaja, salasana);
                        Navigation.findNavController(view).navigate(action);
                    }
                    case 3 -> {
                        KirjautumisKyselyt.setRooli(0);
                        com.oh.time4play.LoginFragmentDirections.ActionLoginFragmentToToimipHallintaFragment action = LoginFragmentDirections.actionLoginFragmentToToimipHallintaFragment(kayttaja, salasana);
                        Navigation.findNavController(view).navigate(action);
                    }
                    case 0 -> {
                        System.out.println("Login failed...");
                        tvVaroitus.setText(R.string.text_tvLogin_vaaratKirjautumistiedot);
                        tvVaroitus.setVisibility(View.VISIBLE);
                        btLogin.setEnabled(true);
                    }
                }
            } else {
                tvVaroitus.setText(R.string.text_tvLogin_varoitus);
                tvVaroitus.setVisibility(View.VISIBLE);
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