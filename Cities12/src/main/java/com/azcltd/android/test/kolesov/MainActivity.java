package com.azcltd.android.test.kolesov;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.SyncStateContract;
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

        boolean isRetry = true;
        boolean connectionState;
        do
        {
            connectionState = isNetworkAvailable();
            if(!connectionState)
                isRetry = showRetryDialog();
        }while(!connectionState && isRetry);
        if(!isRetry && !connectionState)
            System.exit(0);

        loadCitiesList();
//        Button resend = (Button)findViewById(R.id.resendBtn);
//        resend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)

        //});
        ListView listView = (ListView)findViewById(R.id.lvMain);
        listView.setOnItemClickListener (listener);
    }
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //String name = ((TextView) view.findViewById(R.id.txtText)).getText();
                Intent intent = new Intent( MainActivity.this.getApplicationContext(), CityDetails.class);

                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("position", position);
                try{
                    startActivity(intent);
                }
                catch (Exception ex)
                {
                    int i = 0;
                }

            }
        };

    //http://stackoverflow.com/questions/4238921/android-detect-whether-there-is-an-internet-connection-available
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadCitiesList()
    {
        WsClient client = new WsClient();
        cities = WsClient.GetInstance().GetCities();
        if(cities != null){
            ListView lv = (ListView)findViewById(R.id.lvMain);
            City[] citiesArray = new City[cities.size()];
            MyAdapter adapter = new MyAdapter( lv.getContext(),  cities.toArray(citiesArray));//R.layout.mylistitem,
            lv.setAdapter(adapter);
        }
    }

    boolean retryDlgResult = false;
    //http://stackoverflow.com/questions/7456509/android-messagebox
    private boolean showRetryDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("No connection")
                .setMessage("Do you want retry check connection?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        retryDlgResult = true;
                        //do some thing here which you need
                    }
                });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                retryDlgResult = false;
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        return retryDlgResult;
    }
}
