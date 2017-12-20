package com.example.lab6;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Катя on 13.11.2017.
 */

public class ProductsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "productsBase";
    private static final int DB_VERSION = 1;

    public ProductsDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE product ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT , "
                + "upc TEXT , "
                + "izgot TEXT , "
                + "cena INTEGER ,  "
                +"hranenye INTEGER,"
                +"kol  INTEGER)");

        try{
        insertPatient(db, "хлеб", "023456789101", "ОАО Васильевич", 123654L, 12L, 120004L);
        insertPatient(db, "дыня", "023126789101", "ОАО Калинка", 5689011L, 14L, 4500L);
        insertPatient(db, "сельдерей", "044456789101", "ОАО Малинка",  333188L,  4L, 0L);
        insertPatient(db, "петрушка", "113456789115", "ОАО Бурова", 1536754L, 4L, 0L);
    } catch(SQLiteException e) {
            Log.e("errTag", e.getMessage());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static void insertPatient(SQLiteDatabase db, String name, String upc, String izgot, Long cena, Long hranenye, Long kol) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("upc", upc);
        values.put("izgot", izgot);
        values.put("cena", cena);
        values.put("hranenye", hranenye);
        values.put("kol", kol);
        db.insertOrThrow("product", null, values);
    }
}
