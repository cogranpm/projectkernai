package com.glenwood.kernai.data.persistence;

import com.glenwood.kernai.data.abstractions.IConnection;
import com.glenwood.kernai.data.abstractions.IImportEngine;
import com.glenwood.kernai.data.modelimport.ImportEngineBase;

public class JDBCManager {
	
	private IConnection connection;
	private IImportEngine importEngine;

	public IImportEngine getImportEngine() {
		return importEngine;
	}

	public void setImportEngine(IImportEngine importEngine) {
		this.importEngine = importEngine;
	}

	public JDBCManager(IConnection connection)
	{
		this.connection = connection;
		this.importEngine = new ImportEngineBase();
	}
	
	public void connect()
	{
		this.connection.connect();
	}
	
	public void disconnect()
	{
		this.connection.disconnect();
	}

}
