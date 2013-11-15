package com.azcltd.android.test.kolesov;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123 on 13.11.13.
 */

public class WsClient {

    static final String wsUrl = "http://ec2-54-235-247-20.compute-1.amazonaws.com/android/cities.json";
    private List<City> cities;

    public List<City> GetCitites()
    {
        // get from http://stackoverflow.com/questions/7281283/android-webrequest-simple-solution
        HttpClient client = new DefaultHttpClient();
        //URI uri = new URI("http://ec2-54-235-247-20.compute-1.amazonaws.com/android/cities.json");
        HttpGet httpget = new HttpGet(wsUrl);

        // Execute the request
        HttpResponse response;
        try {
            response = client.execute(httpget);
//            URL url = new URL(wsUrl);
//            InputStream in = url.openStream();
//            InputStreamReader reader = new InputStreamReader(in);
            // Get hold of the response entity

            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release

            if (entity != null) {

                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
                // now you have the string representation of the HTML request
                instream.close();

                cities = ConvertJsonStringToCities(result);
            }


        } catch (Exception e) {
            ;
        }
        return cities;
    }

    private static String convertStreamToString(InputStream is) {
    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private List<City> ConvertJsonStringToCities(String Content)
    {
        JSONObject jsonResponse;
        List<City> cities  = new ArrayList<City>();

        //http://androidexample.com/Restful_Webservice_Call_And_Get_And_Parse_JSON_Data-_Android_Example/index.php?view=article_discription&aid=101&aaid=123
        try {

            /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
            jsonResponse = new JSONObject(Content);

            /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
            /*******  Returns null otherwise.  *******/
            JSONArray jsonMainNode = jsonResponse.getJSONArray("cities");

            /*********** Process each JSON Node ************/

            int lengthJsonArr = jsonMainNode.length();

            for(int i=0; i < lengthJsonArr; i++)
            {
                /****** Get Object for each JSON node.***********/
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                /******* Fetch node values **********/
                int id       = jsonChildNode.optInt("id");
                String name     = jsonChildNode.optString("name").toString();
                String description = jsonChildNode.optString("description").toString();
                String image_url = jsonChildNode.optString("image_url").toString();
                String country = jsonChildNode.optString("country").toString();

                JSONObject jsonResponse1 = new JSONObject(jsonChildNode.optString("location").toString());
                double latitude = jsonResponse1.optDouble("latitude");
                double longitude = jsonResponse1.optDouble("longitude");

                Location location = new Location("myProvider");
                location.setLongitude(longitude);
                location.setLatitude(latitude);

                cities.add(new City(id, name, description,image_url, country, location));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cities;
    }
}
