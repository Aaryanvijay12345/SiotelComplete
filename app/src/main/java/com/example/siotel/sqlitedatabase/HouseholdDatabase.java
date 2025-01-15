package com.example.siotel.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.siotel.models.HhModel;
import com.example.siotel.models.HouseholdsModel;

import java.util.ArrayList;

public class HouseholdDatabase extends SQLiteOpenHelper {

    private  static final String DB_NAME="household";
    private static final int DB_VERSION=1;
    private static final String TABLE_NAME="housholdTable";

    private static final  String ID_COL="idcolumn";
    private static final  String HOUSEHOLD_NAME="name";
    private static final  String HOUSEHOLD_ID="Id";
    private static final  String DATE="date";






    public HouseholdDatabase( Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String query="CREATE TABLE " +TABLE_NAME+" ("+
                   ID_COL+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + HOUSEHOLD_NAME +" TEXT,"
                   +HOUSEHOLD_ID+" TEXT,"
                   +DATE+" TEXT)";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addHouseHold(String hname,String hid,String hdate)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(HOUSEHOLD_NAME,hname);
        values.put(HOUSEHOLD_ID,hid);
        values.put(DATE,hdate);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }
    public ArrayList<HouseholdsModel> getHouseHolds() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<HouseholdsModel> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new HouseholdsModel(cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }
    public void dropAddHouseholds()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public int getHouseholdCount()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<HouseholdsModel> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new HouseholdsModel(cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList.size();
    }
    public void deleteDatabase(Context context)
    {
        context.deleteDatabase(DB_NAME);
    }
}
