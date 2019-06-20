package com.example.andrew.andrew_dot_com;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class myLocationListener implements LocationListener {

    private Context activityContext ;

    public myLocationListener(Context context)
    {
        activityContext = context ;
    }
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(activityContext, location.getLatitude()+" , "+location.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(activityContext, "GPS Status has been Changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(activityContext, "GPS Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(activityContext, "GPS disabled", Toast.LENGTH_SHORT).show();
    }
}
