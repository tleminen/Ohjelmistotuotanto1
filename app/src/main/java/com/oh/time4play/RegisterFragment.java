package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.oh.time4play.RegisterFragmentDirections;

import java.sql.SQLException;

//Kesken
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

        EditText etkayttajatunnus = view.findViewById(R.id.etAnnaSahkopostiosoite);
        EditText etsalasana = view.findViewById(R.id.etAnnaSalasana);
        EditText etosoite = view.findViewById(R.id.etAnnaOsoitteesi);

        Rekisteroidy.setOnClickListener(e -> {
            String kayttaja = etkayttajatunnus.getText().toString();
            String salasana = etsalasana.getText().toString();
            String osoite = etosoite.getText().toString();
            Thread t1 = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        try {
                            muutettu = SystemKyselyt.setUusiAsiakas(kayttaja,salasana,osoite);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            t1.start();
            try {
                System.out.println(muutettu);
                t1.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            //Navigoidaan käyttäen safeArgs
            com.oh.time4play.RegisterFragmentDirections.ActionRegisterFragmentToToimipFragment action = com.oh.time4play.RegisterFragmentDirections.actionRegisterFragmentToToimipFragment(kayttaja,salasana);
            Navigation.findNavController(view).navigate(action);
        });



        return view;
    }
}