package com.example.cgousios.databaseexample2;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

/**
 * Created by cgousios on 3/8/2016.
 */
public class MainMenuHelper extends SQLiteOpenHelper {
    // Constants
    private static final String DATABASE_PATH = "/data/data/com.example.cgousios.databaseexample2/databases/";
    private static final String DATABASE_NAME = "seniorprojectDB.db";
    private static final int SCHEMA_VERSION = 1;
    public static final String TABLE_NAME = "tTopics";
    public static final String COLUMN_ID = "fTopicID";
    public static final String COLUMN_TITLE = "fTopicName";

    public SQLiteDatabase dbSQLite;
    private final Context myContext;

    public MainMenuHelper(Context context){
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
        this.myContext = context;
        //Toast.makeText(myContext, "Hereeeeeee!!!!!!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //NOOOOO Toast.makeText(myContext, "Hereeeeeee!!!!!!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

    //@Override
    public void createDatabase(){
        createDB();
    }

    private void createDB() {
        boolean dbExist = DBExists();
        //Toast.makeText(myContext, "Hereeeeeee!!!!!!   " + dbExist, Toast.LENGTH_LONG).show();

        if(!dbExist){
            // Create an empty database into default system location
            // This overwrites that database with our database.
            this.getReadableDatabase();

            // Copy the database we included.
            copyDBFromResource();
        }
    }

    private boolean DBExists(){
        SQLiteDatabase db = null;

        try{
            String databasePath = DATABASE_PATH + DATABASE_NAME;
            db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            //db.setLockingEnabled(true);
            db.setVersion(1);
        }
        catch (SQLException e){
            Log.e("SqlHelper", "database not found");
        }

        if(db != null){
            db.close();
        }
        return  db != null ? true : false;
    }

    private void copyDBFromResource(){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String dbFilePath = DATABASE_PATH + DATABASE_NAME;

        try{
            inputStream = myContext.getAssets().open(DATABASE_NAME);

            outputStream = new FileOutputStream(dbFilePath);

            byte[] buffer = new byte[1024];
            int length = inputStream.read(buffer);
            while(length > 0){
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
        catch(IOException e){
            throw new Error("Problem copying database from file.");
        }
    }

    public void openDatabase() throws SQLException{
        String myPath = DATABASE_PATH + DATABASE_NAME;
        dbSQLite = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close(){
        if(dbSQLite != null){
            dbSQLite.close();
        }
        super.close();
    }

    public Cursor getCursor(){
        Toast.makeText(myContext, "Hereeeeeee!!!!!!", Toast.LENGTH_LONG).show();
        //SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        //queryBuilder.setTables(TABLE_NAME);

        //String[] astrColumnsToReturn = new String[] {COLUMN_ID, COLUMN_TITLE};

        // create table raw query
        //getReadableDatabase().rawQuery("CREATE TABLE tTopics (fTopicID   INTEGER PRIMARY KEY NOT NULL, fTopicName STRING  NOT NULL, fParentID  INTEGER, fFormulaID INTEGER)", null);

        //Cursor mCursor = queryBuilder.query(dbSQLite, astrColumnsToReturn, null, null, null, null, "fTopicName ASC");
        Cursor mCursor = getReadableDatabase().rawQuery("SELECT fTopicName FROM main.tTopics", null);
        //Cursor mCursor = getReadableDatabase().rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        return mCursor;
    }

    public String getName(Cursor cursor){
        return(cursor.getString(0));
    }
}
