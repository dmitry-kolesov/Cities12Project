package com.azcltd.android.test.kolesov;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kdb on 15.11.13.
 */
public class CityDetails extends Activity {

    City city;
    int position;
//    CityDetails()
//    {
//        Intent i= getIntent();
//        int position = i.getIntExtra("position", -1);
//        if(position >= 0)
//            city = WsClient.GetInstance().GetCities().get(position);
//        TextView tv = (TextView)findViewById(R.id.descrTbx);
//        tv.setText(city.GetDescription());
//        ImageView iv = (ImageView)findViewById(R.id.imageView1);
//        DrawableManager manager = new DrawableManager();
//        manager.fetchDrawableOnThread(city.GetImageUrl(), (ImageView) iv);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try{
        setContentView(R.layout.city_details);

        Intent intent= getIntent();
        position = intent.getIntExtra("position", -1);
        if(position >= 0)
        {
            city = WsClient.GetInstance().GetCities().get(position);
            TextView tv = (TextView)findViewById(R.id.descrTbx);
            tv.setText(city.GetDescription());
            ImageView iv = (ImageView)findViewById(R.id.imageView1);
            DrawableManager manager = new DrawableManager();
            manager.fetchDrawableOnThread(city.GetImageUrl(), (ImageView) iv, false);

            Button showMapBtn = (Button)findViewById(R.id.showMapBtn);
            showMapBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent( CityDetails.this.getApplicationContext(), MyMapActivity.class);

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
            });

        }}catch(Exception ex)
    {
        int j = 0;
    }
    }

}
