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
    static int valittuKentta;

    public static void setValittuKentta(int valittuKentta) {
        toimipisteen_hallintaFragment.valittuKentta = valittuKentta;
    }

    Kentan_valintamuuttujat[] dataset;

    public toimipisteen_hallintaFragment() {super(R.layout.fragment_toimipisteen_hallinta);}

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

        thUusiPelivaline.setOnClickListener(e -> {
            com.oh.time4play.toimipisteen_hallintaFragmentDirections.ActionToimipisteenHallintaFragmentToThPelivalineLisaysFragment action = com.oh.time4play.toimipisteen_hallintaFragmentDirections.actionToimipisteenHallintaFragmentToThPelivalineLisaysFragment(kayttajatunnus,salasana);
            Navigation.findNavController(view).navigate(action);
        });

        thMuokkaaKentta.setOnClickListener(e -> {
            com.oh.time4play.toimipisteen_hallintaFragmentDirections.ActionToimipisteenHallintaFragmentToThMuokattavaKenttaFragment action = com.oh.time4play.toimipisteen_hallintaFragmentDirections.actionToimipisteenHallintaFragmentToThMuokattavaKenttaFragment(kayttajatunnus,salasana,valittuKentta);
            Navigation.findNavController(view).navigate(action);
        });

        thMuokkaaPelivaline.setOnClickListener(e -> {
            com.oh.time4play.toimipisteen_hallintaFragmentDirections.ActionToimipisteenHallintaFragmentToThMuokattavaLajiFragment action = com.oh.time4play.toimipisteen_hallintaFragmentDirections.actionToimipisteenHallintaFragmentToThMuokattavaLajiFragment(kayttajatunnus,salasana,valittuKentta);
            Navigation.findNavController(view).navigate(action);
        });

        tpPoistu.setOnClickListener(e -> {
            com.oh.time4play.toimipisteen_hallintaFragmentDirections.ActionToimipisteenHallintaFragmentToLoppuikkunaFragment action = com.oh.time4play.toimipisteen_hallintaFragmentDirections.actionToimipisteenHallintaFragmentToLoppuikkunaFragment(kayttajatunnus);
            Navigation.findNavController(view).navigate(action);
        });


        // Inflate the layout for this fragment
        return view;
    }
}