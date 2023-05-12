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

import java.sql.SQLException;

/**
 * Toteuttaa uuden asiakkaan rekisteröitymisen
 * V1.0
 */
public class RegisterFragment extends Fragment {
    boolean muutettu = false;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        Button Rekisteroidy = view.findViewById(R.id.btTeeRekisterointi);
        TextView tvVirhe = view.findViewById(R.id.tvVirhe_RegisterFragment);
        tvVirhe.setVisibility(View.INVISIBLE);

        EditText etkayttajatunnus = view.findViewById(R.id.etAnnaSahkopostiosoite);
        EditText etsalasana = view.findViewById(R.id.etAnnaSalasana);
        EditText etosoite = view.findViewById(R.id.etAnnaOsoitteesi);
        EditText etNimi = view.findViewById(R.id.etNimi_Register);

        Rekisteroidy.setOnClickListener(e -> {
            tvVirhe.setVisibility(View.INVISIBLE);
            if (!etkayttajatunnus.getText().toString().equals("") && !etsalasana.getText().toString().equals("") && !etosoite.getText().toString().equals("") && !etNimi.getText().toString().equals("")) {
                String kayttaja = etkayttajatunnus.getText().toString();
                String salasana = etsalasana.getText().toString();
                String osoite = etosoite.getText().toString();
                String nimi = etNimi.getText().toString();
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            try {
                                muutettu = SystemKyselyt.setUusiAsiakas(kayttaja, salasana, osoite, nimi);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                t1.start();
                try {
                    t1.join();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(muutettu);
                if (muutettu) {
                    //Navigoidaan käyttäen safeArgs
                    com.oh.time4play.RegisterFragmentDirections.ActionRegisterFragmentToToimipFragment action = com.oh.time4play.RegisterFragmentDirections.actionRegisterFragmentToToimipFragment(kayttaja, salasana);
                    Navigation.findNavController(view).navigate(action);
                } else tvVirhe.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}