package com.example.drivingschool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class SchoolListAdapter extends ArrayAdapter<School>
{
	public ArrayList<School> schools;

	SchoolListAdapter(Context context, ArrayList<School> schools)
	{
		super(context, R.layout.school_item, schools);

		this.schools = new ArrayList<>();
		this.schools.addAll(schools);
	}

	@NonNull
	@Override
	public View getView(int position, View schoolItem, @NonNull ViewGroup parent)
	{
		School school = schools.get(position);

		if (schoolItem == null)
		{
			schoolItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_item, parent, false);
		}

		TextView nameTV = schoolItem.findViewById(R.id.name);
		TextView descriptionTV = schoolItem.findViewById(R.id.description);
		TextView contactTV = schoolItem.findViewById(R.id.contact);
		TextView phoneTV = schoolItem.findViewById(R.id.phone);
		TextView addressTV = schoolItem.findViewById(R.id.address);
		TextView postcodeTV = schoolItem.findViewById(R.id.postcode);

		nameTV.setText(school.name);
		descriptionTV.setText(school.description);
		contactTV.setText(school.contact);
		phoneTV.setText(school.phone);
		addressTV.setText(school.address);
		postcodeTV.setText(school.postcode);

		return schoolItem;
	}
}