package com.azcltd.android.test.kolesov;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 123 on 15.11.13.
 */
public class CityDetails extends Activity {

    City city;
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
        int position = intent.getIntExtra("position", -1);
        if(position >= 0)
        {
            city = WsClient.GetInstance().GetCities().get(position);
            TextView tv = (TextView)findViewById(R.id.descrTbx);
            tv.setText(city.GetDescription());
            ImageView iv = (ImageView)findViewById(R.id.imageView1);
            DrawableManager manager = new DrawableManager();
            manager.fetchDrawableOnThread(city.GetImageUrl(), (ImageView) iv, false);
        }}catch(Exception ex)
    {
        int j = 0;
    }
    }

}
