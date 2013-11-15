package com.azcltd.android.test.kolesov;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    List<City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

//        Button resend = (Button)findViewById(R.id.resendBtn);
//        resend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
            {
                WsClient client = new WsClient();
                cities = client.GetCitites();
                if(cities != null){
                    ListView lv = (ListView)findViewById(R.id.lvMain);
                    MyAdapter adapter = new MyAdapter( lv.getContext(), R.layout.mylistitem, cities);
                    lv.setAdapter(adapter);
                }
            }
        //});
        ListView listView = (ListView)findViewById(R.id.lvMain);
        listView.setOnItemClickListener (listener);
    }
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //String name = ((TextView) view.findViewById(R.id.txtText)).getText();
                Intent intent = new Intent(context, WhatEverYouWant.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }

        };




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            return rootView;
//        }
    }

}
