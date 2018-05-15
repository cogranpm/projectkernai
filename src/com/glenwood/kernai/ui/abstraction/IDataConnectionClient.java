package com.glenwood.kernai.ui.abstraction;

public interface IDataConnectionClient {
	public void onConnectError(String message);
	public void onConnect();

}
