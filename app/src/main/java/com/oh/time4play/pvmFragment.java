package com.oh.time4play;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.RadioButton;

public class pvmFragment extends Fragment {
    public String valittupvm;
    public String valittulaji;
    String paiva;
    String kuukausi;
    String vuosi;

    public pvmFragment(){


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pvm, container, false);

        String kayttajatunnus = pvmFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = pvmFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();
        String valittuToimipiste = pvmFragmentArgs.fromBundle(getArguments()).getValittuToimipiste();

        CalendarView calendarView = view.findViewById(R.id.cvPVM);
        RadioButton TennisNappula = view.findViewById(R.id.rb_PVM_Tennis);
        RadioButton SulkapalloNappula = view.findViewById(R.id.rb_PVM_Sulkapallo);
        Button Vahvista = view.findViewById(R.id.bt_PVM_VahvistaNappi);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                paiva = String.valueOf(dayOfMonth);
                vuosi = String.valueOf(year);
                kuukausi = String.valueOf(month + 1);
            }
        });

                Vahvista.setOnClickListener(e -> {
                    int day = Integer.parseInt(paiva);
                    if (day < 10){
                        paiva = "0"+paiva;
                    }
                    if (Integer.parseInt(kuukausi) < 10) {
                        kuukausi = "0" + kuukausi;
                    }
                    valittupvm = vuosi + "-" + kuukausi + "-" + paiva;
                    System.out.println(valittupvm.toString());
                    if (TennisNappula.isChecked() | SulkapalloNappula.isChecked()) {
                        if (TennisNappula.isChecked()) {
                            valittulaji = "Tennis";
                        } else if (SulkapalloNappula.isChecked()) {
                            valittulaji = "Sulkapallo";
                        }
                    }

                    pvmFragmentDirections.ActionPvmFragmentToKenttaFragment action = pvmFragmentDirections.actionPvmFragmentToKenttaFragment(kayttajatunnus, salasana, valittuToimipiste, valittupvm, valittulaji);
                    Navigation.findNavController(view).navigate(action);
                });
        return view;
    }
}