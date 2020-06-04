package com.example.drivingschool;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface ISignIn
{
	public void onSignInSuccess(Task<AuthResult> task);
	public void onSignInFailure(Task<AuthResult> task);
}