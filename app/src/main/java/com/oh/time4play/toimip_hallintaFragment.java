package com.oh.time4play;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;

//Luokan ohjelmointi valmis paitsi asiakkaan poistaminen
public class toimip_hallintaFragment extends Fragment {

    static String valittuToimipiste = "";

    public static void setValittuToimipiste(String valittuToimipiste) {
        toimip_hallintaFragment.valittuToimipiste = valittuToimipiste;
    }

    Toimip_hallintaMuuttujat[] dataset;

    public toimip_hallintaFragment() {super(R.layout.fragment_toimip_hallinta);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Haetaan navigaation actionista bundle joka sisältää käyttäjätunnuksen ja salasanan
        String kayttajatunnus = toimip_hallintaFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = toimip_hallintaFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();

        Button btLisaaTp = view.findViewById(R.id.btLisaaToimipiste);

        Button btMuokkaaTp = view.findViewById(R.id.btMuokkaaToimipiste);

        Button btPoistaTp = view.findViewById(R.id.btPoistaToimipiste);

        Button btPoistuTp = view.findViewById(R.id.btPoistuToimip_hallinta);


        Button btPoistaAsiakas = view.findViewById(R.id.bt_tpHal_poistaAsiakas);
        EditText etPoistettavaAsiakas = view.findViewById(R.id.et_tpHal_poistettavaAsiakas);

        //RecycleView Toimipisteiden listaamiseen
        RecyclerView rvItemList = view.findViewById(R.id.rwToimipisteidenHallinnointi);
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    try {
                        dataset = Toimip_hallinta_kyselyt.getAllToimipisteet(Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus,salasana));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        Tietokantayhteys.katkaiseYhteysTietokantaan();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        rvItemList.setAdapter(new Toimip_hallintaListAdapter(dataset));
        rvItemList.setLayoutManager(new LinearLayoutManager(getContext()));

        //Asiakas hallinta (Asiakkaan poistaminen)
        btPoistaAsiakas.setOnClickListener(e -> {
            etPoistettavaAsiakas.getText().toString();
        });

        //Siirtymät nappuloiden mukaan
        btLisaaTp.setOnClickListener(e -> Navigation.findNavController(view).navigate(R.id.action_toimip_hallintaFragment_to_toimip_lisaysFragment));

        btMuokkaaTp.setOnClickListener(e -> {
            com.oh.time4play.toimip_hallintaFragmentDirections.ActionToimipHallintaFragmentToToimipMuokkausFragment action = com.oh.time4play.toimip_hallintaFragmentDirections.actionToimipHallintaFragmentToToimipMuokkausFragment(valittuToimipiste);
            Navigation.findNavController(view).navigate(action);
        });

        btPoistaTp.setOnClickListener(e -> {
            com.oh.time4play.toimip_hallintaFragmentDirections.ActionToimipHallintaFragmentToToimipPoistoFragment action = com.oh.time4play.toimip_hallintaFragmentDirections.actionToimipHallintaFragmentToToimipPoistoFragment(valittuToimipiste);
            Navigation.findNavController(view).navigate(action);
        });

        btPoistuTp.setOnClickListener(e -> {
            com.oh.time4play.toimip_hallintaFragmentDirections.ActionToimipHallintaFragmentToLoppuikkunaFragment action = com.oh.time4play.toimip_hallintaFragmentDirections.actionToimipHallintaFragmentToLoppuikkunaFragment("Toimipisteen hallitsija");
            Navigation.findNavController(view).navigate(action);
        });

        super.onViewCreated(view, savedInstanceState);
    }
}