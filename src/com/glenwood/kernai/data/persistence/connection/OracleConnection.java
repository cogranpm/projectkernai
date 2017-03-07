package com.glenwood.kernai.data.persistence.connection;

import java.sql.Connection;
import java.sql.SQLException;

import com.glenwood.kernai.data.abstractions.IConnection;
import com.glenwood.kernai.ui.ApplicationData;

import oracle.jdbc.pool.OracleDataSource;

public class OracleConnection implements IConnection {
	
	private String serverName;
	private String userName;
	private String password;
	private Boolean isExpress;
	
	OracleDataSource dataSource;
	Connection connection;
	
	
	public OracleConnection(String serverName)
	{
		this(serverName, null, null, false);
	}
	
	public OracleConnection(String serverName, String userName, String password)
	{
		this(serverName, userName, password, false);
	}

	
	public OracleConnection(String serverName, String userName, String password, Boolean isExpress)
	{
		this.userName = userName;
		this.password = password;
		this.serverName = serverName;
		this.isExpress = isExpress;
	}
	
	private String getURL()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("jdbc:oracle:thin:");
	/*	sb.append(this.userName);
		sb.append("/");
		sb.append(this.password);
	*/
		sb.append("@");
		sb.append(this.serverName);
		sb.append(":1521");
		if(this.isExpress)
		{
			sb.append(":xe;");
		}
		return sb.toString();
	}

	@Override
	public void connect() {
		try {
			this.dataSource = new OracleDataSource();
			String url = this.getURL();
			System.out.println(url);
			this.dataSource.setURL(url);
			this.dataSource.setUser(this.userName);
			this.dataSource.setPassword(this.password);
			this.connection = this.dataSource.getConnection();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void disconnect() {
		try {
			if(this.dataSource != null && this.dataSource.getConnection() != null && !this.dataSource.getConnection().isClosed())
			{
				this.dataSource.getConnection().close();
				//this.dataSource.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public String getVendorName() {
		return ApplicationData.CONNECTION_VENDOR_NAME_ORACLE;
	}

}
