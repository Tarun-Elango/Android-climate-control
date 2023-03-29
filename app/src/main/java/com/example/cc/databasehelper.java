package com.example.a390cc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.databinding.tool.util.L;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//main database to store the values as they are retrieved from the online database

public class databasehelper extends SQLiteOpenHelper {

    public databasehelper(@Nullable Context context) {
        super(context, "sensor",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement  = "CREATE TABLE SENSOR (UID INTEGER PRIMARY KEY AUTOINCREMENT, TEMPER, HUM,DATE) ";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd '@' HH:mm:ss");
    Date date = new Date();

    //function to add a row of new values
    public boolean addOne(table t){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();  //help associate column name with its value
        cv.put("TEMPER", t.getTemp());
        cv.put("HUM", t.getHum());
        cv.put("DATE",dateFormat.format(date));
        long count_list = db.insert("SENSOR", null, cv);
        if(count_list ==-1)
        {
            return false;
        }
        else {
            return true;
        }
    }
    //get the last temperature value
    public String getlast(){
        String output = null;
        String query = "SELECT TEMPER FROM SENSOR ORDER BY UID DESC LIMIT 1";
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

    //get the last humidty value
    public String getlasthum(){
        String output = null;
        String query = "SELECT HUM FROM SENSOR ORDER BY UID DESC LIMIT 1";
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

    //function to get all temperature values in a list
    public List<Double> gettemparray(){
        List<Double> outputlist = new ArrayList<>();
        String query = "SELECT TEMPER FROM SENSOR ";
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                String countType = cursor.getString(0);
                Double value = Double.parseDouble(countType);
                outputlist.add(value);
            } while (cursor.moveToNext());
        }

        else{
            //nothing done
        }

        db.close();
        cursor.close();
        return outputlist;
    }

    //function to get all humidity values in a list
    public List<Double> gethumarray(){
        List<Double> outputlist = new ArrayList<>();
        String query = "SELECT HUM FROM SENSOR ";
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                String countType = cursor.getString(0);
                Double value = Double.parseDouble(countType);
                outputlist.add(value);
            } while (cursor.moveToNext());
        }

        else{
            //nothing done
        }

        db.close();
        cursor.close();
        return outputlist;}

        //function to return total value
        public int totalCount() {
            int count = 0;
            String query = "SELECT COUNT(*) FROM SENSOR";
            Cursor cursor = getReadableDatabase().rawQuery(query,null);

            if(cursor.getCount()>0){
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
            cursor.close();
            return count;
        }
//test function to delete row
    public void Delete(){
        String query = "DELETE FROM SENSOR WHERE UID = 131";
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            //done
        }
        else{
            //do nothing
        }

    }

}
