package com.example.drivingschool;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

public class LatLngListener implements LocationListener
{
	public ILocation locationInterface;

	public LatLngListener(ILocation delegate)
	{
		locationInterface = delegate;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{

	}

	@Override
	public void onProviderEnabled(String provider)
	{

	}

	@Override
	public void onProviderDisabled(String provider)
	{

	}

	@Override
	public void onLocationChanged(Location location)
	{
		if (location != null)
		{
			LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
			locationInterface.onLatLngUpdated(latLng);
		}
	}
}