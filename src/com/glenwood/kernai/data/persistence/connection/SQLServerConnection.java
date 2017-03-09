package com.glenwood.kernai.data.persistence.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.glenwood.kernai.data.abstractions.IConnection;
import com.glenwood.kernai.data.entity.MSSQLDataConnection;
import com.glenwood.kernai.ui.ApplicationData;

public class SQLServerConnection implements IConnection {
	
	private MSSQLDataConnection dataConnection;
	private Connection connection;
	
	public Connection getConnection()
	{
		return this.connection;
	}
	
	public SQLServerConnection(MSSQLDataConnection dataConnection)
	{
		this.dataConnection = dataConnection;
	}
	
	
	public void connect()
	{
		try {
			this.connection = DriverManager.getConnection(this.getURL());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect()
	{
		try {
			if((connection != null) && (!connection.isClosed()))
			{
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getURL()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("jdbc:sqlserver://");
		sb.append(this.dataConnection.getServerName());
		sb.append(":");
		sb.append(this.dataConnection.getPort().toString());
		sb.append(";");
		if(this.dataConnection.getIsExpress())
		{
			sb.append("instance=SQLEXPRESS;");
		}
		sb.append("user=");
		sb.append(this.dataConnection.getUserName());
		sb.append(";");
		sb.append("password=");
		sb.append(this.dataConnection.getPassword());
		sb.append(";");

		return sb.toString();
	}
	

	@Override
	public String getVendorName() {
		return ApplicationData.CONNECTION_VENDOR_NAME_MSSQL;
	}

}
