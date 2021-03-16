package com.example.cnc.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cnc.supporters.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NyNguyen on Feb 10, 2021
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "StudentManager.db";

    // User table name
    private static final String TABLE_STUDENT = "student";

    // User Table Columns names
    /* private static final String COLUMN_USER_ID = "user_id";*/
    private static final String COLUMN_STUDENT_ID = "student_ID";
    private static final String COLUMN_STUDENT_EMAIL = "student_email";
    private static final String COLUMN_STUDENT_PASSWORD = "student_password";

    // create table sql query
    private String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + "("
            + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY ," + COLUMN_STUDENT_EMAIL + " TEXT," + COLUMN_STUDENT_PASSWORD + " TEXT" + ")";

    // drop table sql query
    private String DROP_STUDENT_TABLE = "DROP TABLE IF EXISTS " + TABLE_STUDENT;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_STUDENT_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, user.getStudentID());
        values.put(COLUMN_STUDENT_EMAIL, user.getEmail());
        values.put(COLUMN_STUDENT_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_STUDENT_ID,
                COLUMN_STUDENT_EMAIL,
                COLUMN_STUDENT_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_STUDENT_ID + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the student table
        /**
         * this function is used to fetch records from student table this function works like we use sql query.
         * SQL query equivalent to this query function is:
         * SELECT student_id,student_email,student_password FROM student ORDER BY student_id;
         */
        Cursor cursor = db.query(TABLE_STUDENT, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setStudentID(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ID)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, user.getStudentID());
        values.put(COLUMN_STUDENT_EMAIL, user.getEmail());
        values.put(COLUMN_STUDENT_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_STUDENT, values, COLUMN_STUDENT_ID + " = ?",
                new String[]{String.valueOf(user.getStudentID())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_STUDENT, COLUMN_STUDENT_ID + " = ?",
                new String[]{String.valueOf(user.getStudentID())});
        db.close();
    }

    /**
     * This method to check user exist or not by email
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_STUDENT_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_STUDENT_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * this function is used to fetch records from student table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT student_id FROM student WHERE student_email = 'abc@address.com';
         */
        Cursor cursor = db.query(TABLE_STUDENT, //Table to query
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

    /**
     * This method to check user exist or not by email and password
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_STUDENT_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_STUDENT_EMAIL + " = ?" + " AND " + COLUMN_STUDENT_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * this function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT student_id FROM student WHERE student_email = 'abc@address.com' AND student_password = 'password';
         */
        Cursor cursor = db.query(TABLE_STUDENT, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

}

