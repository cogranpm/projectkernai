package com.glenwood.kernai.data.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public  class DataConnection extends BaseEntity {
	
	public static final String TYPE_NAME = "DATACONNECTION";
	public static final Integer PORT_SQLSERVER = 1433;
	public static final Integer PORT_ORACLE = 1521;
	public static final Integer PORT_MYSQL = 3306;
	
	public DataConnection()
	{
		super();
		this.type = TYPE_NAME;
		if(port == null)
		{
			port = 1;
		}
	}
	
	
	
	
	/* sql server */
	public DataConnection(String userName, String password, Integer port, String serverName, Boolean isExpress,
			String vendorName) {
		super();
		this.userName = userName;
		this.password = password;
		this.port = port;
		this.serverName = serverName;
		this.isExpress = isExpress;
		this.vendorName = vendorName;
	}

	/* oracle */
	public DataConnection(String userName, String password, Integer port, String serverName, String vendorName,
			String sid) {
		super();
		this.userName = userName;
		this.password = password;
		this.port = port;
		this.serverName = serverName;
		this.vendorName = vendorName;
		this.sid = sid;

	}


	private String userName;
	private String password;
	private Integer port;
	private String serverName;
	private Boolean isExpress;
	/* lookup to listdetail */
	private String vendorName;
	private ListDetail vendorNameLookup;
	private String sid;
	
	@JsonProperty
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		String oldValue = this.userName;
		this.userName = userName;
		this.firePropertyChange("userName", oldValue, this.userName);
	}
	@JsonProperty
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		String oldValue = this.password;
		this.password = password;
		this.firePropertyChange("password", oldValue, this.password);
	}
	@JsonProperty
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		Integer oldValue = this.port;
		this.port = port;
		this.firePropertyChange("port", oldValue, this.port);
	}
	@JsonProperty
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		String oldValue = this.serverName;
		this.serverName = serverName;
		this.firePropertyChange("serverName", oldValue, this.serverName);
	}
	
	
	@JsonProperty
	public Boolean getIsExpress() {
		return isExpress;
	}


	public void setIsExpress(Boolean isExpress) {
		Boolean oldValue = this.isExpress;
		this.isExpress = isExpress;
		firePropertyChange("isExpress", oldValue, this.isExpress);
	}
	
	@JsonProperty
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		String oldValue = this.vendorName;
		this.vendorName = vendorName;
		firePropertyChange("vendorName", oldValue, this.vendorName);
	}
	

	@JsonIgnore
	public ListDetail getVendorNameLookup() {
		return vendorNameLookup;
	}

	public void setVendorNameLookup(ListDetail vendorNameLookup) {
		ListDetail oldValue = this.vendorNameLookup;
		this.vendorNameLookup = vendorNameLookup;
		firePropertyChange("vendorNameLookup", oldValue, this.vendorNameLookup);
	}




	@JsonProperty
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		String oldValue = this.sid;
		this.sid = sid;
		firePropertyChange("sid", oldValue, this.sid);
	}
	@Override
	public int hashCode() {
		return Objects.hash(this.serverName, this.type, this.userName, this.password, this.vendorName, this.sid, this.isExpress);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataConnection other = (DataConnection) obj;
                
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!(this.id == other.id))
			return false;

		return true;
	}
	
}
