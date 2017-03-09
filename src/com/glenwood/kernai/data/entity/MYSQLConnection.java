package com.glenwood.kernai.data.entity;

public class MYSQLConnection extends DataConnection {
	
	public static final String TYPE_NAME = "MYSQLDATACONNECTION";

	public MYSQLConnection()
	{
		super();
		this.type = TYPE_NAME;
	}
}
