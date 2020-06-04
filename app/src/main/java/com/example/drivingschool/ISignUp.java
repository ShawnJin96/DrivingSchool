package com.example.drivingschool;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface ISignUp
{
	public void onSignUpSuccess(Task<AuthResult> task);
	public void onSignUpFailure(Task<AuthResult> task);
}