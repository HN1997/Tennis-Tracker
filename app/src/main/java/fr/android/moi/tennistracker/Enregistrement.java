package fr.android.moi.tennistracker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
    boolean finDuMatch;

    //Statistiques J1 //

    int pointActuelIntJ1; //Le nombre de point actuel
    int pointSet1J1;
    int pointSet2J1;
    int pointSet3J1;
    int nbr1ereOkJ1; //Quand il sert bien la premiere fois
    int nbr2emeOkJ1; //Quand il sert bien la deuxieme fois
    int nbr1AceJ1; //Ace au premier service
    int nbr2AceJ1; //Ace au deuxieme service
    int nbrDoubleFauteJ1; //Nombre de double faute
    int nbrPointGagnantJ1; //Il a remporté le point
    int nbrFauteProvoqueeJ1; //Il n'a pas remporté le point et a fait une faute provoquée
    int nbrFauteDirectJ1; //Il n'a pas remporté le point et a fait une faute directe
    boolean J1isWinner;
    TextView pointActuelJ1 ;
    TextView set1J1 ;
    TextView set2J1;
    TextView set3J1;

    //Statistiques J2 //

    int pointActuelIntJ2; //Le nombre de point actuel
    int pointSet1J2;
    int pointSet2J2;
    int pointSet3J2;
    int nbr1ereOkJ2; //Quand il sert bien la premiere fois
    int nbr2emeOkJ2; //Quand il sert bien la deuxieme fois
    int nbr1AceJ2; //Ace au premier service
    int nbr2AceJ2; //Ace au deuxieme service
    int nbrDoubleFauteJ2; //Nombre de double faute
    int nbrPointGagnantJ2; //Il a remporté le point
    int nbrFauteProvoqueeJ2; //Il n'a pas remporté le point et a fait une faute provoquée
    int nbrFauteDirectJ2; //Il n'a pas remporté le point et a fait une faute directe
    boolean J2isWinner;
    TextView pointActuelJ2;
    TextView set1J2;
    TextView set2J2;
    TextView set3J2;

    //Titre du joueur actuel qui sert
    TextView serveurTitle;

    //Current set playing (entre 1 et 3)
    int currentSetPlaying;

    //Vainqueur des sets (1 pour joueur 1, 2 pour joueur 2)
    int vainqueurSet1;
    int vainqueurSet2;
    int vainqueurSet3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enregistrement, container, false);

        //Bundle to get data from NouveauMatch
        Bundle bundle = this.getArguments();

        //TextView J1
        joueur1Name = view.findViewById(R.id.joueur1Name);
        pointActuelJ1 = view.findViewById(R.id.pointActuelJ1);
        set1J1 = view.findViewById(R.id.set1J1);
        set2J1 = view.findViewById(R.id.set2J1);
        set3J1 = view.findViewById(R.id.set3J1);

        //TextView J2
        joueur2Name = view.findViewById(R.id.joueur2Name);
        pointActuelJ2 = view.findViewById(R.id.pointActuelJ2);
        set1J2 = view.findViewById(R.id.set1J2);
        set2J2 = view.findViewById(R.id.set2J2);
        set3J2 = view.findViewById(R.id.set3J2);

        //Titre du joueur actuel qui sert
        serveurTitle = view.findViewById(R.id.serveurTitle);

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

        //Fin du match boolean
        finDuMatch = false;

        //Initialisation des points
        pointActuelIntJ1 = pointSet1J1 = pointSet2J1 = pointSet3J1 = 0; //J1
        pointActuelIntJ2 = pointSet1J2 = pointSet2J2 = pointSet3J2 = 0; //J2

        //Personne n'est encore winner
        J1isWinner = false;
        J2isWinner = false;

        //Current set playing (entre 1 et 3)
        currentSetPlaying = 1;

        //Vainqueur des sets (1 pour joueur 1, 2 pour joueur 2)
        vainqueurSet1 = vainqueurSet2 = vainqueurSet3 = 0;

        //Image de la balle de tennis
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

        ace1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ajoutPoint();
            }
        });

        return view;
    }

    public void ajoutPoint()
    {
        //Si ce n'est pas la fin du match
        if(!finDuMatch)
        {
            //Si c'est le joueur 1 qui sert
            if(joueur1isServing)
            {
                // Si il a 0 point
                if(pointActuelIntJ1 == 0)
                {
                    pointActuelIntJ1 = 15;
                    pointActuelJ1.setText("15");
                }
                //Si il a 15 points
                else if(pointActuelIntJ1 == 15)
                {
                    pointActuelIntJ1 = 30;
                    pointActuelJ1.setText("30");
                }
                //Si il a 30 points
                else if(pointActuelIntJ1 == 30)
                {
                    pointActuelIntJ1 = 40;
                    pointActuelJ1.setText("40");
                }
                //Si il a 40 points ou 50 points
                else if(pointActuelIntJ1 == 40 || pointActuelIntJ1 == 50)
                {
                    //Si les deux joueurs ont 40 points
                    if(pointActuelIntJ2 == 40 && pointActuelIntJ1 == 40)
                    {
                        pointActuelIntJ1 = 50; //On lui mets 50 points ce qui voudra dire "Avantage
                        pointActuelJ1.setText("AD");
                    }
                    //Si l'adversaire a lui avantage
                    else if(pointActuelIntJ2 == 50 && pointActuelIntJ1 == 40)
                    {
                        pointActuelIntJ2 = 40; //On decremente le nombre de points de J2
                        pointActuelJ2.setText("40");
                    }
                    //Sinon, le joueur a remporté le point
                    else
                    {
                        //On reinitialise les points
                        pointActuelIntJ1 = pointActuelIntJ2 = 0;
                        pointActuelJ1.setText("0");
                        pointActuelJ2.setText("0");

                        //C'est a l'adversaire de servir
                        joueur1isServing = false;
                        joueur1Name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); //ajout de l'image de la balle de tennis au j1
                        joueur2Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tennis_ball, 0, 0, 0); //ajout de l'image de la balle de tennis au j1
                        serveurTitle.setText("Serveur : " + joueur2Name.getText());

                        //Si au Set 1 il n'a pas encore 6 points et qu'on est au set 1
                        if(pointSet1J1<6 && currentSetPlaying == 1)
                        {
                            pointSet1J1++;
                            set1J1.setText(String.valueOf(pointSet1J1));

                            //Si il a maintenant 6 points
                            if(pointSet1J1==6)
                            {
                                vainqueurSet1 = 1;
                                currentSetPlaying = 2; //on passe au set2
                            }
                        }
                        //Si au Set 2 il n'a pas encore 6 points
                        else if(pointSet2J1<6 && currentSetPlaying == 2)
                        {
                            pointSet2J1++;
                            set2J1.setText(String.valueOf(pointSet2J1));

                            //Si il a 6 points, il remporte le set
                            if(pointSet2J1 == 6)
                            {
                                vainqueurSet2 = 1;

                                //Si il a remporté le set 1 et 2, il a gagné le match
                                if(vainqueurSet1 == 1 && vainqueurSet2 == 1)
                                {
                                    finDuMatch = true;
                                    pointActuelJ1.setText("0");
                                    pointActuelJ2.setText("0");

                                }
                                //Sinon on doit passer au set3
                                else
                                {
                                    currentSetPlaying =3; //on passe au set3
                                }
                            }
                        }
                        //Si au Set 3 il n'a pas encore 6 points
                        else if(pointSet3J1<6 && currentSetPlaying == 3)
                        {
                            pointSet3J1++;
                            set3J1.setText(String.valueOf(pointSet3J1));

                            //Si il a 6 points, il a gagné le match
                            if(pointSet3J1 == 6)
                            {
                                finDuMatch = true;
                                pointActuelJ1.setText("0");
                                pointActuelJ2.setText("0");
                            }
                        }
                    }
                }
            }

            //Sinon, c'est le joueur 2 qui sert
            else
            {
                // Si il a 0 point
                if(pointActuelIntJ2 == 0)
                {
                    pointActuelIntJ2 = 15;
                    pointActuelJ2.setText("15");
                }
                //Si il a 15 points
                else if(pointActuelIntJ2 == 15)
                {
                    pointActuelIntJ2 = 30;
                    pointActuelJ2.setText("30");
                }
                //Si il a 30 points
                else if(pointActuelIntJ2 == 30)
                {
                    pointActuelIntJ2 = 40;
                    pointActuelJ2.setText("40");
                }
                //Si il a 40 points ou 50 points
                else if(pointActuelIntJ2 == 40 || pointActuelIntJ1 == 50)
                {
                    //Si les deux joueurs ont 40 points
                    if(pointActuelIntJ1 == 40 && pointActuelIntJ2 == 40)
                    {
                        pointActuelIntJ2 = 50; //On lui mets 50 points ce qui voudra dire "Avantage
                        pointActuelJ2.setText("AD");
                    }
                    //Si l'adversaire a lui avantage
                    else if(pointActuelIntJ1 == 50 && pointActuelIntJ2 == 40)
                    {
                        pointActuelIntJ1 = 40; //On decremente le nombre de points de J2
                        pointActuelJ1.setText("40");
                    }
                    //Sinon, le joueur a remporté le point
                    else
                    {
                        //On reinitialise les points
                        pointActuelIntJ2 = pointActuelIntJ1 = 0;
                        pointActuelJ2.setText("0");
                        pointActuelJ1.setText("0");

                        //C'est a l'adversaire de servir
                        joueur1isServing = true;
                        joueur1Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tennis_ball, 0, 0, 0); //ajout de l'image de la balle de tennis au j1
                        joueur2Name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); //ajout de l'image de la balle de tennis au j1
                        serveurTitle.setText("Serveur : " + joueur1Name.getText());

                        //Si au Set 1 il n'a pas encore 6 points et qu'on est au set 1
                        if(pointSet1J2<6 && currentSetPlaying == 1)
                        {
                            pointSet1J2++;
                            set1J2.setText(String.valueOf(pointSet1J2));

                            //Si il a maintenant 6 points
                            if(pointSet1J2==6)
                            {
                                vainqueurSet1 = 2;
                                currentSetPlaying = 2; //on passe au set2
                            }
                        }
                        //Si au Set 2 il n'a pas encore 6 points
                        else if(pointSet2J2<6 && currentSetPlaying == 2)
                        {
                            pointSet2J2++;
                            set2J2.setText(String.valueOf(pointSet2J2));

                            //Si il a 6 points, il remporte le set
                            if(pointSet2J2 == 6)
                            {
                                vainqueurSet2 = 1;

                                //Si il a remporté le set 1 et 2, il a gagné le match
                                if(vainqueurSet1 == 2 && vainqueurSet2 == 2)
                                {
                                    finDuMatch = true;
                                    pointActuelJ2.setText("0");
                                    pointActuelJ1.setText("0");

                                }
                                //Sinon on doit passer au set3
                                else
                                {
                                    currentSetPlaying = 3; //on passe au set3
                                }
                            }
                        }
                        //Si au Set 3 il n'a pas encore 6 points
                        else if(pointSet3J2<6 && currentSetPlaying == 3)
                        {
                            pointSet3J2++;
                            set3J2.setText(String.valueOf(pointSet3J2));

                            //Si il a 6 points, il a gagné le match
                            if(pointSet3J2 == 6)
                            {
                                finDuMatch = true;
                                pointActuelJ1.setText("0");
                                pointActuelJ2.setText("0");
                            }
                        }
                    }
                }
            }
        }
    }

}