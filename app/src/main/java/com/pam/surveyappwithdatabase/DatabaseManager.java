package com.pam.surveyappwithdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseManager {

    private Context context;
    private SQLHelper helper;
    private SQLiteDatabase db;
    protected static final String DB_NAME = "customsurvey.db";

    protected static final int DB_VERSION = 1;
    protected static final String DB_TABLE = "survey";

    private static final String INT_COL = "_id";
    protected static final String NAME_COL = "survey_question";
    protected static final String ANSWER1_COL = "answer1";
    protected static final String ANSWER2_COL = "answer2";

    private static final String DB_TAG = "DatabaseManager" ;
    private static final String SQL_TAG = "SQLHelper" ;

    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close(); //Closes the database - very important!
    }


    //add method to fetch all data and return a Cursor
    public Cursor getCursorAll(){
        Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, NAME_COL);
        return cursor;
    }
    //TODO add method to select (search) for a surveyQuestion
    //add method to insert (add) a product and answer1count
    public boolean addSurveyQuestion(String surveyQuestion, int answer1count, int answer2count){
        ContentValues newSurveyQuestion = new ContentValues();
        newSurveyQuestion.put(NAME_COL, surveyQuestion);
        newSurveyQuestion.put(ANSWER1_COL, answer1count);
        newSurveyQuestion.put(ANSWER2_COL, answer2count);
        try{
            db.insertOrThrow(DB_TABLE, null, newSurveyQuestion);
            return true;
        } catch (SQLiteConstraintException sqlce){
            Log.e(DB_TAG, "error inserting data into table. "+"Name:" +surveyQuestion+ "answer1count:"+answer1count, sqlce);
            return false;
        }
    }

    public int getSurveyAnswers(String surveyQuestion){
        String[] cols = {ANSWER1_COL};

        //this query is case sensitive. If you don't care about case, convert the search
        // query to uppercase and compare to the up uppercase version of the data in the database
        //select quantity from products where upper (product_name) = upper(surveyQuestion>)

        String selection = NAME_COL + "=?";
        String[] selectionArgs = {surveyQuestion};
        Cursor cursor = db.query(DB_TABLE, cols, selection, selectionArgs, null, null, null);

        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            int quantity = cursor.getInt(0);
            cursor.close();
            return quantity;
        } else{
            //0 surveys found- the survey is not in the database.
            //(Or more than one, which would indicate a problem with the design.
            //When the db was created, the survey_name was configured to be unique.)
            return-1; //todo - better way to indicate survey not found?
        }
    }

    //add method to delete a product
    public boolean deleteSurveyQuestion(long surveyQuestionID) {
        String[] whereArgs = {Long.toString(surveyQuestionID)};
        String where = "_id = ?";
        int rowsDeleted = db.delete(DB_TABLE, where, whereArgs);

        Log.i(DB_TAG, "Delete " + surveyQuestionID + " rows deleted:" + rowsDeleted);

        if (rowsDeleted == 1) {
            return true; //should be exactly one row deleted, since _id is a primary key
        }
        return false;//nothing deleted, this primary key is not in the DB
        //(or more than 1 row deleted, which indicates DB design error.)
    }

    //add method to update (change) the quantity of a product
    //Return false if no update is made, for example, product not found
    public boolean updateSurveyAnswerCount(String name, int newAnswer1Count, int newAnswer2Count){
        ContentValues updateProduct = new ContentValues();
        updateProduct.put(ANSWER1_COL, newAnswer1Count);
        updateProduct.put(ANSWER2_COL, newAnswer2Count);
        String[] whereArgs = {name};
        String where = NAME_COL+" =?";

        int rowsChanged = db.update(DB_TABLE, updateProduct, where, whereArgs);

        Log.i(DB_TAG, "Update "+ name + " new answer count "+ newAnswer1Count + " rows modified "+ rowsChanged);
        Log.i(DB_TAG, "Update "+ name + " new answer count "+ newAnswer2Count + " rows modified "+ rowsChanged);

        if(rowsChanged> 0){
            return true; //if at least one row changed, an update was made.
        }
        return false; //Otherwise, no rows changed. Return false to indicate no update.
    }


    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context c){
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            //Table contains a primary key column, _id which autoincrements - saves you setting the value
            //Having a primary key column is almost always a good idea. In this app, the _id column is used by
            //the list CursorAdapter data source to figure out what to put in the list, and to uniquely identify each element
            //Name column, String
            //Quantity column, int

            String createTable = "CREATE TABLE " + DB_TABLE +
                    " (" + INT_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_COL +" TEXT UNIQUE, " + ANSWER1_COL +" INTEGER, "
                    + ANSWER1_COL +" INTEGER);";

            Log.d(SQL_TAG, createTable);
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
            Log.w(SQL_TAG, "Upgrade table - drop and recreate it");
        }
    }
}