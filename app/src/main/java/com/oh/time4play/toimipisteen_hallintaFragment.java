package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.oh.time4play.toimipisteen_hallintaFragmentDirections;


public class toimipisteen_hallintaFragment extends Fragment {


    public toimipisteen_hallintaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_toimipisteen_hallinta, container, false);

        String kayttajatunnus = toimip_poistoFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = toimip_poistoFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();

        Button thLisaaKentta = view.findViewById(R.id.bt_LisaaKentta_toimipisteen_hallinta);
        Button thMuokkaaKentta = view.findViewById(R.id.bt_muokkaaKenttaa_toimipisteen_hallinta);
        Button thUusiPelivaline = view.findViewById(R.id.bt_uusiPelivaline_toimipisteen_hallinta);
        Button thMuokkaaPelivaline = view.findViewById(R.id.bt_muokkaaPelivalineita_toimipisteen_hallinta);

        Button tpPoistu = view.findViewById(R.id.bt_poistu_toimipisteen_hallinta);

        thLisaaKentta.setOnClickListener(e -> {
            com.oh.time4play.toimipisteen_hallintaFragmentDirections.ActionToimipisteenHallintaFragmentToThKenttaLisaysFragment action = com.oh.time4play.toimipisteen_hallintaFragmentDirections.actionToimipisteenHallintaFragmentToThKenttaLisaysFragment(kayttajatunnus,salasana);
            Navigation.findNavController(view).navigate(action);
        });


        // Inflate the layout for this fragment
        return view;
    }
}