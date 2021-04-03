package fr.android.moi.tennistracker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class BackgroundDatabase extends AsyncTask<Object, Void, Boolean> {
    Context c;
    Activity a;
    DatabaseHelper databaseHelper;
    public BackgroundDatabase(Context c, Activity a)
    {
        this.c = c;
        this.a = a ;
        databaseHelper = new DatabaseHelper(c);
    }

    @Override
    protected Boolean doInBackground(Object... obj) {
        String method = (String) obj[0];
        boolean insertData = false;
        if(method.equals("add_info"))
        {
            String pointActuelIntJ1s = (String) obj[1];
            String pointSet1J1s = (String) obj[2];
            String pointSet2J1s = (String) obj[3];
            String pointSet3J1s = (String) obj[4];
            String nbr1ereOkJ1s = (String) obj[5];
            String nbr2emeOkJ1s = (String) obj[6];
            String nbr1AceJ1s =(String) obj[7];
            String nbr2AceJ1s = (String) obj[8];
            String nbrDoubleFauteJ1s = (String) obj[9];
            String nbrPointGagnantJ1s = (String) obj[10];
            String nbrFauteProvoqueeJ1s = (String) obj[11];
            String nbrFauteDirectJ1s = (String) obj[12];

            String pointActuelIntJ2s = (String) obj[13];
            String pointSet1J2s = (String) obj[14];
            String pointSet2J2s = (String) obj[15];
            String pointSet3J2s = (String) obj[16];
            String nbr1ereOkJ2s =(String) obj[17];
            String nbr2emeOkJ2s = (String) obj[18];
            String nbr1AceJ2s = (String) obj[19];
            String nbr2AceJ2s = (String) obj[20];
            String nbrDoubleFauteJ2s = (String) obj[21];
            String nbrPointGagnantJ2s = (String) obj[22];
            String nbrFauteProvoqueeJ2s = (String) obj[23];
            String nbrFauteDirectJ2s = (String) obj[24];
            String locationMatch = (String) obj[25];
            String joueur1Name = (String) obj[26];
            String joueur2Name = (String) obj[27];
            ArrayList<Bitmap> array = (ArrayList<Bitmap>) obj[28];

            insertData =  databaseHelper.addData(pointActuelIntJ1s, pointSet1J1s, pointSet2J1s, pointSet3J1s, nbr1ereOkJ1s, nbr2emeOkJ1s,
                    nbr1AceJ1s, nbr2AceJ1s, nbrDoubleFauteJ1s, nbrPointGagnantJ1s, nbrFauteProvoqueeJ1s, nbrFauteDirectJ1s, pointActuelIntJ2s,
                    pointSet1J2s, pointSet2J2s, pointSet3J2s, nbr1ereOkJ2s, nbr2emeOkJ2s, nbr1AceJ2s, nbr2AceJ2s, nbrDoubleFauteJ2s,
                    nbrPointGagnantJ2s, nbrFauteProvoqueeJ2s, nbrFauteDirectJ2s, locationMatch, joueur1Name, joueur2Name,
                    array);
            if(insertData)
                a.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(a, "Match saved successfuly", Toast.LENGTH_LONG);
                    }
                });

        }
        return insertData;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
