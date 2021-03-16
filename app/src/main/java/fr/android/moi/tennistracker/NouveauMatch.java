package fr.android.moi.tennistracker;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class NouveauMatch extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nouveau_match, container, false);
        Button retourButton = view.findViewById(R.id.buttonRetour);
        Button demarrerButton = view.findViewById(R.id.buttonDemarrer);
        EditText joueur1et = view.findViewById(R.id.Personne1Txt);
        EditText joueur2et = view.findViewById(R.id.Personne2Txt);
        RadioButton rbJoueur1 = view.findViewById(R.id.rbJ1);

        //Clic sur le boutton retour, revient au fragment Main Page
        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new MainPage());
                fr.commit();
            }
        });

        //Clic sur le boutton demarrer, passage au fragment ..
        demarrerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("j1Text", joueur1et.getText().toString());
                bundle.putString("j2Text", joueur2et.getText().toString());
                bundle.putBoolean("player1Serving", rbJoueur1.isChecked());
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                Enregistrement enregistrement = new Enregistrement();
                enregistrement.setArguments(bundle);
                fr.replace(R.id.fragment_container, enregistrement);
                fr.commit();
            }
        });
        return view;
    }


}