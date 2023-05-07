package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import com.oh.time4play.kellonaikaValintaFragmentDirections;

import java.sql.SQLException;
import java.util.ArrayList;


public class kellonaikaValintaFragment extends Fragment {
    static final int varattuVari = 0xFF883200;
    private int valittuAika;
    private ArrayList<VarausAjat> kentanVaraukset;

    private String kayttajatunnus;
    private String salasana;
    private String valittuToimipiste;
    private String valittuPVM;
    private int valittuKentta;
    private String valittuLaji;
    private String kentanHinta;

    public kellonaikaValintaFragment() {
        // Required empty public constructor
    }

    private void navigoi(View v) {
        System.out.println("Valittu aika: " + valittuAika);
        com.oh.time4play.kellonaikaValintaFragmentDirections.ActionKellonaikaValintaFragmentToPelivalineetFragment action = com.oh.time4play.kellonaikaValintaFragmentDirections.actionKellonaikaValintaFragmentToPelivalineetFragment(kayttajatunnus,salasana,valittuToimipiste,valittuPVM,valittuKentta,valittuAika,valittuLaji,kentanHinta);
        Navigation.findNavController(v).navigate(action);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_kellonaika_valinta, container, false);

        kayttajatunnus = kellonaikaValintaFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        salasana = kellonaikaValintaFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();
        valittuToimipiste = kellonaikaValintaFragmentArgs.fromBundle(getArguments()).getValittuToimipiste();
        valittuPVM = kellonaikaValintaFragmentArgs.fromBundle(getArguments()).getValittuPVM();
        valittuKentta = kellonaikaValintaFragmentArgs.fromBundle(getArguments()).getValittuKentta();
        valittuLaji = kellonaikaValintaFragmentArgs.fromBundle(getArguments()).getValittuLaji();
        kentanHinta = kellonaikaValintaFragmentArgs.fromBundle(getArguments()).getValitunKentanHinta();

        TextView t0 = view.findViewById(R.id.tv_KellonAikaValKLO0);
        TextView t1 = view.findViewById(R.id.tv_KellonAikaValKLO1);
        TextView t2 = view.findViewById(R.id.tv_KellonAikaValKLO2);
        TextView t3 = view.findViewById(R.id.tv_KellonAikaValKLO3);
        TextView t4 = view.findViewById(R.id.tv_KellonAikaValKLO4);
        TextView t5 = view.findViewById(R.id.tv_KellonAikaValKLO5);
        TextView t6 = view.findViewById(R.id.tv_KellonAikaValKLO6);
        TextView t7 = view.findViewById(R.id.tv_KellonAikaValKLO7);
        TextView t8 = view.findViewById(R.id.tv_KellonAikaValKLO8);
        TextView t9 = view.findViewById(R.id.tv_KellonAikaValKLO9);
        TextView t10 = view.findViewById(R.id.tv_KellonAikaValKLO10);
        TextView t11 = view.findViewById(R.id.tv_KellonAikaValKLO11);
        TextView t12 = view.findViewById(R.id.tv_KellonAikaValKLO12);
        TextView t13 = view.findViewById(R.id.tv_KellonAikaValKLO13);
        TextView t14 = view.findViewById(R.id.tv_KellonAikaValKLO14);
        TextView t15 = view.findViewById(R.id.tv_KellonAikaValKLO15);
        TextView t16 = view.findViewById(R.id.tv_KellonAikaValKLO16);
        TextView t17 = view.findViewById(R.id.tv_KellonAikaValKLO17);
        TextView t18 = view.findViewById(R.id.tv_KellonAikaValKLO18);
        TextView t19 = view.findViewById(R.id.tv_KellonAikaValKLO19);
        TextView t20 = view.findViewById(R.id.tv_KellonAikaValKLO20);
        TextView t21 = view.findViewById(R.id.tv_KellonAikaValKLO21);
        TextView t22 = view.findViewById(R.id.tv_KellonAikaValKLO22);
        TextView t23 = view.findViewById(R.id.tv_KellonAikaValKLO23);

        Thread th1 = new Thread(() -> {
            try {
                try {
                    kentanVaraukset = th_kyselyt.getKenttaVarausAjat(Tietokantayhteys.yhdistaTietokantaan(kayttajatunnus,salasana),valittuKentta,valittuPVM);

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

        th1.start();
        try {
            th1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //TODO JATKA TÄHÄN TOTA SETCLICKABLE JOS SE TOIMII!

        for (VarausAjat i: kentanVaraukset) {
            switch (i.getVarausAika()) {
                case 0 -> {
                    t0.setBackgroundColor(varattuVari);
                    t0.setEnabled(false);
                }
                case 1 -> {
                    t1.setBackgroundColor(varattuVari);
                    t1.setEnabled(false);
                }
                case 2 -> {
                    t2.setBackgroundColor(varattuVari);
                    t2.setEnabled(false);
                }
                case 3 -> {
                    t3.setBackgroundColor(varattuVari);
                    t3.setEnabled(false);
                }
                case 4 -> {
                    t4.setBackgroundColor(varattuVari);
                    t4.setEnabled(false);
                }
                case 5 -> {
                    t5.setBackgroundColor(varattuVari);
                    t5.setEnabled(false);
                }
                case 6 -> {
                    t6.setBackgroundColor(varattuVari);
                    t6.setEnabled(false);
                }
                case 7 -> {
                    t7.setBackgroundColor(varattuVari);
                    t7.setEnabled(false);
                }
                case 8 -> {
                    t8.setBackgroundColor(varattuVari);
                    t8.setEnabled(false);
                }
                case 9 -> {
                    t9.setBackgroundColor(varattuVari);
                    t9.setEnabled(false);
                }
                case 10 -> {
                    t10.setBackgroundColor(varattuVari);
                    t10.setEnabled(false);
                }
                case 11 -> {
                    t11.setBackgroundColor(varattuVari);
                    t11.setEnabled(false);
                }
                case 12 -> {
                    t12.setBackgroundColor(varattuVari);
                    t12.setEnabled(false);
                }
                case 13 -> {
                    t13.setBackgroundColor(varattuVari);
                    t13.setEnabled(false);
                }
                case 14 -> {
                    t14.setBackgroundColor(varattuVari);
                    t14.setEnabled(false);
                }
                case 15 -> {
                    t15.setBackgroundColor(varattuVari);
                    t15.setEnabled(false);
                }
                case 16 -> {
                    t16.setBackgroundColor(varattuVari);
                    t16.setEnabled(false);
                }
                case 17 -> {
                    t17.setBackgroundColor(varattuVari);
                    t17.setEnabled(false);
                }
                case 18 -> {
                    t18.setBackgroundColor(varattuVari);
                    t18.setEnabled(false);
                }
                case 19 -> {
                    t19.setBackgroundColor(varattuVari);
                    t19.setEnabled(false);
                }
                case 20 -> {
                    t20.setBackgroundColor(varattuVari);
                    t20.setEnabled(false);
                }
                case 21 -> {
                    t21.setBackgroundColor(varattuVari);
                    t21.setEnabled(false);
                }
                case 22 -> {
                    t22.setBackgroundColor(varattuVari);
                    t22.setEnabled(false);
                }
                case 23 -> {
                    t23.setBackgroundColor(varattuVari);
                    t23.setEnabled(false);
                }
            }
        }

        t0.setOnClickListener(e -> {
            valittuAika = 0;
            navigoi(view);
        });
        t1.setOnClickListener(e -> {
            valittuAika = 1;
            navigoi(view);
        });
        t2.setOnClickListener(e -> {
            valittuAika = 2;
            navigoi(view);
        });
        t3.setOnClickListener(e -> {
            valittuAika = 3;
            navigoi(view);
        });
        t4.setOnClickListener(e -> {
            valittuAika = 4;
            navigoi(view);
        });
        t5.setOnClickListener(e -> {
            valittuAika = 5;
            navigoi(view);
        });
        t6.setOnClickListener(e -> {
            valittuAika = 6;
            navigoi(view);
        });
        t7.setOnClickListener(e -> {
            valittuAika = 7;
            navigoi(view);
        });
        t8.setOnClickListener(e -> {
            valittuAika = 8;
            navigoi(view);
        });
        t9.setOnClickListener(e -> {
            valittuAika = 9;
            navigoi(view);
        });
        t10.setOnClickListener(e -> {
            valittuAika = 10;
            navigoi(view);
        });
        t11.setOnClickListener(e -> {
            valittuAika = 11;
            navigoi(view);
        });
        t12.setOnClickListener(e -> {
            valittuAika = 12;
            navigoi(view);
        });
        t13.setOnClickListener(e -> {
            valittuAika = 13;
            navigoi(view);
        });
        t14.setOnClickListener(e -> {
            valittuAika = 14;
            navigoi(view);
        });
        t15.setOnClickListener(e -> {
            valittuAika = 15;
            navigoi(view);
        });
        t16.setOnClickListener(e -> {
            valittuAika = 16;
            navigoi(view);
        });
        t17.setOnClickListener(e -> {
            valittuAika = 17;
            navigoi(view);
        });
        t18.setOnClickListener(e -> {
            valittuAika = 18;
            navigoi(view);
        });
        t19.setOnClickListener(e -> {
            valittuAika = 19;
            navigoi(view);
        });
        t20.setOnClickListener(e -> {
            valittuAika = 20;
            navigoi(view);
        });
        t21.setOnClickListener(e -> {
            valittuAika = 21;
            navigoi(view);
        });
        t22.setOnClickListener(e -> {
            valittuAika = 22;
            navigoi(view);
        });
        t23.setOnClickListener(e -> {
            valittuAika = 23;
            navigoi(view);
        });

        return view;
    }
}