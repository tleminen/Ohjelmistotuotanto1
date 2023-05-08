package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class LaskutusFragment extends Fragment {


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



        return view;
    }
}