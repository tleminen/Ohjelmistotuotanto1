package com.oh.time4play;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import com.oh.time4play.toimip_hallintaFragmentDirections;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class toimip_hallintaFragment extends Fragment {
    public ArrayList<Toimip_hallintaMuuttujat> itemArrayList; //

    public static String valittuToimipiste;
    public static int valittuPositio;
    int muutettu = 0;

    public static String getValittuToimipiste() {
        return valittuToimipiste;
    }

    public static void setValittuToimipiste(String valittuToimipiste) {
        toimip_hallintaFragment.valittuToimipiste = valittuToimipiste;
    }

    public toimip_hallintaFragment() {super(R.layout.fragment_toimip_hallinta);}

    @SuppressLint("SetTextI18n")
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
        TextView tvHuomio = view.findViewById(R.id.tvHuomioToimip_hallinta);
        tvHuomio.setVisibility(View.INVISIBLE);

        //RecycleView Toimipisteiden listaamiseen
        RecyclerView myRecycleView = view.findViewById(R.id.rwToimipisteidenHallinnointi);

        Thread t1 = new Thread(() -> {
            try {
                try {
                    itemArrayList = Toimip_hallinta_kyselyt.getAllToimipisteet(Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus,salasana));

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Tietokantayhteys.katkaiseYhteysTietokantaan();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        myRecycleView.setAdapter(new Toimip_hallintaListAdapter(itemArrayList));
        myRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));


        //Asiakas hallinta (Asiakkaan poistaminen)
        btPoistaAsiakas.setOnClickListener(e -> {
            tvHuomio.setVisibility(View.INVISIBLE);
            if (!etPoistettavaAsiakas.getText().toString().equals("")) {
                String poistettavaAsiakas = etPoistettavaAsiakas.getText().toString();

                Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Connection connection = Tietokantayhteys.yhdistaSystemTietokantaan();
                            muutettu = Toimip_hallinta_kyselyt.poistaAsiakas(connection, poistettavaAsiakas);
                            Tietokantayhteys.katkaiseYhteysTietokantaan();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                t2.start();
                try {
                    t2.join();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                if (muutettu ==1 ){
                tvHuomio.setText("Asiakas: " + poistettavaAsiakas + " poistettu!");
                tvHuomio.setTextColor(Color.GREEN);
                } else {
                    tvHuomio.setText("Asiakasta: " + poistettavaAsiakas + " ei poistettu!\nTarkasta syöttämäsi tiedot");
                    tvHuomio.setTextColor(Color.RED);
                }

                tvHuomio.setVisibility(View.VISIBLE);
                etPoistettavaAsiakas.setText(null);
            }
        });

        //Siirtymät nappuloiden mukaan
        btLisaaTp.setOnClickListener(e -> {
            com.oh.time4play.toimip_hallintaFragmentDirections.ActionToimipHallintaFragmentToToimipLisaysFragment action = com.oh.time4play.toimip_hallintaFragmentDirections.actionToimipHallintaFragmentToToimipLisaysFragment(kayttajatunnus,salasana);
            Navigation.findNavController(view).navigate(action);
        });

        btMuokkaaTp.setOnClickListener(e -> {
            if (valittuToimipiste != null) {
                com.oh.time4play.toimip_hallintaFragmentDirections.ActionToimipHallintaFragmentToToimipMuokkausFragment action = com.oh.time4play.toimip_hallintaFragmentDirections.actionToimipHallintaFragmentToToimipMuokkausFragment(kayttajatunnus, salasana, valittuToimipiste);
                System.out.println("seuraavaan fragmenttiin menee: " + getValittuToimipiste());
                Navigation.findNavController(view).navigate(action);
            }
        });

        btPoistaTp.setOnClickListener(e -> {
            if (valittuToimipiste != null) {
                com.oh.time4play.toimip_hallintaFragmentDirections.ActionToimipHallintaFragmentToToimipPoistoFragment action = com.oh.time4play.toimip_hallintaFragmentDirections.actionToimipHallintaFragmentToToimipPoistoFragment(valittuToimipiste, kayttajatunnus, salasana);
                Navigation.findNavController(view).navigate(action);
            }
        });

        btPoistuTp.setOnClickListener(e -> {
            com.oh.time4play.toimip_hallintaFragmentDirections.ActionToimipHallintaFragmentToLoppuikkunaFragment action = com.oh.time4play.toimip_hallintaFragmentDirections.actionToimipHallintaFragmentToLoppuikkunaFragment("Toimipisteen Hallinnoitsija");
            Navigation.findNavController(view).navigate(action);
        });

        super.onViewCreated(view, savedInstanceState);
    }
}