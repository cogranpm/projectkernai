package com.glenwood.kernai.ui.workers;

import java.util.List;

import com.glenwood.kernai.data.abstractions.IConnection;
import com.glenwood.kernai.data.modelimport.ColumnDefinition;
import com.glenwood.kernai.data.persistence.JDBCManager;
import com.glenwood.kernai.data.persistence.connection.SQLServerConnection;

public class ImportWorker {
	
	private IConnection connection;
	private JDBCManager man;
	
	public ImportWorker()
	{
		 connection = new SQLServerConnection("kron1", "dotconnectservice", "reddingo", true);
		 man = new JDBCManager(connection);
		 man.connect();
		 man.getImportEngine().init(connection);
	}
	

	
	
	
	private Thread getDatabasesWorker()
	{
		return new Thread() {
			 @Override
			public void run() {

				 List<String> databases = man.getImportEngine().getDatabases();
				 for(String database : databases)
				 {
					 System.out.println(database);
					 int tableCounter = 0;
					 List<String> tables = man.getImportEngine().getTables(database);
					 for(String table : tables)
					 {
						 System.out.println(table);
						 List<ColumnDefinition> columns = man.getImportEngine().getColumns(database, table);
						 for(ColumnDefinition column : columns)
						 {
							 System.out.println(column.getName());
						 }
						 if(tableCounter > 50)
						 {
							 break;
						 }
						 tableCounter++;
					 }
				 }
				 man.disconnect();
			}
		};
	}


	
	public void getDatabases()
	{
		this.getDatabasesWorker().start();
	}

}
