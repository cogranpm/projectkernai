package com.glenwood.kernai.data.entity;

public class OracleDataConnection extends DataConnection {
	public static final String TYPE_NAME = "ORACLEDATACONNECTION";
	
	public OracleDataConnection()
	{
		super();
		this.type = TYPE_NAME;
	}
	
	public OracleDataConnection(String serverName, String userName, String password, String sid, Integer port)
	{
		this();
		this.setServerName(serverName);
		this.setUserName(userName);
		this.setPassword(password);
		this.setSid(sid);
		this.setPort(port);
	}
	
	private String sid;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		String oldValue = this.sid;
		this.sid = sid;
		firePropertyChange("sid", oldValue, this.sid);
	}
	
	
}
