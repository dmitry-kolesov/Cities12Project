package com.azcltd.android.test.kolesov;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kdb on 14.11.13.
 */
public class MyAdapter  extends ArrayAdapter<City> {

        Context context;
        City[]  data;
        private static LayoutInflater inflater = null;
        static DrawableManager manager;
        //private Map<Integer, View> myViews= new HashMap <Integer, View>();

    static
    {
        manager = new DrawableManager();
    }

        public MyAdapter(Context context, City[] objects) {
            super(context, R.layout.mylistitem, objects);
            // TODO Auto-generated constructor stub
            this.context = context;
            this.data = objects;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.length;
        }
//
        @Override
        public City getItem(int position) {
            // TODO Auto-generated method stub
            return data[position];
        }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            //View view = myViews.get(position);
            //if (view == null) {
                if (v == null)
                {
                    LayoutInflater vi =
                            (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.mylistitem, null);
                    //View v2 = inflater.inflate(R.layout.mylistitem, null);
                }
                //myViews.put(position, v);
                TextView text = (TextView) v.findViewById(R.id.textView1);
                text.setText(data[position].GetName());

                try{
                    String url = data[position].GetImageUrl();
                    ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
                    if(imageView.getDrawable() == null)// || imageView.getTag(0) == DrawableManager.isErrorImageKey)
                        manager.fetchDrawableOnThreadWithNoImage(url, imageView, true);
                    else
                    {int i = 0;}
                }
                catch (Exception ex)
                {
                    ;
                }
            return v;
        }
    }

