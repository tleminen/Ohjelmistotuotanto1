package com.oh.time4play;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.oh.time4play.kenttaFragmentDirections;

import java.sql.SQLException;
import java.util.ArrayList;

public class kenttaFragment extends Fragment {

    public kenttaFragment() {
        super(R.layout.fragment_kentta);
    }

    public ArrayList<Kentta_Muuttujat> itemArrayList;

    private ArrayList<VarausAjat> varatutAjatList;

    private static int valittuKentta = -1;

    public static int getValittuKentta() {
        return valittuKentta;
    }

    public static void setValittuKentta(int valinta) {
        kenttaFragment.valittuKentta = valinta;
        System.out.println(valinta);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Haetaan navigaation actionista bundle joka sisältää käyttäjätunnuksen ja salasanan
        String kayttajatunnus = kenttaFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = kenttaFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();
        String valittuToimipiste = kenttaFragmentArgs.fromBundle(getArguments()).getValittuToimipiste();
        String valittuPVM = kenttaFragmentArgs.fromBundle(getArguments()).getValittuPVM();
        String valittuLaji = kenttaFragmentArgs.fromBundle(getArguments()).getValittuLaji();

        Button btSeuraava = view.findViewById(R.id.bt_kenttaVahvistanappi);

        //RecycleView kenttien listaamiseen
        RecyclerView myRecycleView = view.findViewById(R.id.rv_kenttaValinnat);

        Thread t1 = new Thread(() -> {
            try {
                try {
                    itemArrayList = th_kyselyt.getAllKentat(Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus, salasana), valittuToimipiste);
                    varatutAjatList = th_kyselyt.getAllVarausAjat(Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus,salasana),valittuToimipiste,valittuPVM);

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

        lisaaVarauksetKenttiin(itemArrayList,varatutAjatList);

        myRecycleView.setAdapter(new kentta_ListAdapter(itemArrayList));
        myRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        btSeuraava.setOnClickListener(e -> {
            if (valittuKentta != -1) {
                com.oh.time4play.kenttaFragmentDirections.ActionKenttaFragmentToKellonaikaValintaFragment action = com.oh.time4play.kenttaFragmentDirections.actionKenttaFragmentToKellonaikaValintaFragment(kayttajatunnus,salasana,valittuToimipiste,valittuPVM,valittuKentta,valittuLaji);
                Navigation.findNavController(view).navigate(action);
            }
        });

    }

    private void lisaaVarauksetKenttiin(ArrayList<Kentta_Muuttujat> itemArrayList, ArrayList<VarausAjat> varatutAjatList) {
        for (VarausAjat aika: varatutAjatList) {
            for (Kentta_Muuttujat kentta: itemArrayList) {
                if (aika.getKenttaID() == kentta.kenttaID) {
                    switch (aika.getVarausAika()) {
                        case 0 -> kentta.setKlo00(1);
                        case 1 -> kentta.setKlo01(1);
                        case 2 -> kentta.setKlo02(1);
                        case 3 -> kentta.setKlo03(1);
                        case 4 -> kentta.setKlo04(1);
                        case 5 -> kentta.setKlo05(1);
                        case 6 -> kentta.setKlo06(1);
                        case 7 -> kentta.setKlo07(1);
                        case 8 -> kentta.setKlo08(1);
                        case 9 -> kentta.setKlo09(1);
                        case 10 -> kentta.setKlo10(1);
                        case 11 -> kentta.setKlo11(1);
                        case 12 -> kentta.setKlo12(1);
                        case 13 -> kentta.setKlo13(1);
                        case 14 -> kentta.setKlo14(1);
                        case 15 -> kentta.setKlo15(1);
                        case 16 -> kentta.setKlo16(1);
                        case 17 -> kentta.setKlo17(1);
                        case 18 -> kentta.setKlo18(1);
                        case 19 -> kentta.setKlo19(1);
                        case 20 -> kentta.setKlo20(1);
                        case 21 -> kentta.setKlo21(1);
                        case 22 -> kentta.setKlo22(1);
                        case 23 -> kentta.setKlo23(1);
                    }
                }
            }
        }
    }
}