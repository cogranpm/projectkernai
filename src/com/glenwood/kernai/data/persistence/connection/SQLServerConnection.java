package com.glenwood.kernai.data.persistence.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.glenwood.kernai.data.abstractions.IConnection;
import com.glenwood.kernai.ui.ApplicationData;

public class SQLServerConnection implements IConnection {
	
	private Connection connection;
	
	public Connection getConnection()
	{
		return this.connection;
	}
	
	public SQLServerConnection(String serverName)
	{
		this(serverName, null, null, true, false);
	}
	
	public SQLServerConnection(String serverName, String userName, String password)
	{
		this(serverName, userName, password, false, false);
	}
	
	public SQLServerConnection(String serverName, String userName, String password, Boolean isExpress)
	{
		this(serverName, userName, password, false, isExpress);
	}
	
	public SQLServerConnection(String serverName, String userName, String password, Boolean isTrusted, Boolean isExpress)
	{
		this.isTrustedConnection = false;
		this.userName = userName;
		this.password = password;
		this.serverName = serverName;
		this.isExpress = isExpress;
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
		sb.append(this.serverName);
		sb.append(":1433;");
		if(this.isExpress)
		{
			sb.append("instance=SQLEXPRESS;");
		}
		if(this.isTrustedConnection)
		{
			//url = String.format("jdbc:sqlserver://%s:1433;%sintegratedSecurity=true;", this.serverName, (this.isExpress ? "instance=SQLEXPRESS;" : ""));
			sb.append("integratedSecurity=true;");
		}
		else
		{
			sb.append("user=");
			sb.append(this.userName);
			sb.append(";");
			sb.append("password=");
			sb.append(this.password);
			sb.append(";");
			//url = String.format("jdbc:sqlserver://%s:1433;%suser=%s;password=%s;", this.serverName, (this.isExpress ? "instance=SQLEXPRESS;" : ""), this.userName, this.password);
		}
		return sb.toString();
	}
	
	private String serverName;
	private String userName;
	private String password;
	private Boolean isTrustedConnection;
	private Boolean isExpress;

	@Override
	public String getVendorName() {
		return ApplicationData.CONNECTION_VENDOR_NAME_MSSQL;
	}

}
