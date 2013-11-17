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
import java.util.List;

/**
 * Created by kdb on 14.11.13.
 */
public class MyAdapter  extends ArrayAdapter<City> {

        Context context;
        City[]  data;
        private static LayoutInflater inflater = null;
        static DrawableManager manager;


    static
    {
        manager = new DrawableManager();
    }

//    public MyAdapter()
//    {
//        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
        public MyAdapter(Context context, City[] objects) {
            super(context, R.layout.mylistitem, objects);
            // TODO Auto-generated constructor stub
            this.context = context;
            this.data = objects;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

//        @Override
//        public int getCount() {
//            // TODO Auto-generated method stub
//            return data.length;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            // TODO Auto-generated method stub
//            return data[position];
//        }
//
//        @Override
//        public long getItemId(int position) {
//            // TODO Auto-generated method stub
//            return position;
//        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View v = convertView;
            if (v == null)
            {
                LayoutInflater vi =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.mylistitem, null);
                //vi = inflater.inflate(R.layout.mylistitem, null);
            }
            TextView text = (TextView) v.findViewById(R.id.textView1);
            text.setText(data[position].GetName());

            //            ImageView image = (ImageView) vi.findViewById(R.id.imageView1);
            try{
                //            image.setImageURI(Uri.parse(data.get(position).GetImageUrl()));

                String url = data[position].GetImageUrl();
                //if(!manager.GetDrawableMap().containsKey(url))
                manager.fetchDrawableOnThreadWithNoImage(url, (ImageView) v.findViewById(R.id.imageView), true);
                //new DownloadImageTask((ImageView) vi.findViewById(R.id.imageView1)).execute(url);
            }catch (Exception ex)
            {
                ;
            }
            return v;
        }



    }

