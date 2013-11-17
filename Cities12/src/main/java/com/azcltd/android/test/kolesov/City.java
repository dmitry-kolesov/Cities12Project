package com.azcltd.android.test.kolesov;

import android.location.Location;

/**
 * Created by kdb on 13.11.13.
 */
public class City {

    private int id;
    private String name;
    private String description;
    private String image_url;
    private String country;
    private Location location;

    public int GetId()
    { return id;}
    public String GetName()
    { return name;}
    public String GetImageUrl()
    { return image_url;}
    public String GetCountry()
    { return country;}
    public String GetDescription()
    { return description;}
    public Location GetLocation()
    { return location;}

    public City(int id,
            String name,
            String description,
            String image_url,
            String country,
            Location location)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.country = country;
        this.location = location;
    }

}
