package com.example.cgousios.databaseexample2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.DoubleBuffer;


public class MainActivity extends Activity {

    private MainMenuHelper dbMainMenuHelper = null;
    private Cursor myCursor = null;
    private MainMenuAdapter adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toast.makeText(getApplicationContext(), "Hereeeeeee!!!!!!", Toast.LENGTH_LONG).show();

        //ListView element
        ListView myListView = (ListView) findViewById(R.id.mylistview);


        //Toast.makeText(getApplicationContext(), "Hereeeeeee!!!!!!", Toast.LENGTH_LONG).show();

        // DB helper
        dbMainMenuHelper = new MainMenuHelper(this);

        //Toast.makeText(getApplicationContext(), "Hereeeeeee!!!!!!", Toast.LENGTH_LONG).show();

        // Call Create
        dbMainMenuHelper.createDatabase();

        //Toast.makeText(getApplicationContext(), "Hereeeeeee!!!!!!", Toast.LENGTH_LONG).show();
        // Open
        dbMainMenuHelper.openDatabase();

        //Toast.makeText(getApplicationContext(), "Hereeeeeee!!!!!!", Toast.LENGTH_LONG).show();

        myCursor = dbMainMenuHelper.getCursor();
        // Adapter
        adapter = new MainMenuAdapter(myCursor);


        // List item click listener
        myListView.setOnItemClickListener(onListClick);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        dbMainMenuHelper.close();
    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
        }
    };

    // Adapter class
    class MainMenuAdapter extends CursorAdapter {
        MainMenuAdapter(Cursor c){
            super(MainActivity.this, c);
        }
        @Override
        // Cursor adapter
        // use bindView and newView calls (instead of getView)
        // CursorAdapters have a default implementation of getVeiw that calls
        // bindView and newView when needed.  Cleaner and better code.
        public void bindView(View row, Context ctxt, Cursor c){
            MainMenuHolder holder = (MainMenuHolder) row.getTag();
            holder.populateFrom(c, dbMainMenuHelper);
        }
        @Override
        public View newView(Context txt, Cursor c, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            MainMenuHolder holder = new MainMenuHolder(row);
            row.setTag(holder);
            return(row);
        }
    }

    // Holder class
    static class MainMenuHolder {
        private TextView name = null;

        MainMenuHolder(View row){
            name = (TextView) row.findViewById(R.id.MainMenuText);
        }

        void populateFrom(Cursor c, MainMenuHelper r){
            name.setText(r.getName(c));
        }
    }




}
