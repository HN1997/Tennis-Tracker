package fr.android.moi.tennistracker;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Historique extends Fragment {

    //Boutton main menu
    Button buttonRetourMainMenu;
    Button viewDataButton;

    //Container match
    LinearLayout containerMatch;
    TextView resumer_match;

    //Database
    DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historique, container, false);

        //Boutton main menu
        buttonRetourMainMenu = view.findViewById(R.id.buttonRetourMainMenu);
        viewDataButton = view.findViewById(R.id.viewDataButton);

        //Container match
        containerMatch = view.findViewById(R.id.container_matchs);
        resumer_match = view.findViewById(R.id.resumer_match);

        //Database
        databaseHelper = new DatabaseHelper(getContext());

        //Listener Boutton main menu
        buttonRetourMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new MainPage());
                fr.commit();
            }
        });

        viewDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printData();
                viewDataButton.setEnabled(false);
            }
        });

        return view;
    }

    public void printData()
    {
        Cursor cursor = databaseHelper.ViewData();
        StringBuilder stringBuilder = new StringBuilder();
        int nbrColonne = cursor.getColumnCount();
        int numeroMatch = 1;

        while (cursor.moveToNext())
        {
            int pointActuelIntJ1 = Integer.parseInt(cursor.getString(1));
            int pointSet1J1 = Integer.parseInt(cursor.getString(2));
            int pointSet2J1 = Integer.parseInt(cursor.getString(3));
            int pointSet3J1 = Integer.parseInt(cursor.getString(4));
            int nbr1ereOkJ1 = Integer.parseInt(cursor.getString(5)); //Quand il sert bien la premiere fois
            int nbr2emeOkJ1 = Integer.parseInt(cursor.getString(6)); //Quand il sert bien la deuxieme fois
            int nbr1AceJ1 = Integer.parseInt(cursor.getString(7)); //Ace au premier service
            int nbr2AceJ1 = Integer.parseInt(cursor.getString(8)); //Ace au deuxieme service
            int nbrDoubleFauteJ1 = Integer.parseInt(cursor.getString(9)); //Nombre de double faute
            int nbrPointGagnantJ1 = Integer.parseInt(cursor.getString(10)); //Il a remporté le point
            int nbrFauteProvoqueeJ1 = Integer.parseInt(cursor.getString(11)); //Il n'a pas remporté le point et a fait une faute provoquée
            int nbrFauteDirectJ1 = Integer.parseInt(cursor.getString(12)); //Il n'a pas remporté le point et a fait une faute directe
            int pointActuelIntJ2 = Integer.parseInt(cursor.getString(13)); //Le nombre de point actuel
            int pointSet1J2 = Integer.parseInt(cursor.getString(14));
            int pointSet2J2 = Integer.parseInt(cursor.getString(15));
            int pointSet3J2 = Integer.parseInt(cursor.getString(16));
            int nbr1ereOkJ2 = Integer.parseInt(cursor.getString(17)); //Quand il sert bien la premiere fois
            int nbr2emeOkJ2 = Integer.parseInt(cursor.getString(18)); //Quand il sert bien la deuxieme fois
            int nbr1AceJ2 = Integer.parseInt(cursor.getString(19)); //Ace au premier service
            int nbr2AceJ2 = Integer.parseInt(cursor.getString(20)); //Ace au deuxieme service
            int nbrDoubleFauteJ2 = Integer.parseInt(cursor.getString(21)); //Nombre de double faute
            int nbrPointGagnantJ2 = Integer.parseInt(cursor.getString(22)); //Il a remporté le point
            int nbrFauteProvoqueeJ2 = Integer.parseInt(cursor.getString(23)); //Il n'a pas remporté le point et a fait une faute provoquée
            int nbrFauteDirectJ2 = Integer.parseInt(cursor.getString(24)); //Il n'a pas remporté le point et a fait une faute directe
            byte[] img1 = cursor.getBlob(28);
            byte[] img2 = cursor.getBlob(29);
            byte[] img3 = cursor.getBlob(30);
            byte[] img4 = cursor.getBlob(31);
            byte[] img5 = cursor.getBlob(32);

            // Titre
            TextView textViewTitre = new TextView(getContext());
            textViewTitre.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewTitre.setText("MATCH : " + cursor.getInt(0));
            textViewTitre.setTextSize(30);
            textViewTitre.setGravity(Gravity.CENTER);
            textViewTitre.setTextColor(Color.BLACK);
            containerMatch.addView(textViewTitre);

            // Titre joueur 1
            TextView textView1 = new TextView(getContext());
            textView1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textView1.setText("- " + cursor.getString(26) + " -");
            textView1.setGravity(Gravity.CENTER);
            textView1.setTextColor(Color.BLACK);
            textView1.setTextSize(20);
            containerMatch.addView(textView1);

            // Ace1 joueur 1
            TextView textViewAce1J1 = new TextView(getContext());
            textViewAce1J1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewAce1J1.setText(nbr1AceJ1 + " / " + (nbr1AceJ1+nbr1ereOkJ1));
            textViewAce1J1.setGravity(Gravity.CENTER);
            textViewAce1J1.setTextColor(Color.BLACK);
            containerMatch.addView(textViewAce1J1);

            // Ace2 joueur 1
            TextView textViewAce2J1 = new TextView(getContext());
            textViewAce2J1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewAce2J1.setText(nbr2AceJ1 + " / " + (nbr1AceJ1+nbr1ereOkJ1));
            textViewAce2J1.setGravity(Gravity.CENTER);
            textViewAce2J1.setTextColor(Color.BLACK);
            containerMatch.addView(textViewAce2J1);

            // Reussite premier service J1
            TextView textView1ereOKJ1 = new TextView(getContext());
            textView1ereOKJ1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textView1ereOKJ1.setText((nbr1AceJ1+nbr1ereOkJ1) + " / " + (nbr1AceJ1+nbr1ereOkJ1+nbr2AceJ1+nbr2emeOkJ1));
            textView1ereOKJ1.setGravity(Gravity.CENTER);
            textView1ereOKJ1.setTextColor(Color.BLACK);
            containerMatch.addView(textView1ereOKJ1);

            // Reussite deuxieme service J1
            TextView textView2emeOKJ1 = new TextView(getContext());
            textView2emeOKJ1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textView2emeOKJ1.setText((nbr2AceJ1+nbr2emeOkJ1) + " / " + (nbr1AceJ1+nbr1ereOkJ1+nbr2AceJ1+nbr2emeOkJ1));
            textView2emeOKJ1.setGravity(Gravity.CENTER);
            textView2emeOKJ1.setTextColor(Color.BLACK);
            containerMatch.addView(textView2emeOKJ1);

            // Double faute J1
            TextView textViewDoubleFauteJ1 = new TextView(getContext());
            textViewDoubleFauteJ1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewDoubleFauteJ1.setText(( nbrDoubleFauteJ1 + " / " + (nbr1AceJ1+nbr1ereOkJ1+nbr2AceJ1+nbr2emeOkJ1)));
            textViewDoubleFauteJ1.setGravity(Gravity.CENTER);
            textViewDoubleFauteJ1.setTextColor(Color.BLACK);
            containerMatch.addView(textViewDoubleFauteJ1);

            // Point Gagnant J1
            TextView textViewPointGagnantJ1 = new TextView(getContext());
            textViewPointGagnantJ1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewPointGagnantJ1.setText(( nbrPointGagnantJ1 + " / " + (nbrPointGagnantJ1+nbrPointGagnantJ2) ));
            textViewPointGagnantJ1.setGravity(Gravity.CENTER);
            textViewPointGagnantJ1.setTextColor(Color.BLACK);
            containerMatch.addView(textViewPointGagnantJ1);

            // Faute Provoquee J1
            TextView textViewFauteProvoqueeJ1 = new TextView(getContext());
            textViewFauteProvoqueeJ1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewFauteProvoqueeJ1.setText(( nbrFauteProvoqueeJ1 + " / " + (nbrFauteProvoqueeJ1+nbrFauteProvoqueeJ2) ));
            textViewFauteProvoqueeJ1.setGravity(Gravity.CENTER);
            textViewFauteProvoqueeJ1.setTextColor(Color.BLACK);
            containerMatch.addView(textViewFauteProvoqueeJ1);

            // Faute Directe J1
            TextView textViewFauteDirecteJ1 = new TextView(getContext());
            textViewFauteDirecteJ1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewFauteDirecteJ1.setText(( nbrFauteDirectJ1 + " / " + (nbrFauteDirectJ1+nbrFauteDirectJ2) ));
            textViewFauteDirecteJ1.setGravity(Gravity.CENTER);
            textViewFauteDirecteJ1.setTextColor(Color.BLACK);
            containerMatch.addView(textViewFauteDirecteJ1);

            // Titre joueur 2
            TextView textView2 = new TextView(getContext());
            textView2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textView2.setText("- " + cursor.getString(27) + " -");
            textView2.setGravity(Gravity.CENTER);
            textView2.setTextColor(Color.BLACK);
            textView2.setTextSize(20);
            containerMatch.addView(textView2);

            // Ace1 joueur 2
            TextView textViewAce1J2 = new TextView(getContext());
            textViewAce1J2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewAce1J2.setText(( nbr1AceJ2 + " / " + (nbr1AceJ2+nbr1ereOkJ2) ));
            textViewAce1J2.setGravity(Gravity.CENTER);
            textViewAce1J2.setTextColor(Color.BLACK);
            containerMatch.addView(textViewAce1J2);

            // Ace2 joueur 2
            TextView textViewAce2J2 = new TextView(getContext());
            textViewAce2J2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewAce2J2.setText(nbr2AceJ2 + " / " + (nbr1AceJ2+nbr1ereOkJ2));
            textViewAce2J2.setGravity(Gravity.CENTER);
            textViewAce2J2.setTextColor(Color.BLACK);
            containerMatch.addView(textViewAce2J2);

            // Reussite premier service J2
            TextView textView1ereOKJ2 = new TextView(getContext());
            textView1ereOKJ2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textView1ereOKJ2.setText((nbr1AceJ2+nbr1ereOkJ2) + " / " + (nbr1AceJ2+nbr1ereOkJ2+nbr2AceJ2+nbr2emeOkJ2));
            textView1ereOKJ2.setGravity(Gravity.CENTER);
            textView1ereOKJ2.setTextColor(Color.BLACK);
            containerMatch.addView(textView1ereOKJ2);

            // Reussite deuxieme service J2
            TextView textView2emeOKJ2 = new TextView(getContext());
            textView2emeOKJ2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textView2emeOKJ2.setText((nbr2AceJ2+nbr2emeOkJ2) + " / " + (nbr1AceJ2+nbr1ereOkJ2+nbr2AceJ2+nbr2emeOkJ2));
            textView2emeOKJ2.setGravity(Gravity.CENTER);
            textView2emeOKJ2.setTextColor(Color.BLACK);
            containerMatch.addView(textView2emeOKJ2);

            // Double faute J2
            TextView textViewDoubleFauteJ2 = new TextView(getContext());
            textViewDoubleFauteJ2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewDoubleFauteJ2.setText(nbrDoubleFauteJ2 + " / " + (nbr1AceJ1+nbr1ereOkJ1+nbr2AceJ1+nbr2emeOkJ1));
            textViewDoubleFauteJ2.setGravity(Gravity.CENTER);
            textViewDoubleFauteJ2.setTextColor(Color.BLACK);
            containerMatch.addView(textViewDoubleFauteJ2);

            // Point Gagnant J2
            TextView textViewPointGagnantJ2 = new TextView(getContext());
            textViewPointGagnantJ2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewPointGagnantJ2.setText(nbrPointGagnantJ2 + " / " + (nbrPointGagnantJ2+nbrPointGagnantJ1));
            textViewPointGagnantJ2.setGravity(Gravity.CENTER);
            textViewPointGagnantJ2.setTextColor(Color.BLACK);
            containerMatch.addView(textViewPointGagnantJ2);

            // Faute Provoquee J2
            TextView textViewFauteProvoqueeJ2 = new TextView(getContext());
            textViewFauteProvoqueeJ2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewFauteProvoqueeJ2.setText(nbrFauteProvoqueeJ2 + " / " + (nbrFauteProvoqueeJ2+nbrFauteProvoqueeJ1));
            textViewFauteProvoqueeJ2.setGravity(Gravity.CENTER);
            textViewFauteProvoqueeJ2.setTextColor(Color.BLACK);
            containerMatch.addView(textViewFauteProvoqueeJ2);

            // Faute Directe J2
            TextView textViewFauteDirecteJ2 = new TextView(getContext());
            textViewFauteDirecteJ2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewFauteDirecteJ2.setText(nbrFauteDirectJ2 + " / " + (nbrFauteDirectJ2+nbrFauteDirectJ1));
            textViewFauteDirecteJ2.setGravity(Gravity.CENTER);
            textViewFauteDirecteJ2.setTextColor(Color.BLACK);
            containerMatch.addView(textViewFauteDirecteJ2);

            // Titre Localisation du match
            TextView textViewTitreLocalisation = new TextView(getContext());
            textViewTitreLocalisation.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewTitreLocalisation.setText("- Localisation du match -");
            textViewTitreLocalisation.setGravity(Gravity.CENTER);
            textViewTitreLocalisation.setTextColor(Color.BLACK);
            textViewTitreLocalisation.setTextSize(20);
            containerMatch.addView(textViewTitreLocalisation);

            // Localisation du match
            TextView textViewLocalisation = new TextView(getContext());
            textViewLocalisation.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewLocalisation.setText(cursor.getString(25));
            textViewLocalisation.setGravity(Gravity.CENTER);
            textViewLocalisation.setTextColor(Color.BLACK);
            containerMatch.addView(textViewLocalisation);

            // Titre Image du match
            TextView textViewTitreImage = new TextView(getContext());
            textViewTitreImage.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textViewTitreImage.setText("- IMAGES DU MATCH -");
            textViewTitreImage.setGravity(Gravity.CENTER);
            textViewTitreImage.setTextColor(Color.BLACK);
            textViewTitreImage.setTextSize(20);
            containerMatch.addView(textViewTitreImage);

            // Image 1
            Bitmap bitmap = BitmapFactory.decodeByteArray(img1, 0, img1.length);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    600,
                    600
            );
            if(bitmap != null)
            {
                ImageView img = new ImageView(getContext());
                img.setImageBitmap(bitmap);
                img.setLayoutParams(params);
                containerMatch.addView(img);
            }

            // Image 2
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(img2, 0, img2.length);
            if(bitmap2 != null)
            {
                ImageView img22 = new ImageView(getContext());
                img22.setImageBitmap(bitmap2);
                img22.setLayoutParams(params);
                containerMatch.addView(img22);
            }

            // Image 3
            Bitmap bitmap3 = BitmapFactory.decodeByteArray(img3, 0, img3.length);
            if(bitmap3!=null)
            {
                ImageView img33 = new ImageView(getContext());
                img33.setImageBitmap(bitmap3);
                img33.setLayoutParams(params);
                containerMatch.addView(img33);
            }

            // Image 4
            Bitmap bitmap4 = BitmapFactory.decodeByteArray(img4, 0, img4.length);
            if(bitmap4!= null)
            {
                ImageView img44 = new ImageView(getContext());
                img44.setImageBitmap(bitmap4);
                img44.setLayoutParams(params);
                containerMatch.addView(img44);
            }

            // Image 5
            Bitmap bitmap5 = BitmapFactory.decodeByteArray(img5, 0, img5.length);
            if(bitmap5!=null)
            {
                ImageView img55 = new ImageView(getContext());
                img55.setImageBitmap(bitmap5);
                img55.setLayoutParams(params);
                containerMatch.addView(img55);
            }
        }
        resumer_match.setText(stringBuilder);
    }
}