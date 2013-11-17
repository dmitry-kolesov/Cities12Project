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
public class MyAdapter  extends BaseAdapter {

        Context context;
        List<City>  data;
        private static LayoutInflater inflater = null;
        static DrawableManager manager;


    static
    {
        manager = new DrawableManager();
    }

    public MyAdapter()
    {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
        public MyAdapter(Context context, int resource, List<City> objects) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.data = objects;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = convertView;
            if (vi == null)
                vi = inflater.inflate(R.layout.mylistitem, null);
            TextView text = (TextView) vi.findViewById(R.id.textView1);
            text.setText(data.get(position).GetName());

            //            ImageView image = (ImageView) vi.findViewById(R.id.imageView1);
            try{
                //            image.setImageURI(Uri.parse(data.get(position).GetImageUrl()));

                String url = data.get(position).GetImageUrl();
                //if(!manager.GetDrawableMap().containsKey(url))
                manager.fetchDrawableOnThread(url, (ImageView) vi.findViewById(R.id.imageView1), true);
                //new DownloadImageTask((ImageView) vi.findViewById(R.id.imageView1)).execute(url);
            }catch (Exception ex)
            {
                ;
            }
            return vi;
        }



    }

