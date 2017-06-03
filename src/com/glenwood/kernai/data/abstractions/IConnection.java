package com.glenwood.kernai.data.abstractions;

import java.sql.Connection;

import com.glenwood.kernai.data.entity.DataConnection;

public interface IConnection {
	
	public void connect();
	public void disconnect();
	public Connection getConnection();
	public String getVendorName();
	public DataConnection getDataConnection();
	public boolean isValid();

}
