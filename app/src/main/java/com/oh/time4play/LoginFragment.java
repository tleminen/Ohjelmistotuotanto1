package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.SQLException;

//Ihan hyvä ehkä, pitää testata toi safeArgs castaus toimiiko ja muutenkin toimivuus testattava kun tietokantayhteys on tehty
public class LoginFragment extends Fragment {

    private boolean rooli = false;

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
            String kayttaja = etKayttajatunnus.getText().toString();
            String salasana = etSalasana.getText().toString();
            Connection connection = Tietokantayhteys.yhdistaTietokantaan(kayttaja, salasana);

            //Ensin kokeillaan onko rooli asiakas ja jos on niin siirrytään toimip-fragmenttiin
            try {
                rooli = KirjautumisKyselyt.getOnkoRooliAsiakas(connection,kayttaja);
                Tietokantayhteys.katkaiseYhteysTietokantaan();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (rooli) {
                com.oh.time4play.LoginFragmentDirections.ActionLoginFragmentToToimipFragment action = (com.oh.time4play.LoginFragmentDirections.ActionLoginFragmentToToimipFragment) LoginFragmentDirections.actionLoginFragmentToToimipFragment();
                action.setKirjautunutKayttaja(kayttaja);
                action.setKirjautunutSalasana(salasana);
            Navigation.findNavController(view).navigate(action);
            }

            //Seuraavaksi kokeillaan onko rooli toimipistevastaava, ja jos on niin siirrytään toimipisteen hallintaan
            try {
                rooli = KirjautumisKyselyt.getOnkoRooliToimipistevastaava(connection,kayttaja);
                Tietokantayhteys.katkaiseYhteysTietokantaan();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (rooli) {
                com.oh.time4play.LoginFragmentDirections.ActionLoginFragmentToToimipisteenHallintaFragment action = (com.oh.time4play.LoginFragmentDirections.ActionLoginFragmentToToimipisteenHallintaFragment) LoginFragmentDirections.actionLoginFragmentToToimipisteenHallintaFragment();
                action.setKirjautunutKayttaja(kayttaja);
                action.setKirjautunutSalasana(salasana);
                Navigation.findNavController(view).navigate(action);
            }

            //Lopuksi testataan onko rooli toimipisteiden hallinnoitsija ja jos on niin siirrytään toimip_hallinta fragmenttiin
            try {
                rooli = KirjautumisKyselyt.getOnkoRooliToimipisteidenHallinnoitsija(connection, kayttaja);
                Tietokantayhteys.katkaiseYhteysTietokantaan();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (rooli) {
                com.oh.time4play.LoginFragmentDirections.ActionLoginFragmentToToimipHallintaFragment action = (com.oh.time4play.LoginFragmentDirections.ActionLoginFragmentToToimipHallintaFragment) LoginFragmentDirections.actionLoginFragmentToToimipFragment();
                action.setKirjautunutKayttaja(kayttaja);
                action.setKirjautunutSalasana(salasana);
                Navigation.findNavController(view).navigate(action);
            }
        });

        //Rekisteröidy buttonin toiminta
        btRegister.setOnClickListener(e -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        });

        return view;
    }
}