package com.glenwood.kernai.data.abstractions;

import java.sql.Connection;

public interface IConnection {
	
	public void connect();
	public void disconnect();
	public Connection getConnection();
	public String getVendorName();

}
