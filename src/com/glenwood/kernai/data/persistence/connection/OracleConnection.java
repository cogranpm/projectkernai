package com.glenwood.kernai.data.persistence.connection;

import java.sql.Connection;
import java.sql.SQLException;

import com.glenwood.customExceptions.OracleConnectionException;
import com.glenwood.kernai.data.abstractions.IConnection;
import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.ui.ApplicationData;

import oracle.jdbc.pool.OracleDataSource;

public class OracleConnection implements IConnection {
	

	
	OracleDataSource dataSource;
	Connection connection;
	DataConnection dataConnection;
	
	public OracleConnection(DataConnection dataConnection)
	{
		this.dataConnection = dataConnection;
	}
	
	
	
	private String getURL()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("jdbc:oracle:thin:");
		sb.append(this.dataConnection.getUserName());
		sb.append("/");
		sb.append(this.dataConnection.getPassword());
	
		sb.append("@//");
		sb.append(this.dataConnection.getServerName());
		sb.append(":");
		sb.append(this.dataConnection.getPort().toString());
		sb.append("/");
		sb.append(this.dataConnection.getSid());
		return sb.toString();
	}
	
	@Override
	public DataConnection getDataConnection() {
		return this.getDataConnection();
	}

	@Override
	public void connect() {
		try {
			this.dataSource = new OracleDataSource();
			String url = this.getURL();
			this.dataSource.setURL(url);
			this.connection = this.dataSource.getConnection();
			
		} catch (SQLException e) {
			throw new OracleConnectionException(e);
		}

	}

	@Override
	public void disconnect() {
		try {
			if(this.dataSource != null && this.dataSource.getConnection() != null && !this.dataSource.getConnection().isClosed())
			{
				this.dataSource.getConnection().close();
			}
		} catch (SQLException e) {
			throw new OracleConnectionException(e);
		}
	}

	@Override
	public Connection getConnection() {
		return this.connection;
	}
	
	@Override
	public boolean isValid() {
		if(this.getConnection() == null)
		{
			return false;
		}
		else
		{
			try{
				return this.getConnection().isValid(ApplicationData.SQL_TIMEOUT_VALID_CHECK);
			}
			catch(SQLException exception)
			{
				return false;
			}
		}
		
	}

	@Override
	public String getVendorName() {
		return ApplicationData.CONNECTION_VENDOR_NAME_ORACLE;
	}

}
