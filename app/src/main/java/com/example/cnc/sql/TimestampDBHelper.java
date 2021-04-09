package com.example.cnc.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cnc.supporters.Timestamp;
import com.example.cnc.supporters.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lai Shan Law on March 13, 2021
 */
public class TimestampDBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TimestampManager.db";

    // User table name
    private static final String TABLE_TIMESTAMP = "timestamp";

    // User Table Columns names
    private static final String COLUMN_STUDENT_ID = "student_ID";
    private static final String COLUMN_ASSMNT_CODE = "assmnt_Code";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    // create table
    private String CREATE_TIMESTAMP_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_TIMESTAMP + "("
                    + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY ,"
                    + COLUMN_ASSMNT_CODE + " TEXT,"
                    + COLUMN_TIMESTAMP + " TEXT"
                    //+ " PRIMARY KEY (" + COLUMN_STUDENT_ID + ", " + COLUMN_ASSMNT_CODE + ")"
                    + ")";

    // drop table
    private String DROP_TIMESTAMP_TABLE = "DROP TABLE IF EXISTS " + TABLE_TIMESTAMP;

    /**
     * Constructor
     */
    public TimestampDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TIMESTAMP_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop Timestamp Table if exist
        db.execSQL(DROP_TIMESTAMP_TABLE);

        // Create tables again
        onCreate(db);
    }

    /**
     * Create timestamp record
     */
    public void addTimestamp(Timestamp timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, timestamp.getStudentID());
        values.put(COLUMN_ASSMNT_CODE, timestamp.getAssmntCode());
        values.put(COLUMN_TIMESTAMP, timestamp.getTimestamp());

        // Inserting Row
        db.insert(TABLE_TIMESTAMP, null, values);
        db.close();
    }

    /**
     * display all records and return a list of the records
     */
    public List<Timestamp> getAllRecords() {

        String[] columns = {
                COLUMN_STUDENT_ID,
                COLUMN_ASSMNT_CODE,
                COLUMN_TIMESTAMP
        };
        // sorting orders
        String sortOrder =
                COLUMN_STUDENT_ID + ", " + COLUMN_ASSMNT_CODE + " ASC";
        List<Timestamp> tsList = new ArrayList<Timestamp>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the Timestamp table
        Cursor cursor = db.query(TABLE_TIMESTAMP, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Go through each row and adding to list
        if (cursor.moveToFirst()) {
            do {
                Timestamp timestamp = new Timestamp();
                timestamp.setStudentID(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ID)));
                timestamp.setAssmntCode(cursor.getString(cursor.getColumnIndex(COLUMN_ASSMNT_CODE)));
                timestamp.setTimestamp(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
                tsList.add(timestamp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return tsList;
    }

    /**
     * Update timestamp record
     */
    public void updateTimestamp(Timestamp timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, timestamp.getStudentID());
        values.put(COLUMN_ASSMNT_CODE, timestamp.getAssmntCode());
        values.put(COLUMN_TIMESTAMP, timestamp.getTimestamp());

        // updating row
        db.update(TABLE_TIMESTAMP, values, COLUMN_STUDENT_ID + " = ?",
                new String[]{String.valueOf(timestamp.getStudentID())});
        db.close();
    }

    /**
     * Delete timestamp record
     */
    public void deleteTimestamp(Timestamp timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete timestamp record by id
        db.delete(TABLE_TIMESTAMP, COLUMN_STUDENT_ID + " = ? AND " + COLUMN_ASSMNT_CODE + " = ?",
       // db.delete(TABLE_TIMESTAMP, COLUMN_STUDENT_ID + " = ?",
                new String[]{String.valueOf(timestamp.getStudentID())});
        db.close();
    }
    /**
     * Delete all timestamps in the SQLite
     */
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete all timestamps
        db.delete(TABLE_TIMESTAMP, null, null);
        //db.execSQL("delete * From "+ TABLE_TIMESTAMP);
        db.close();
    }



    /**
     * Check Timestamp exist or not
      */
    public String getTSFromIntDB(String id, String code) {
        String ts = null;
        // array of columns to fetch
        String[] columns = {
                COLUMN_TIMESTAMP
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // String selection = COLUMN_STUDENT_ID + " = ? AND " + COLUMN_ASSMNT_CODE + " = ?";
        String selection = COLUMN_STUDENT_ID + " = ?";

        // selection argument
        String[] selectionArgs = {id};

        // query timestamp table with condition
        /**
         * SQL query equivalent to this query function is
         * SELECT timestamp FROM timestamp WHERE student_id = '123456789';
         */
        Cursor cursor = db.query(TABLE_TIMESTAMP, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

      //  int index = cursor.getColumnIndex(COLUMN_TIMESTAMP);
     //   String ts = cursor.getString(index);
        System.out.println("-->TimestampDBHelper page: getTimestamp >> " + cursor);
        if (cursor.moveToNext()) {
            ts = cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP));
            System.out.println("-->TimestampDBHelper page: ts >> " + ts);
        }
        cursor.close();

        db.close();

        return ts;
    }

    /**
     * Check Timestamp exist or not
     */
    public boolean isExist(String id, String code) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_TIMESTAMP
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_STUDENT_ID + " = ?";

        // selection argument
        String[] selectionArgs = {id};

        // query timestamp table with condition
        /**
         * SQL query equivalent to this query function is
         * SELECT student_id FROM timestamp WHERE student_id = '123456789';
         */
        Cursor cursor = db.query(TABLE_TIMESTAMP, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


     public Cursor getData() {
        String[] timestampTable = {COLUMN_STUDENT_ID,COLUMN_ASSMNT_CODE,COLUMN_TIMESTAMP};
        return getWritableDatabase().query(TABLE_TIMESTAMP, timestampTable, null,null,null,null,null);

    }
}

