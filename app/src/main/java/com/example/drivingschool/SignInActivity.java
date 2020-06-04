package com.example.drivingschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity implements ISignIn
{
	public FireBase fb;

	public String emailaddress;
	public String password;

	public EditText emailet;
	public EditText passwordet;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
		setCustomActionBar();

		emailet = findViewById(R.id.emailET);
		passwordet = findViewById(R.id.passwordET);

		fb = new FireBase();
		fb.setISignIn(this);
	}

	public void onSignIn(View view)
	{
		emailaddress = emailet.getText().toString();
		password = passwordet.getText().toString();

		if (!Pattern.compile(getString(R.string.emai_regx)).matcher(emailaddress).matches())
		{
			Toast.makeText(getBaseContext(), "E-mail format is invalid. Please confirm and try again.", Toast.LENGTH_LONG).show();
			return;
		}

		if (password.isEmpty())
		{
			Toast.makeText(getBaseContext(), "Password cannot be left blank. Please confirm and try again.", Toast.LENGTH_LONG).show();
			return;
		}

		if (emailaddress.equals("jinzhanghao54@gmail.com"))
			MainActivity.isAdmin = true;

		fb.signIn(emailaddress, password);
	}

	public void onSignUp(View view)
	{
		startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
	}

	private void setCustomActionBar()
	{
		ActionBar.LayoutParams lp =new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		View mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar, null);
		ActionBar actionBar = getSupportActionBar();
		assert actionBar != null;
		actionBar.setCustomView(mActionBarView, lp);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
	}

	@Override
	public void onSignInSuccess(Task<AuthResult> task)
	{
		startActivity(new Intent(SignInActivity.this, MainActivity.class));
	}

	@Override
	public void onSignInFailure(Task<AuthResult> task)
	{
		Toast.makeText(getBaseContext(), String.format("SignIn failed: %s", Objects.requireNonNull(task.getException()).getMessage()), Toast.LENGTH_LONG).show();
	}
}