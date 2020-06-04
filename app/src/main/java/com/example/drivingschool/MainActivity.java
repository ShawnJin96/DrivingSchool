package com.example.drivingschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IStorage
{
	//	To indicate whether the current user is administrator.
	//	Administrator is allowed to modify school information, such as location on the map, contact person, phone, or address
	//	Normal users are not allowed to perform such action
	public static boolean isAdmin;

	public ArrayList<School> schools;
	public FireBase fb;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView schoolsList = findViewById(R.id.schools);
		schoolsList.setOnItemClickListener(listener);

		schools = new ArrayList<>();

		fb = new FireBase();
		fb.setIStorage(this);

		fb.get();
	}

	AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
		{
			//	Navigate to the school information activity when clicked on a school item
			Intent intent = new Intent(MainActivity.this, SchoolActivity.class);
			intent.putExtra("School", schools.get(i));
			startActivityForResult(intent, 0);
		}
	};

	//	If logged in as administarotr and modified something in the SchoolActivity, it is necessary to know this happens and update the UI.
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		//	RESULT_OK indicates that a school information is modified.
		if (resultCode == Activity.RESULT_OK)
		{
			School event = (School) data.getSerializableExtra("School");

			if (event != null)
			{
				for (int i = 0; i < schools.size(); i++)
				{
					if (schools.get(i).name.equals(event.name))
					{
						schools.set(i, event);
						break;
					}
				}
			}

			//	First update the UI
			updateList();

			if (schools.size() != 0)
			{
				JSONArray jsonArray = new JSONArray();

				for (School S : schools)
				{
					jsonArray.put(S);
				}

				//	Then update the data persisted on Firebase Storage
				fb.put(jsonArray.toString().getBytes());
			}
		}
	}

	void updateList()
	{
		//	Update the UI by resetting list view adapter
		SchoolListAdapter adapter = new SchoolListAdapter(MainActivity.this, schools);
		ListView eventsLV = findViewById(R.id.schools);
		eventsLV.setAdapter(adapter);
	}

	//	If successfully got "list.json", the school list persisted on Firebase Storage
	@Override
	public void onGetSuccess(byte[] data)
	{
		try
		{
			//	The data is actually a JSON string
			JSONArray jsonArray = new JSONArray(new String(data));

			for (int i = 0; i < jsonArray.length(); i++)
			{
				schools.add(new School(jsonArray.getString(i)));
			}

			//	Update UI
			updateList();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	//	If failed to get "list.json". It may because data on the Firebase Storage is not initialized.
	//	Then try to put new entries to initialize data
	@Override
	public void onGetFailure(Exception exception)
	{
		School A = new School();
		A.name = "Victoria Drivers' School";
		A.description = "Vic Description";
		A.contact = "Paul Roberts";
		A.phone = "0430158118";
		A.address = "Clayton, VIC";
		A.postcode = "3166";
		A.location = "-37.988055$145.213997";

		School B = new School();
		B.name = "Melbourne Drivers' School";
		B.description = "Melbourne Description";
		B.contact = "Bill Gates";
		B.phone = "0430111222";
		B.address = "South Yarra, VIC";
		B.postcode = "30000";
		B.location = "-37.986434$145.216094";

		JSONArray jsonArray = new JSONArray();
		jsonArray.put(A);
		jsonArray.put(B);

		fb.put(jsonArray.toString().getBytes());
	}

	@Override
	public void onPutSuccess()
	{
		Toast.makeText(getBaseContext(), "School information updated.", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onPutFailure(Exception exception)
	{
		int t = 3;
	}
}