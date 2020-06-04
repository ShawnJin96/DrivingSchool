package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements ISignUp
{
	private FireBase fb;

	private EditText emailet;
	private EditText passwordet;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
		setTitle(R.string.sign_up);

		emailet = findViewById(R.id.emailET);
		passwordet = findViewById(R.id.passwordET);

		fb = new FireBase();
		fb.setISignUp(this);
	}

	public void onSignUp(View view)
	{
		String mEMail = emailet.getText().toString();
		String mPassword = passwordet.getText().toString();

		if (!Pattern.compile(getString(R.string.emai_regx)).matcher(mEMail).matches())
		{
			Toast.makeText(getBaseContext(), "E-mail format is invalid. Please confirm and try again.", Toast.LENGTH_LONG).show();
			return;
		}

		if (mPassword.isEmpty())
		{
			Toast.makeText(getBaseContext(), "Password cannot be left blank. Please confirm and try again.", Toast.LENGTH_LONG).show();
			return;
		}

		if (mEMail.equals("jinzhanghao54@gmail.com"))
			MainActivity.isAdmin = true;

		fb.signUp(mEMail, mPassword);
	}

	@Override
	public void onSignUpSuccess(Task<AuthResult> task)
	{
		startActivity(new Intent(SignUpActivity.this, MainActivity.class));
	}

	@Override
	public void onSignUpFailure(Task<AuthResult> task)
	{
		Toast.makeText(getBaseContext(), String.format("Sign-up Failed: %s", Objects.requireNonNull(task.getException()).getMessage()), Toast.LENGTH_LONG).show();
	}
}
