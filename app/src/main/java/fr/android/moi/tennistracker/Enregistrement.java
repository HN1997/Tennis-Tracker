package fr.android.moi.tennistracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Enregistrement extends Fragment {
    TextView joueur1Name;
    TextView joueur2Name;
    boolean premiereOkButtonClicked = false; //Si le boutton 1ere ok est cliqué
    boolean deuxiemeOkButtonClicked = false; //Si le boutton 2eme ok est cliqué
    boolean joueur1isServing;
    boolean finDuMatch;

    //Buttons
    Button premiereOK;
    Button deuxiemeOK;
    Button ace1;
    Button ace2;
    Button doubleFaute;
    Button pointGagnantJ1;
    Button fauteProvoqueeJ1;
    Button fauteDirecteJ1;
    Button pointGagnantJ2;
    Button fauteProvoqueeJ2;
    Button fauteDirecteJ2;
    Button buttonLocation;

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
    TextView pointActuelJ1;
    TextView set1J1;
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

    //Location
    TextView locationTV;
    FusedLocationProviderClient fusedLocationProviderClient;

    //Pictures
    Button buttonPicture;
    LinearLayout container_layout_images;

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
        premiereOK = view.findViewById(R.id.premiereOK);
        deuxiemeOK = view.findViewById(R.id.deuxiemeOK);
        ace1 = view.findViewById(R.id.ace1);
        ace2 = view.findViewById(R.id.ace2);
        doubleFaute = view.findViewById(R.id.doubleFaute);
        pointGagnantJ1 = view.findViewById(R.id.pointGagnantJ1);
        fauteProvoqueeJ1 = view.findViewById(R.id.fauteProvoqueeJ1);
        fauteDirecteJ1 = view.findViewById(R.id.fauteDirecteJ1);
        pointGagnantJ2 = view.findViewById(R.id.pointGagnantJ2);
        fauteProvoqueeJ2 = view.findViewById(R.id.fauteProvoqueeJ2);
        fauteDirecteJ2 = view.findViewById(R.id.fauteDirecteJ2);

        //Location
        buttonLocation = view.findViewById(R.id.button_location);
        locationTV = view.findViewById(R.id.locationTV);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        //Picture
        buttonPicture = view.findViewById(R.id.button_Picture);
        container_layout_images = view.findViewById(R.id.container_layout_images);

        //Changing name player1, nameplayer2, person who serves first with the bundle
        joueur1Name.setText(bundle.getString("j1Text"));
        joueur2Name.setText(bundle.getString("j2Text"));
        joueur1isServing = bundle.getBoolean("player1Serving");

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
        if (joueur1isServing) {
            joueur1Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tennis_ball, 0, 0, 0); //ajout de l'image de la balle de tennis au j1
            serveurTitle.setText("Serveur : " + joueur1Name.getText());
        } else {
            joueur2Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tennis_ball, 0, 0, 0); //ajout de l'image de la balle de tennis au j2
            serveurTitle.setText("Serveur : " + joueur2Name.getText());
        }

        premiereOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                premiereOK.setBackgroundColor(Color.rgb(75, 105, 6)); //On change la couleur du boutton 1ere ok (couleur enfoncée)
                deuxiemeOK.setBackgroundColor(Color.rgb(255, 217, 17)); //On change la couleur du boutton 2eme ok (couleur relachée = par défaut)
                premiereOkButtonClicked = true; //le boutton 1ere ok est cliqué
                deuxiemeOkButtonClicked = false;//le boutton 1ere ok est cliqué
            }
        });

        deuxiemeOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                premiereOK.setBackgroundColor(Color.rgb(125, 195, 0)); //On change la couleur du boutton 1ere ok (couleur relachée = par défaut)
                deuxiemeOK.setBackgroundColor(Color.rgb(255, 139, 15)); //On change la couleur du boutton 2eme ok (couleur relachée = par défaut)
                premiereOkButtonClicked = false; //le boutton 1ere ok est cliqué
                deuxiemeOkButtonClicked = true;//le boutton 1ere ok est cliqué
            }
        });

        ace1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutPoint(joueur1isServing, true, true);
            }
        });

        ace2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutPoint(joueur1isServing, true, false);
            }
        });

        doubleFaute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutPoint(!joueur1isServing, false, false);
                if (joueur1isServing)
                    nbrDoubleFauteJ1++;
                else
                    nbrDoubleFauteJ2++;
            }
        });

        pointGagnantJ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutPoint(true, false, false);
                nbrPointGagnantJ1++;
            }
        });

        pointGagnantJ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutPoint(false, false, false);
                nbrPointGagnantJ2++;
            }
        });

        fauteProvoqueeJ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutPoint(false, false, false);
                nbrFauteProvoqueeJ1++;
            }
        });

        fauteProvoqueeJ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutPoint(true, false, false);
                nbrFauteProvoqueeJ2++;
            }
        });

        fauteDirecteJ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutPoint(false, false, false);
                nbrFauteDirectJ1++;
            }
        });

        fauteDirecteJ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutPoint(true, false, false);
                nbrFauteDirectJ2++;
            }
        });

        return view;
    }

    public void ajoutPoint(boolean joueur1IsMarking, boolean isAce, boolean aceFirst) {
        //Si ce n'est pas la fin du match
        if (!finDuMatch) {
            //Si c'est le joueur 1 qui marque le point
            if (joueur1IsMarking) {
                //Reinitialisation des couleurs de 1ere OK et 2eme OK
                deuxiemeOK.setBackgroundColor(Color.rgb(255, 217, 17)); //On change la couleur du boutton 2eme ok (couleur relachée = par défaut)
                premiereOK.setBackgroundColor(Color.rgb(125, 195, 0)); //On change la couleur du boutton 1ere ok (couleur relachée = par défaut)

                //Si le boutton premiere ok est selectionne et que ca ne provient pas d'un ACE
                if (premiereOkButtonClicked && !isAce && joueur1isServing) // Ce n'est pas un ace, la 1ere est OK et J1 sert
                {
                    nbr1ereOkJ1++;
                    nbrPointGagnantJ1++;
                } else if (deuxiemeOkButtonClicked && !isAce && joueur1isServing) // Ce n'est pas un ace, la 2eme est OK et J1 sert
                {
                    nbr2emeOkJ1++;
                    nbrPointGagnantJ1++;
                } else if (isAce && joueur1isServing && aceFirst) //Le jouer 1 sert, fait un ace et c'est son premier service
                {
                    nbr1AceJ1++;
                } else if (isAce && joueur1isServing && !aceFirst) //Le jouer 1 sert, fait un ace et ce n'est pas son premier service
                {
                    nbr2AceJ1++;
                }

                //Reinitialisation de la valeur true/false des bouttons 1ereok et 2eme ok
                premiereOkButtonClicked = false;
                deuxiemeOkButtonClicked = false;

                // Si il a 0 point
                if (pointActuelIntJ1 == 0) {
                    pointActuelIntJ1 = 15;
                    pointActuelJ1.setText("15");
                }
                //Si il a 15 points
                else if (pointActuelIntJ1 == 15) {
                    pointActuelIntJ1 = 30;
                    pointActuelJ1.setText("30");
                }
                //Si il a 30 points
                else if (pointActuelIntJ1 == 30) {
                    pointActuelIntJ1 = 40;
                    pointActuelJ1.setText("40");
                }
                //Si il a 40 points ou 50 points
                else if (pointActuelIntJ1 == 40 || pointActuelIntJ1 == 50) {
                    //Si les deux joueurs ont 40 points
                    if (pointActuelIntJ2 == 40 && pointActuelIntJ1 == 40) {
                        pointActuelIntJ1 = 50; //On lui mets 50 points ce qui voudra dire "Avantage
                        pointActuelJ1.setText("AD");
                    }
                    //Si l'adversaire a lui avantage
                    else if (pointActuelIntJ2 == 50 && pointActuelIntJ1 == 40) {
                        pointActuelIntJ2 = 40; //On decremente le nombre de points de J2
                        pointActuelJ2.setText("40");
                    }
                    //Sinon, le joueur a remporté le point
                    else {
                        //On reinitialise les points
                        pointActuelIntJ1 = pointActuelIntJ2 = 0;
                        pointActuelJ1.setText("0");
                        pointActuelJ2.setText("0");

                        //C'est a l'adversaire de servir
                        joueur1isServing = !joueur1isServing;
                        if (joueur1isServing) {
                            joueur1Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tennis_ball, 0, 0, 0); //ajout de l'image de la balle de tennis au j1
                            joueur2Name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); //ajout de l'image de la balle de tennis au j1
                            serveurTitle.setText("Serveur : " + joueur1Name.getText());
                        } else {
                            joueur1Name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); //ajout de l'image de la balle de tennis au j1
                            joueur2Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tennis_ball, 0, 0, 0); //ajout de l'image de la balle de tennis au j1
                            serveurTitle.setText("Serveur : " + joueur2Name.getText());
                        }

                        //Si au Set 1 il n'a pas encore 6 points et qu'on est au set 1
                        if (pointSet1J1 < 6 && currentSetPlaying == 1) {
                            pointSet1J1++;
                            set1J1.setText(String.valueOf(pointSet1J1));

                            //Si il a maintenant 6 points
                            if (pointSet1J1 == 6) {
                                vainqueurSet1 = 1;
                                currentSetPlaying = 2; //on passe au set2
                            }
                        }
                        //Si au Set 2 il n'a pas encore 6 points
                        else if (pointSet2J1 < 6 && currentSetPlaying == 2) {
                            pointSet2J1++;
                            set2J1.setText(String.valueOf(pointSet2J1));

                            //Si il a 6 points, il remporte le set
                            if (pointSet2J1 == 6) {
                                vainqueurSet2 = 1;

                                //Si il a remporté le set 1 et 2, il a gagné le match
                                if (vainqueurSet1 == 1 && vainqueurSet2 == 1) {
                                    finDuMatch = true;
                                    pointActuelJ1.setText("0");
                                    pointActuelJ2.setText("0");
                                    Toast.makeText(getActivity(), "Winner is : " + joueur1Name.getText() + ", you can save and quit", Toast.LENGTH_LONG).show();

                                }
                                //Sinon on doit passer au set3
                                else {
                                    currentSetPlaying = 3; //on passe au set3
                                }
                            }
                        }
                        //Si au Set 3 il n'a pas encore 6 points
                        else if (pointSet3J1 < 6 && currentSetPlaying == 3) {
                            pointSet3J1++;
                            set3J1.setText(String.valueOf(pointSet3J1));

                            //Si il a 6 points, il a gagné le match
                            if (pointSet3J1 == 6) {
                                finDuMatch = true;
                                pointActuelJ1.setText("0");
                                pointActuelJ2.setText("0");
                                Toast.makeText(getActivity(), "Winner is : " + joueur1Name.getText() + ", you can save and quit", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }

            //Sinon, c'est le joueur 2 qui marque
            else {
                //Reinitialisation des couleurs de 1ere OK et 2eme OK
                deuxiemeOK.setBackgroundColor(Color.rgb(255, 217, 17)); //On change la couleur du boutton 2eme ok (couleur relachée = par défaut)
                premiereOK.setBackgroundColor(Color.rgb(125, 195, 0)); //On change la couleur du boutton 1ere ok (couleur relachée = par défaut)

                //Si le boutton premiere ok est selectionne et que ca ne provient pas d'un ACE
                if (premiereOkButtonClicked && !isAce && !joueur1isServing) // Ce n'est pas un ace, la 1ere est OK et J2 sert
                {
                    nbr1ereOkJ2++;
                    nbrPointGagnantJ2++;
                } else if (deuxiemeOkButtonClicked && !isAce && !joueur1isServing) // Ce n'est pas un ace, la 2eme est OK et J2 sert
                {
                    nbr2emeOkJ2++;
                    nbrPointGagnantJ2++;
                } else if (isAce && !joueur1isServing && aceFirst) //Le jouer 2 sert, fait un ace et c'est son premier service
                {
                    nbr1AceJ2++;
                } else if (isAce && !joueur1isServing && !aceFirst) //Le jouer 2 sert, fait un ace et ce n'est pas son premier service
                {
                    nbr2AceJ2++;
                }

                //Reinitialisation de la valeur true/false des bouttons 1ereok et 2eme ok
                premiereOkButtonClicked = false;
                deuxiemeOkButtonClicked = false;

                // Si il a 0 point
                if (pointActuelIntJ2 == 0) {
                    pointActuelIntJ2 = 15;
                    pointActuelJ2.setText("15");
                }
                //Si il a 15 points
                else if (pointActuelIntJ2 == 15) {
                    pointActuelIntJ2 = 30;
                    pointActuelJ2.setText("30");
                }
                //Si il a 30 points
                else if (pointActuelIntJ2 == 30) {
                    pointActuelIntJ2 = 40;
                    pointActuelJ2.setText("40");
                }
                //Si il a 40 points ou 50 points
                else if (pointActuelIntJ2 == 40 || pointActuelIntJ2 == 50) {

                    //Si les deux joueurs ont 40 points
                    if (pointActuelIntJ1 == 40 && pointActuelIntJ2 == 40) {
                        pointActuelIntJ2 = 50; //On lui mets 50 points ce qui voudra dire "Avantage
                        pointActuelJ2.setText("AD");
                    }
                    //Si l'adversaire a lui avantage
                    else if (pointActuelIntJ1 == 50 && pointActuelIntJ2 == 40) {
                        pointActuelIntJ1 = 40; //On decremente le nombre de points de J1
                        pointActuelJ1.setText("40");
                    }
                    //Sinon, le joueur 2 a remporté le point
                    else {
                        //On reinitialise les points
                        pointActuelIntJ2 = pointActuelIntJ1 = 0;
                        pointActuelJ2.setText("0");
                        pointActuelJ1.setText("0");

                        //C'est a l'adversaire de servir
                        joueur1isServing = !joueur1isServing;
                        if (joueur1isServing) {
                            joueur1Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tennis_ball, 0, 0, 0); //ajout de l'image de la balle de tennis au j1
                            joueur2Name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); //ajout de l'image de la balle de tennis au j1
                            serveurTitle.setText("Serveur : " + joueur1Name.getText());
                        } else {
                            joueur1Name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); //ajout de l'image de la balle de tennis au j1
                            joueur2Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tennis_ball, 0, 0, 0); //ajout de l'image de la balle de tennis au j1
                            serveurTitle.setText("Serveur : " + joueur2Name.getText());
                        }

                        //Si au Set 1 il n'a pas encore 6 points et qu'on est au set 1
                        if (pointSet1J2 < 6 && currentSetPlaying == 1) {
                            pointSet1J2++;
                            set1J2.setText(String.valueOf(pointSet1J2));

                            //Si il a maintenant 6 points
                            if (pointSet1J2 == 6) {
                                vainqueurSet1 = 2;
                                currentSetPlaying = 2; //on passe au set2
                            }
                        }
                        //Si au Set 2 il n'a pas encore 6 points
                        else if (pointSet2J2 < 6 && currentSetPlaying == 2) {
                            pointSet2J2++;
                            set2J2.setText(String.valueOf(pointSet2J2));

                            //Si il a 6 points, il remporte le set
                            if (pointSet2J2 == 6) {
                                vainqueurSet2 = 2;

                                //Si il a remporté le set 1 et 2, il a gagné le match
                                if (vainqueurSet1 == 2 && vainqueurSet2 == 2) {
                                    finDuMatch = true;
                                    pointActuelJ2.setText("0");
                                    pointActuelJ1.setText("0");
                                    Toast.makeText(getActivity(), "Winner is : " + joueur2Name.getText() + ", you can save and quit", Toast.LENGTH_LONG).show();

                                }
                                //Sinon on doit passer au set3
                                else {
                                    currentSetPlaying = 3; //on passe au set3
                                }
                            }
                        }
                        //Si au Set 3 il n'a pas encore 6 points
                        else if (pointSet3J2 < 6 && currentSetPlaying == 3) {
                            pointSet3J2++;
                            set3J2.setText(String.valueOf(pointSet3J2));

                            //Si il a 6 points, il a gagné le match
                            if (pointSet3J2 == 6) {
                                finDuMatch = true;
                                pointActuelJ1.setText("0");
                                pointActuelJ2.setText("0");
                                Toast.makeText(getActivity(), "Winner is : " + joueur2Name.getText() + ", you can save and quit", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        //initialize geocoder
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        //Initialize address list
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        //Set text view
                        locationTV.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Address:</b><br></font>"
                                + addresses.get(0).getAddressLine(0)
                        ));

                        //Remove button
                        ViewGroup layout = (ViewGroup) buttonLocation.getParent();
                        if(null!=layout) //for safety only  as you are doing onClick
                            layout.removeView(buttonLocation);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Prendre des photos bouttons
        buttonPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si il n'a pas la permission de la camera
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    //On la lui demande
                    ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.CAMERA
                    }, 100);
                }
                //Il a la permission
                else
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 100);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100)
        {
            //Get capture image
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            //Set capture image
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    600,
                    600
            );
            params.setMargins(0,20,0,0);
            imageView.setLayoutParams(params);
            imageView.setImageBitmap(captureImage);

            container_layout_images.addView(imageView);
        }
    }
}