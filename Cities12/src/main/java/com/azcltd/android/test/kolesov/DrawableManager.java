package com.azcltd.android.test.kolesov;
//http://stackoverflow.com/questions/541966/how-do-i-do-a-lazy-load-of-images-in-listview/3068012#3068012
/*
 Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

//http://stackoverflow.com/questions/541966/how-do-i-do-a-lazy-load-of-images-in-listview/3068012#3068012
public class DrawableManager {
    private static final Map<String, Drawable> drawableMap = new HashMap<String, Drawable>();

    public static final Map<String, Drawable> GetDrawableMap(){return drawableMap;}

    public DrawableManager() {
       // drawableMap = new HashMap<String, Drawable>();
    }

    public Drawable fetchDrawable(String urlString) {
        if (drawableMap.containsKey(urlString)) {
            return drawableMap.get(urlString);
        }

        Log.d(this.getClass().getSimpleName(), "image url:" + urlString);
        try {
            InputStream is = fetch(urlString);
            Drawable drawable = Drawable.createFromStream(is, "src");


            if (drawable != null) {
                drawableMap.put(urlString, drawable);
//                Log.d(this.getClass().getSimpleName(), "got a thumbnail drawable: " + drawable.getBounds() + ", "
//                        + drawable.getIntrinsicHeight() + "," + drawable.getIntrinsicWidth() + ", "
//                        + drawable.getMinimumHeight() + "," + drawable.getMinimumWidth());
            } else {
                Log.w(this.getClass().getSimpleName(), "could not get thumbnail");
            }

            return drawable;
        } catch (MalformedURLException e) {
            Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
            return null;
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
            return null;
        }
    }
    private Drawable getThumbnail(Drawable image) {
        if(image != null)
        {
            Bitmap b = ((BitmapDrawable)image).getBitmap();
            float coef = b.getWidth() / b.getHeight();
            Bitmap bitmapResized = Bitmap.createScaledBitmap(b, (int)(100 * coef), 100, false);
            return new BitmapDrawable(bitmapResized);
        }
        return null;
    }



    public void fetchDrawableOnThread(final String urlString, final ImageView imageView, final boolean isThumbnail) {

        if (drawableMap.containsKey(urlString)) {
            Drawable image = drawableMap.get(urlString);
            SetDrawableToImageView(image, imageView, isThumbnail);
            return;
        }

            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message message) {
                    DrawableWithFlag obj = (DrawableWithFlag) message.obj;
                    Drawable image = obj.image;
                    boolean flag = obj.isNeedThumbnailed;
                    SetDrawableToImageView(image, imageView, flag);
                }
            };

            Thread thread = new Thread() {
                @Override
                public void run() {
                    setPriority(3);
                    //TODO : set imageView to a "pending" image
                    Drawable drawable = fetchDrawable(urlString);
                    Message message = handler.obtainMessage(1, new DrawableWithFlag(drawable, isThumbnail));
                    handler.sendMessage(message);
                }
            };
            thread.start();
    }

    private void SetDrawableToImageView(Drawable image, ImageView imageView, boolean isNeedThumbnailed)
    {
        if(isNeedThumbnailed)
        {
            image = getThumbnail(image);
        }
        if(image != null)
            imageView.setImageDrawable(image);
    }

class DrawableWithFlag
{
    Drawable image;
    boolean isNeedThumbnailed;
    public DrawableWithFlag(Drawable mImage,
                                boolean mIsNeedThumbnailed)
    {
        image = mImage;
        isNeedThumbnailed = mIsNeedThumbnailed;
    }
}

    private InputStream fetch(String urlString) throws MalformedURLException, IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlString);
        HttpResponse response = httpClient.execute(request);
        return response.getEntity().getContent();
    }
}