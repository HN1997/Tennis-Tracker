package fr.android.moi.tennistracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Historique extends Fragment {

    //Boutton main menu
    Button buttonRetourMainMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historique, container, false);

        //Boutton main menu
        buttonRetourMainMenu = view.findViewById(R.id.buttonRetourMainMenu);

        //Listener Boutton main menu
        buttonRetourMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new MainPage());
                fr.commit();
            }
        });

        return view;
    }
}