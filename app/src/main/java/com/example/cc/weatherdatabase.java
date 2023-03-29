package com.example.a390cc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

// database for testing
public class weatherdatabase extends SQLiteOpenHelper {
    public weatherdatabase(@Nullable Context context) {
        super(context,"weather", null,2);
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd '@' HH:mm:ss");
    Date date = new Date();

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement2  = "CREATE TABLE WEATHER (UID INTEGER PRIMARY KEY AUTOINCREMENT, TEMPVALUE, HUMVALUE,DAT) ";
     sqLiteDatabase.execSQL(createTableStatement2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addtwo(tabletwo t2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();  //help associate column name with its value
        cv.put("TEMPVALUE", t2.getTemp());
        cv.put("HUMALUE", t2.getHum());
        cv.put("DAT",dateFormat.format(date));
        long count_list = db.insert("WEATHER", null, cv);
        if(count_list ==-1)
        {
            return false;
        }
        else {
            return true;
        }
    }
    public String getlast(){
        String output = null;
        String query = "SELECT TEMPVALUE FROM WEATHER ORDER BY UID DESC LIMIT 1";
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                String countType = cursor.getString(0);
                output = countType;
            } while (cursor.moveToNext());
        }

        else{
            //nothing done
        }

        db.close();
        cursor.close();
        return output;
    }
    public String getlasthum(){
        String output = null;
        String query = "SELECT HUMVALUE FROM WEATHER ORDER BY UID DESC LIMIT 1";
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                String countType = cursor.getString(0);
                output = countType;
            } while (cursor.moveToNext());
        }

        else{
            //nothing done
        }

        db.close();
        cursor.close();
        return output;
    }
}
