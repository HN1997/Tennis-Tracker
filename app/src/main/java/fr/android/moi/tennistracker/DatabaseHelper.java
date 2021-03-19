package fr.android.moi.tennistracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME="players_table";
    private static final String COL1="ID";

    private static final String COL2="pointActuelIntJ1";
    private static final String COL3="pointSet1J1";
    private static final String COL4="pointSet2J1";
    private static final String COL5="pointSet3J1";
    private static final String COL6="nbr1ereOkJ1";
    private static final String COL7="nbr2emeOkJ1";
    private static final String COL8="nbr1AceJ1";
    private static final String COL9="nbr2AceJ1";
    private static final String COL10="nbrDoubleFauteJ1";
    private static final String COL11="nbrPointGagnantJ1";
    private static final String COL12="nbrFauteProvoqueeJ1";
    private static final String COL13="nbrFauteDirectJ1";

    private static final String COL14="pointActuelIntJ2";
    private static final String COL15="pointSet1J2";
    private static final String COL16="pointSet2J2";
    private static final String COL17="pointSet3J2";
    private static final String COL18="nbr1ereOkJ2";
    private static final String COL19="nbr2emeOkJ2";
    private static final String COL20="nbr1AceJ2";
    private static final String COL21="nbr2AceJ2";
    private static final String COL22="nbrDoubleFauteJ2";
    private static final String COL23="nbrPointGagnantJ2";
    private static final String COL24="nbrFauteProvoqueeJ2";
    private static final String COL25="nbrFauteDirectJ2";
    private static final String COL26="locationMatch";

    private static final String COL27="pseudoJun";
    private static final String COL28="pseudoJdeux";

    public DatabaseHelper(Context context)
    {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " +
                COL3+ " TEXT, " +
                COL4+ " TEXT, " +
                COL5+ " TEXT, " +
                COL6+ " TEXT, " +
                COL7+ " TEXT, " +
                COL8+ " TEXT, " +
                COL9+ " TEXT, " +
                COL10+ " TEXT, " +
                COL11+ " TEXT, " +
                COL12+ " TEXT, " +
                COL13+ " TEXT, " +
                COL14+ " TEXT, " +
                COL15+ " TEXT, " +
                COL16+ " TEXT, " +
                COL17+ " TEXT, " +
                COL18+ " TEXT, " +
                COL19+ " TEXT, " +
                COL20+ " TEXT, " +
                COL21+ " TEXT, " +
                COL22+ " TEXT, " +
                COL23+ " TEXT, " +
                COL24+ " TEXT, " +
                COL25+ " TEXT, " +
                COL26+ " TEXT, " +
                COL27+ " TEXT, " +
                COL28+ " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean addData(String pointActuelIntJ1, String pointSet1J1, String pointSet2J1, String pointSet3J1,
                           String nbr1ereOkJ1, String nbr2emeOkJ1, String nbr1AceJ1, String nbr2AceJ1,
                           String nbrDoubleFauteJ1, String nbrPointGagnantJ1, String nbrFauteProvoqueeJ1, String nbrFauteDirectJ1,
                           String pointActuelIntJ2, String pointSet1J2, String pointSet2J2, String pointSet3J2,
                           String nbr1ereOkJ2, String nbr2emeOkJ2, String nbr1AceJ2, String nbr2AceJ2,
                           String nbrDoubleFauteJ2, String nbrPointGagnantJ2, String nbrFauteProvoqueeJ2, String nbrFauteDirectJ2,
                           String locationMatch, String pseudoJun, String pseudoJdeux)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, pointActuelIntJ1);
        contentValues.put(COL3, pointSet1J1);
        contentValues.put(COL4, pointSet2J1);
        contentValues.put(COL5, pointSet3J1);
        contentValues.put(COL6, nbr1ereOkJ1);
        contentValues.put(COL7, nbr2emeOkJ1);
        contentValues.put(COL8, nbr1AceJ1);
        contentValues.put(COL9, nbr2AceJ1);
        contentValues.put(COL10, nbrDoubleFauteJ1);
        contentValues.put(COL11, nbrPointGagnantJ1);
        contentValues.put(COL12, nbrFauteProvoqueeJ1);
        contentValues.put(COL13, nbrFauteDirectJ1);
        contentValues.put(COL14, pointActuelIntJ2);
        contentValues.put(COL15, pointSet1J2);
        contentValues.put(COL16, pointSet2J2);
        contentValues.put(COL17, pointSet3J2);
        contentValues.put(COL18, nbr1ereOkJ2);
        contentValues.put(COL19, nbr2emeOkJ2);
        contentValues.put(COL20, nbr1AceJ2);
        contentValues.put(COL21, nbr2AceJ2);
        contentValues.put(COL22, nbrDoubleFauteJ2);
        contentValues.put(COL23, nbrPointGagnantJ2);
        contentValues.put(COL24, nbrFauteProvoqueeJ2);
        contentValues.put(COL25, nbrFauteDirectJ2);
        contentValues.put(COL26, locationMatch);
        contentValues.put(COL27, pseudoJun);
        contentValues.put(COL28, pseudoJdeux);

        Log.i(TAG, "addData: Adding some data to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor ViewData()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        if(cursor==null)
            Log.i("tag", "cursor is null");
        else
            Log.i("tag", "cursor is not null");
        return cursor;
    }

}
