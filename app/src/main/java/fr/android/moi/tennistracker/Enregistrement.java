package fr.android.moi.tennistracker;

import android.graphics.Color;
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
    TextView joueur1Name;
    TextView joueur2Name;
    boolean premiereOkButtonClicked = false; //Si le boutton 1ere ok est cliqué
    boolean deuxiemeOkButtonClicked = false; //Si le boutton 2eme ok est cliqué
    boolean joueur1isServing;

    //Statistiques J1 //

    int pointActuelIntJ1; //Le nombre de point actuel
    int nbr1ereOkJ1; //Quand il sert bien la premiere fois
    int nbr2emeOkJ1; //Quand il sert bien la deuxieme fois
    int nbr1AceJ1; //Ace au premier service
    int nbr2AceJ1; //Ace au deuxieme service
    int nbrDoubleFauteJ1; //Nombre de double faute
    int nbrPointGagnantJ1; //Il a remporté le point
    int nbrFauteProvoqueeJ1; //Il n'a pas remporté le point et a fait une faute provoquée
    int nbrFauteDirectJ1; //Il n'a pas remporté le point et a fait une faute directe

    //Statistiques J2 //

    int pointActuelIntJ2; //Le nombre de point actuel
    int nbr1ereOkJ2; //Quand il sert bien la premiere fois
    int nbr2emeOkJ2; //Quand il sert bien la deuxieme fois
    int nbr1AceJ2; //Ace au premier service
    int nbr2AceJ2; //Ace au deuxieme service
    int nbrDoubleFauteJ2; //Nombre de double faute
    int nbrPointGagnantJ2; //Il a remporté le point
    int nbrFauteProvoqueeJ2; //Il n'a pas remporté le point et a fait une faute provoquée
    int nbrFauteDirectJ2; //Il n'a pas remporté le point et a fait une faute directe

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enregistrement, container, false);

        //Bundle to get data from NouveauMatch
        Bundle bundle = this.getArguments();

        //TextView J1
        joueur1Name = view.findViewById(R.id.joueur1Name);
        TextView pointActuelJ1 = view.findViewById(R.id.pointActuelJ1);
        TextView set1J1 = view.findViewById(R.id.set1J1);
        TextView set2J1 = view.findViewById(R.id.set2J1);

        //TextView J2
        joueur2Name = view.findViewById(R.id.joueur2Name);
        TextView pointActuelJ2 = view.findViewById(R.id.pointActuelJ2);
        TextView set1J2 = view.findViewById(R.id.set1J2);
        TextView set2J2 = view.findViewById(R.id.set2J2);
        TextView serveurTitle = view.findViewById(R.id.serveurTitle);

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

        //Changing name player1, nameplayer2, person who serves first with the bundle
        joueur1Name.setText(bundle.getString("j1Text"));
        joueur2Name.setText(bundle.getString("j2Text"));
        joueur1isServing=bundle.getBoolean("player1Serving");

        if(joueur1isServing)
        {
            joueur1Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tennis_ball, 0, 0, 0); //ajout de l'image de la balle de tennis au j1
            serveurTitle.setText("Serveur : " + joueur1Name.getText());
        }
        else{
            joueur2Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tennis_ball, 0, 0, 0); //ajout de l'image de la balle de tennis au j2
            serveurTitle.setText("Serveur : " + joueur2Name.getText());
        }

        premiereOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                premiereOK.setBackgroundColor(Color.rgb(75,105,6)); //On change la couleur du boutton 1ere ok (couleur enfoncée)
                deuxiemeOK.setBackgroundColor(Color.rgb(255,217,17)); //On change la couleur du boutton 2eme ok (couleur relachée = par défaut)
                premiereOkButtonClicked = true; //le boutton 1ere ok est cliqué
                deuxiemeOkButtonClicked = false;//le boutton 1ere ok est cliqué
            }
        });

        deuxiemeOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                premiereOK.setBackgroundColor(Color.rgb(125,195,0)); //On change la couleur du boutton 1ere ok (couleur relachée = par défaut)
                deuxiemeOK.setBackgroundColor(Color.rgb(255,139,15)); //On change la couleur du boutton 2eme ok (couleur relachée = par défaut)
                premiereOkButtonClicked = false; //le boutton 1ere ok est cliqué
                deuxiemeOkButtonClicked = true;//le boutton 1ere ok est cliqué
            }
        });

        return view;
    }


}