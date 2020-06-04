package com.example.drivingschool;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

class School implements Serializable
{
	public String name;
	public String description;
	public String contact;
	public String phone;
	public String address;
	public String postcode;
	public String location;

	School(){}

	//	Constructor to construct an instance using json string
	School(String raw)
	{
		try
		{
			JSONObject json = new JSONObject(raw);

			name = json.getString("name");
			description = json.getString("description");
			contact = json.getString("contact");
			phone = json.getString("phone");
			address = json.getString("address");
			postcode = json.getString("postcode");
			location = json.getString("location");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	//	Return the LatLng instance to add marker on the GoogleMap
	LatLng getLocation()
	{
		String[] latLng = location.split("\\$");
		return new LatLng(Double.parseDouble(latLng[0]), Double.parseDouble(latLng[1]));
	}

	@NonNull
	@Override
	public String toString()
	{
		try
		{
			JSONObject json = new JSONObject();
			json.put("name", name);
			json.put("description", description);
			json.put("contact", contact);
			json.put("phone", phone);
			json.put("address", address);
			json.put("postcode", postcode);
			json.put("location", location);

			return json.toString();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		return "";
	}
}