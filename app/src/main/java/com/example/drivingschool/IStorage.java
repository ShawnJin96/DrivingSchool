package com.example.drivingschool;

public interface IStorage
{
	public void onGetSuccess(byte[] data);
	public void onGetFailure(Exception exception);

	public void onPutSuccess();
	public void onPutFailure(Exception exception);
}