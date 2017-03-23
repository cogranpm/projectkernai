package com.glenwood.kernai.ui.workers;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.glenwood.kernai.data.abstractions.IConnection;
import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.modelimport.ColumnDefinition;
import com.glenwood.kernai.data.modelimport.DatabaseDefinition;
import com.glenwood.kernai.data.modelimport.ForeignKeyDefinition;
import com.glenwood.kernai.data.modelimport.PrimaryKeyDefinition;
import com.glenwood.kernai.data.modelimport.TableDefinition;
import com.glenwood.kernai.data.persistence.JDBCManager;
import com.glenwood.kernai.data.persistence.connection.OracleConnection;
import com.glenwood.kernai.data.persistence.connection.SQLServerConnection;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.IImportWorkerClient;

public class ImportWorker {
	
	private IConnection connection;
	private JDBCManager man;
	
	public ImportWorker(DataConnection dataConnection)
	{
		this();
		if(dataConnection.getVendorNameLookup() == null)
		{
			throw new NullPointerException("DataConnection, vendor name was null");
		}
		if(ApplicationData.CONNECTION_VENDOR_NAME_MSSQL.equalsIgnoreCase(dataConnection.getVendorNameLookup().getKey()))
		{
			this.connection = new SQLServerConnection(dataConnection);
		}
		else if(ApplicationData.CONNECTION_VENDOR_NAME_ORACLE.equalsIgnoreCase(dataConnection.getVendorNameLookup().getKey()))
		{
			this.connection = new OracleConnection(dataConnection);
		}
		else if(ApplicationData.CONNECTION_VENDOR_NAME_MYSQL.equalsIgnoreCase(dataConnection.getVendorNameLookup().getKey()))
		{
			//this.connection = new my
		}
		
	}
	

	public ImportWorker()
	{

	}
	
	public void openConnection(IImportWorkerClient client, Display display)
	{
		this.getConnectionWorker(client, display).start();
		
		/*
		man = new JDBCManager(connection);
		man.connect();
		//man.setImportEngine(new ImportEngineSchemaCrawler());
		man.getImportEngine().init(connection);
		client.onConnect();
		*/
	}
	
	public void closeConnection()
	{
		man.disconnect();
	}
	
	public void getDatabases(IImportWorkerClient client, Display display)
	{
		this.getDatabasesWorker(client, display).start();
	}
	
	private Thread getConnectionWorker(IImportWorkerClient client, Display display)
	{
		return new Thread() {
			@Override
			public void run() {
				man = new JDBCManager(connection);
				man.connect();
				//man.setImportEngine(new ImportEngineSchemaCrawler());
				man.getImportEngine().init(connection);
				display.syncExec(new Runnable() {
					
					@Override
					public void run() {
						client.onConnect();
					}
				});
			}
		};
	}
	
	private Thread getDatabasesWorker(IImportWorkerClient client, Display display)
	{
		return new Thread() {
			@Override
			public void run() {
				List<DatabaseDefinition> databases = man.getImportEngine().getDatabases();
				display.syncExec(new Runnable() {
					
					@Override
					public void run() {
						client.setDatabases(databases);
					}
				});
			}
			
			
		};
	}
	
	/*
	private Thread getDatabasesWorker(Display display)
	{
		return new Thread() {
			 @Override
			public void run() {

				 List<DatabaseDefinition> databases = man.getImportEngine().getDatabases();
				 for(DatabaseDefinition database : databases)
				 {
					 if((connection.getVendorName().equals(ApplicationData.CONNECTION_VENDOR_NAME_MSSQL) 
							 && "AdventureWorks2012".equalsIgnoreCase(database.getName()))
						|| (connection.getVendorName().equals(ApplicationData.CONNECTION_VENDOR_NAME_ORACLE)
						&& "HR".equalsIgnoreCase(database.getName())))
					 {
						 System.out.println(database.getName());		
						 man.getImportEngine().getTables(database, true, false);
						 for(TableDefinition table : database.getTables())
						 {
							 System.out.println("TABLE: " + table.getName());
							 man.getImportEngine().getColumns(database, table);
							 for(ColumnDefinition column : table.getColumns())
							 {
								 System.out.println("Column:" + column.getName());
								 System.out.println("Type: " + column.getDataType());
								 System.out.println("DataType:" + column.getDbTypeName());
								 System.out.println("Size: " + column.getSize());
								 System.out.println("Default:" + column.getDefaultValue());
								 System.out.println("AutoIncrement:" + column.getIsAutoIncrement());
								 System.out.println("IsNullable: " + column.getIsNullable());
								 System.out.println("Nullable: " + column.getNullable());
								 System.out.println("Remarks: " + column.getRemarks());
								 System.out.println("SourceDataType: " + column.getSourceDataType());

							 }
							 
							 System.out.println("Primary Keys");
							 for (PrimaryKeyDefinition key : table.getPrimaryKeys())
							 {
								 System.out.println("Name: " + key.getKeyName() + " Column: " + key.getColumnName());
							 }
							 
							 System.out.println("Foreign Keys - where I am referenced in another table");
							 for (ForeignKeyDefinition key : table.getForeignKeys())
							 {
								 System.out.println("Table: " + key.getTableName() + " Column: " + key.getColumnName()
								 + " Sequence: " + key.getKeySequence());
							 }
						 }
						 
					 }
					 
					
					
				 }
				 man.disconnect();
				 
				 display.asyncExec(new Runnable() {
					
					@Override
					public void run() {
						MessageDialog.openInformation(display.getActiveShell(), "Complete", "Import Complete");
						
					}
				});
			}
		};
	}

	*/
	


}
