package com.oh.time4play;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class loppuikkunaFragment extends Fragment {

    public loppuikkunaFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loppuikkuna,container,false);

        String tultiin = loppuikkunaFragmentArgs.fromBundle(getArguments()).getMistaTanneTultiin();

        TextView textView = view.findViewById(R.id.tvkenelleTervehdys);

        textView.setText("Nähdään " +tultiin + "!");

        view.setOnClickListener(onClickListener);

        return view;
    }

    View.OnClickListener onClickListener = v -> {
        com.oh.time4play.loppuikkunaFragmentDirections.ActionLoppuikkunaFragmentToLoginFragment action = com.oh.time4play.loppuikkunaFragmentDirections.actionLoppuikkunaFragmentToLoginFragment();
        Navigation.findNavController(v).navigate(action);
    };
}