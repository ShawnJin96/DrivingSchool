package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.FirebaseApp;

public class SplashActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		FirebaseApp.initializeApp(this);

		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				startActivity(new Intent(SplashActivity.this, SignInActivity.class));
				finish();
			}
		}, 2000);
	}
}
