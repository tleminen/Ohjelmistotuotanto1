package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;

public class pvmFragment extends Fragment {
    public String valittupvm;
    public String valittulaji;

    public pvmFragment(){


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pvm, container, false);

        String kayttajatunnus = pvmFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = pvmFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();
        String valittuToimipiste = pvmFragmentArgs.fromBundle(getArguments()).getValittuToimipiste();

        DatePicker Alkupvm = view.findViewById(R.id.datePicker_PVM);
        RadioButton TennisNappula = view.findViewById(R.id.rb_PVM_Tennis);
        RadioButton SulkapalloNappula = view.findViewById(R.id.rb_PVM_Sulkapallo);
        Button Vahvista = view.findViewById(R.id.bt_PVM_VahvistaNappi);

        Vahvista.setOnClickListener(e->{

            valittupvm = String.valueOf(Alkupvm.getYear()+"-"+ Alkupvm.getMonth()+"-"+ Alkupvm.getDayOfMonth());
            if (TennisNappula.isSelected() | SulkapalloNappula.isSelected()) {
                if (TennisNappula.isSelected()){
                    valittulaji = String.valueOf(TennisNappula);
                } else if (SulkapalloNappula.isSelected()){
                    valittulaji = String.valueOf(SulkapalloNappula);
                }
            }
            pvmFragmentDirections.ActionPvmFragmentToKenttaFragment action = pvmFragmentDirections.actionPvmFragmentToKenttaFragment(kayttajatunnus,salasana,valittuToimipiste,valittupvm, valittulaji);
            Navigation.findNavController(view).navigate(action);
        });
        return view;
    }
}