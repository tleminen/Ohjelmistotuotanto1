package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.oh.time4play.ToimipFragmentDirections;

/**
 * TÄnne tehty testisiirtymä
 */
public class ToimipFragment extends Fragment {


    public ToimipFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toimip, container, false);
        //Haetaan navigaation actionista bundle joka sisältää käyttäjätunnuksen ja salasanan
        String kayttajatunnus = toimip_hallintaFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = toimip_hallintaFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();

        Button btVahvista = view.findViewById(R.id.btVahvista_toimipFrag);


        btVahvista.setOnClickListener(e -> {
            com.oh.time4play.ToimipFragmentDirections.ActionToimipFragmentToPvmFragment action = com.oh.time4play.ToimipFragmentDirections.actionToimipFragmentToPvmFragment(kayttajatunnus,salasana,"testi");
            Navigation.findNavController(view).navigate(action);
        });

        return view;




    }
}