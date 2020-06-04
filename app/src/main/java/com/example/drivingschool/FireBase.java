package com.example.drivingschool;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

class FireBase
{
	public ISignUp signUpInterface;
	public ISignIn signInInterface;
	public IStorage storageInterface;

	public FirebaseAuth auth;
	public FirebaseStorage storage;

	FireBase()
	{
		auth = FirebaseAuth.getInstance();
		storage = FirebaseStorage.getInstance();
	}

	void setISignUp(ISignUp iSignUp)
	{
		this.signUpInterface = iSignUp;
	}

	void setISignIn(ISignIn iSignIn)
	{
		this.signInInterface = iSignIn;
	}

	void setIStorage(IStorage iStorage)
	{
		this.storageInterface = iStorage;
	}

	void signUp(String account, String password)
	{
		auth.createUserWithEmailAndPassword(account, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
		{
			@Override
			public void onComplete(@NonNull Task<AuthResult> task)
			{
				try
				{
					if (task.isSuccessful())
					{
						signUpInterface.onSignUpSuccess(task);
					}
					else
					{
						signUpInterface.onSignUpFailure(task);
					}
				}
				catch (Exception ignored){}
			}
		});
	}

	void signIn(String email, String password)
	{
		auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
		{
			@Override
			public void onComplete(@NonNull Task<AuthResult> task)
			{
				try
				{
					if (task.isSuccessful())
					{
						signInInterface.onSignInSuccess(task);
					}
					else
					{
						signInInterface.onSignInFailure(task);
					}
				}
				catch (Exception ignored) {}
			}
		});
	}

	void get()
	{
		storage.getReference().child("list.json").getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>()
		{
			@Override
			public void onSuccess(byte[] bytes)
			{
				try
				{
					storageInterface.onGetSuccess(bytes);
				}
				catch (Exception ignored) {}
			}
		}).addOnFailureListener(new OnFailureListener()
		{
			@Override
			public void onFailure(@NonNull Exception exception)
			{
				try
				{
					storageInterface.onGetFailure(exception);
				}
				catch (Exception ignored) {}
			}
		});
	}

	void put(byte[] data)
	{
		storage.getReference().child("list.json").putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
		{
			@Override
			public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
			{
				try
				{
					storageInterface.onPutSuccess();
				}
				catch (Exception ignored) {}
			}
		}).addOnFailureListener(new OnFailureListener()
		{
			@Override
			public void onFailure(@NonNull Exception exception)
			{
				try
				{
					storageInterface.onPutFailure(exception);
				}
				catch (Exception ignored) {}
			}
		});
	}
}