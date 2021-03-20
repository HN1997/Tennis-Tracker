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

            stringBuilder.append("Statistiques du match numéro  " + numeroMatch + "\n");
            stringBuilder.append("Joueur : " + cursor.getColumnName(26) + "\n");
            stringBuilder.append("  Ace 1er service: " +  nbr1AceJ1 + " / " + (nbr1AceJ1+nbr1ereOkJ1)  + "\n");
            stringBuilder.append("  Ace 2eme service: " + nbr2AceJ1 + " / " + (nbr1AceJ1+nbr1ereOkJ1) + "\n");
            stringBuilder.append("  Reussite 1er service: " +  (nbr1AceJ1+nbr1ereOkJ1) + " / " + (nbr1AceJ1+nbr1ereOkJ1+nbr2AceJ1+nbr2emeOkJ1)  + "\n");
            stringBuilder.append("  Reussite 2eme service: " +  (nbr2AceJ1+nbr2emeOkJ1) + " / " + (nbr1AceJ1+nbr1ereOkJ1+nbr2AceJ1+nbr2emeOkJ1)  + "\n");
            stringBuilder.append("  Double Faute: " + ( nbrDoubleFauteJ1 + " / " + (nbr1AceJ1+nbr1ereOkJ1+nbr2AceJ1+nbr2emeOkJ1) ) + "\n");
            stringBuilder.append("  Point gagnant: " + ( nbrPointGagnantJ1 + " / " + (nbrPointGagnantJ1+nbrPointGagnantJ2) ) + "\n");
            stringBuilder.append("  Faute provoquee: " + ( nbrFauteProvoqueeJ1 + " / " + (nbrFauteProvoqueeJ1+nbrFauteProvoqueeJ2) ) + "\n");
            stringBuilder.append("  Faute directe: " + ( nbrFauteDirectJ1 + " / " + (nbrFauteDirectJ1+nbrFauteDirectJ2) ) + "\n");
            stringBuilder.append("Joueur : " + cursor.getColumnName(27) + "\n");
            stringBuilder.append("  Ace 1er service: " + ( nbr1AceJ2 + " / " + (nbr1AceJ2+nbr1ereOkJ2) ) + "\n");
            stringBuilder.append("  Ace 2eme service: " + ( nbr2AceJ2 + " / " + (nbr1AceJ2+nbr1ereOkJ2) ) + "\n");
            stringBuilder.append("  Reussite 1er service: " + ( (nbr1AceJ2+nbr1ereOkJ2) + " / " + (nbr1AceJ2+nbr1ereOkJ2+nbr2AceJ2+nbr2emeOkJ2) ) + "\n");
            stringBuilder.append("  Reussite 2eme service: " + ( (nbr2AceJ2+nbr2emeOkJ2) + " / " + (nbr1AceJ2+nbr1ereOkJ2+nbr2AceJ2+nbr2emeOkJ2) ) + "\n");
            stringBuilder.append("  Double Faute: " + ( nbrDoubleFauteJ2 + " / " + (nbr1AceJ1+nbr1ereOkJ1+nbr2AceJ1+nbr2emeOkJ1) ) + "\n");
            stringBuilder.append("  Point gagnant: " + ( nbrPointGagnantJ2 + " / " + (nbrPointGagnantJ2+nbrPointGagnantJ1) ) + "\n");
            stringBuilder.append("  Faute provoquee: " + ( nbrFauteProvoqueeJ2 + " / " + (nbrFauteProvoqueeJ2+nbrFauteProvoqueeJ1) ) + "\n");
            stringBuilder.append("  Faute directe: " + ( nbrFauteDirectJ2 + " / " + (nbrFauteDirectJ2+nbrFauteDirectJ1) ) + "\n");
            stringBuilder.append(cursor.getString(25) + "\n");

            numeroMatch++;
            stringBuilder.append("\n");

            Bitmap bitmap = BitmapFactory.decodeByteArray(img1, 0, img1.length);
            ImageView img = new ImageView(getContext());
            img.setImageBitmap(bitmap);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    600,
                    600
            );
            img.setLayoutParams(params);

            if(img.getParent() != null) {
                ((ViewGroup)img.getParent()).removeView(img); // <- fix
            }
            containerMatch.addView(img);
        }
        resumer_match.setText(stringBuilder);
    }
}