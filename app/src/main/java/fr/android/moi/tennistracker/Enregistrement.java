package fr.android.moi.tennistracker;

import android.media.MediaPlayer;
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
import android.widget.TextView;

import java.io.Console;

public class Enregistrement extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enregistrement, container, false);

        //TextView J1
        TextView pointActuelJ1 = view.findViewById(R.id.pointActuelJ1);
        TextView set1J1 = view.findViewById(R.id.set1J1);
        TextView set2J1 = view.findViewById(R.id.set2J1);

        //TextView J2
        TextView pointActuelJ2 = view.findViewById(R.id.pointActuelJ2);
        TextView set1J2 = view.findViewById(R.id.set1J2);
        TextView set2J2 = view.findViewById(R.id.set2J2);

        //Buttons
        Button premiereOK = view.findViewById(R.id.premiereOK);
        Button deuxiemeOK = view.findViewById(R.id.deuxiemeOK);
        Button ace1 = view.findViewById(R.id.ace1);
        Button ace2 = view.findViewById(R.id.ace2);
        Button doubleFaute = view.findViewById(R.id.doubleFaute);
        Button pointGagnantJ1 = view.findViewById(R.id.pointGagnantJ1);
        Button fauteProvoquéeJ1 = view.findViewById(R.id.fauteProvoquéeJ1);
        Button fauteDirecteJ1 = view.findViewById(R.id.fauteDirecteJ1);
        Button pointGagnantJ2 = view.findViewById(R.id.pointGagnantJ2);
        Button fauteProvoquéeJ2 = view.findViewById(R.id.fauteProvoquéeJ2);
        Button fauteDirecteJ2 = view.findViewById(R.id.fauteDirecteJ2);

        //Button ace 1
        ace1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointActuelJ1.setText("15");
            }
        });

        return view;
    }


}