package com.glenwood.kernai.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MSSQLDataConnection extends DataConnection {
	public static final String TYPE_NAME = "MSSQLDATACONNECTION";
	
	public MSSQLDataConnection()
	{
		super();
		this.type = TYPE_NAME;
	}
	
	public MSSQLDataConnection(String serverName, String userName, String password, Boolean isExpress, Integer port)
	{
		this();
		this.setServerName(serverName);
		this.setUserName(userName);
		this.setPassword(password);
		this.setIsExpress(isExpress);
		this.setPort(port);
	}

	
	private Boolean isExpress;

	@JsonProperty
	public Boolean getIsExpress() {
		return isExpress;
	}


	public void setIsExpress(Boolean isExpress) {
		Boolean oldValue = this.isExpress;
		this.isExpress = isExpress;
		firePropertyChange("isExpress", oldValue, this.isExpress);
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MSSQLDataConnection [isExpress=");
		builder.append(isExpress);
		builder.append(", getUserName()=");
		builder.append(getUserName());
		builder.append(", getPassword()=");
		builder.append(getPassword());
		builder.append(", getPort()=");
		builder.append(getPort());
		builder.append(", getServerName()=");
		builder.append(getServerName());
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getType()=");
		builder.append(getType());
		builder.append("]");
		return builder.toString();
	}
	
	
}
