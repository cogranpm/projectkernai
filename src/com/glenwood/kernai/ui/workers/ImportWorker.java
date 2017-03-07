package com.glenwood.kernai.ui.workers;

import java.util.List;

import com.glenwood.kernai.data.abstractions.IConnection;
import com.glenwood.kernai.data.modelimport.ColumnDefinition;
import com.glenwood.kernai.data.modelimport.DatabaseDefinition;
import com.glenwood.kernai.data.modelimport.TableDefinition;
import com.glenwood.kernai.data.modelimport.UserDefinedTypeDefinition;
import com.glenwood.kernai.data.persistence.JDBCManager;
import com.glenwood.kernai.data.persistence.connection.OracleConnection;

public class ImportWorker {
	
	private IConnection connection;
	private JDBCManager man;
	
	public ImportWorker()
	{
		// connection = new SQLServerConnection("kron1", "dotconnectservice", "reddingo", true);
		connection = new OracleConnection("localhost", "SYS", "reddingo", true);
		 man = new JDBCManager(connection);
		 man.connect();
		 //man.setImportEngine(new ImportEngineSchemaCrawler());
		 man.getImportEngine().init(connection);
	}
	

	
	
	
	private Thread getDatabasesWorker()
	{
		return new Thread() {
			 @Override
			public void run() {

				 List<DatabaseDefinition> databases = man.getImportEngine().getDatabases();
				 for(DatabaseDefinition database : databases)
				 {
					 System.out.println(database.getName());
					 for(UserDefinedTypeDefinition udt : database.getUserDefinedTypes())
					 {
						 System.out.println(udt.toString());
					 }
					 man.getImportEngine().getTables(database);
					 if(database.getName().equalsIgnoreCase("AdventureWorks2012"))
					 {
						 for(TableDefinition table : database.getTables())
						 {
							 //System.out.println("TABLE: " + table.getName());
							 man.getImportEngine().getColumns(database, table);
							 for(ColumnDefinition column : table.getColumns())
							 {
								 System.out.println("Column:" + column.getName());
								 System.out.println("Type: " + column.getDataType());
								 System.out.println("DataType:" + column.getDbTypeName());
								 System.out.println("Table was: " + table.getName() + "**************************************");

								 /*
								 
								 System.out.println("Size: " + column.getSize());
								 System.out.println("Default:" + column.getDefaultValue());
								 System.out.println("AutoIncrement:" + column.getIsAutoIncrement());
								 System.out.println("IsNullable: " + column.getIsNullable());
								 System.out.println("Nullable: " + column.getNullable());
								 System.out.println("Remarks: " + column.getRemarks());
								 System.out.println("SourceDataType: " + column.getSourceDataType());
								 */
							 }
						 }
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
