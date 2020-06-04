package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SchoolActivity extends AppCompatActivity implements OnMapReadyCallback
{
	public LatLng latlng;
	public GoogleMap map;
	public Marker marker;

	public EditText nameet;
	public EditText deset;
	public EditText contactet;
	public EditText phoneet;
	public EditText addresset;
	public EditText postcodeet;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_school);

		requestLocation(this);

		nameet = findViewById(R.id.nameET);
		deset = findViewById(R.id.descriptionET);
		contactet = findViewById(R.id.contactET);
		phoneet = findViewById(R.id.phoneET);
		addresset = findViewById(R.id.addressET);
		postcodeet = findViewById(R.id.postcodeET);

		School school = (School) getIntent().getSerializableExtra("School");

		if (school != null)
		{
			nameet.setText(school.name);
			nameet.setEnabled(false);
			deset.setText(school.description);
			contactet.setText(school.contact);
			phoneet.setText(school.phone);
			addresset.setText(school.address);
			postcodeet.setText(school.postcode);
			latlng = school.getLocation();
		}

		SupportMapFragment mapFragment = (SupportMapFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.map));
		mapFragment.getMapAsync(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.schoolbar, menu);

		if (!MainActivity.isAdmin)
			menu.findItem(R.id.save).setVisible(false);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.save)
		{
			School school = new School();

			school.name = nameet.getText().toString();
			school.description = deset.getText().toString();
			school.contact = contactet.getText().toString();
			school.phone = phoneet.getText().toString();
			school.address = addresset.getText().toString();
			school.postcode = postcodeet.getText().toString();
			school.location = latlng.latitude + "$" + latlng.longitude;

			if (school.name.isEmpty())
			{
				Toast.makeText(this, "School name cannot be empty.", Toast.LENGTH_LONG).show();
				return false;
			}

			if (school.description.isEmpty())
			{
				Toast.makeText(this, "School description cannot be left blank.", Toast.LENGTH_LONG).show();
				return false;
			}

			if (school.contact.isEmpty())
			{
				Toast.makeText(this, "Contact person cannot be left blank.", Toast.LENGTH_LONG).show();
				return false;
			}

			if (school.phone.isEmpty())
			{
				Toast.makeText(this, "Contact phone cannot be left blank.", Toast.LENGTH_LONG).show();
				return false;
			}

			if (school.address.isEmpty())
			{
				Toast.makeText(this, "School address cannot be left blank.", Toast.LENGTH_LONG).show();
				return false;
			}

			if (school.postcode.isEmpty())
			{
				Toast.makeText(this, "Postcode cannot be left blank.", Toast.LENGTH_LONG).show();
				return false;
			}

			Intent intent = new Intent();
			intent.putExtra("School", school);
			setResult(RESULT_OK, intent);
		}
		else
		{
			setResult(RESULT_CANCELED);
		}
		finish();

		return super.onOptionsItemSelected(item);
	}

	GoogleMap.OnMapClickListener OnMap = new GoogleMap.OnMapClickListener()
	{
		@Override
		public void onMapClick(LatLng latLng)
		{
			latlng = latLng;

			if (marker != null)
			{
				marker.remove();
				map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18));
				marker = map.addMarker(new MarkerOptions().position(latlng));
			}
		}
	};

	@SuppressLint("MissingPermission")
	@Override
	public void onMapReady(GoogleMap googleMap)
	{
		map = googleMap;
		map.setOnMapClickListener(OnMap);
		marker = map.addMarker(new MarkerOptions().position(latlng));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18));
		map.setMyLocationEnabled(true);
	}

	void requestLocation(Activity activity)
	{
		LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

		assert locationManager != null;
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			if (ActivityCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
			{
				ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION}, 1);
				ActivityCompat.requestPermissions(activity, new String[]{ACCESS_COARSE_LOCATION}, 1);
				requestLocation(activity);
			}
		}
	}
}