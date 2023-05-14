package com.oh.time4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * th_muokattava_lajiFragment toteuttaa halutun lajin muokattavan pelivälineen valitsemisen
 */
public class th_muokattava_lajiFragment extends Fragment {

    public th_muokattava_lajiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_th_muokattava_laji, container, false);

        String kayttajatunnus = th_muokattava_lajiFragmentArgs.fromBundle(getArguments()).getKirjautunutKayttaja();
        String salasana = th_muokattava_lajiFragmentArgs.fromBundle(getArguments()).getKirjautunutSalasana();

        RadioButton rbTennis = view.findViewById(R.id.rb_tennis_th_muokattava_laji);
        RadioButton rbSulkapallo = view.findViewById(R.id.rb_sulkapallo_th_muokattava_laji);

        Button btVahvista = view.findViewById(R.id.bt_vahvista_th_muokattava_laji);

        btVahvista.setOnClickListener(e -> {
            String valittuLaji;
            if (rbSulkapallo.isChecked() | rbTennis.isChecked()) {
                if (rbSulkapallo.isChecked()) {
                    valittuLaji = "Sulkapallo";
                } else valittuLaji = "Tennis";
                com.oh.time4play.th_muokattava_lajiFragmentDirections.ActionThMuokattavaLajiFragmentToThPelivMuutoksetFragment action = com.oh.time4play.th_muokattava_lajiFragmentDirections.actionThMuokattavaLajiFragmentToThPelivMuutoksetFragment(kayttajatunnus, salasana, valittuLaji);
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }
}