package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;

//Kesken
public class RegisterFragment extends Fragment {

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
            try {
                SystemKyselyt.setUusiAsiakas(kayttaja,salasana,etosoite.getText().toString());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        return view;
    }
}