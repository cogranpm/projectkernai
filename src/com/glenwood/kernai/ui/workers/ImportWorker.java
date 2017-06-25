/* note: this is wrong, don't use threads directly, use executors and tasks instead */

package com.glenwood.kernai.ui.workers;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.swt.widgets.Display;

import com.glenwood.kernai.data.abstractions.IConnection;
import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.modelimport.DatabaseDefinition;
import com.glenwood.kernai.data.modelimport.TableDefinition;
import com.glenwood.kernai.data.persistence.JDBCManager;
import com.glenwood.kernai.data.persistence.connection.OracleConnection;
import com.glenwood.kernai.data.persistence.connection.SQLServerConnection;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.IDataConnectionClient;
import com.glenwood.kernai.ui.abstraction.IDataTableSelectorClient;

public class ImportWorker {
	
	private IConnection connection;
	private JDBCManager manager;
	
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
	
	public IConnection getConnection()
	{
		return this.connection;
	}
	
	public JDBCManager getManager()
	{
		return this.manager;
	}
	

	public ImportWorker()
	{

	}
	
	public void openConnection(IDataConnectionClient client, Display display)
	{
		//this.getConnectionWorker(client, display).start();
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(this.getConnectionWorker(client, display));
		executor.shutdown();
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
		manager.disconnect();
	}
	
	public void getDatabases(IDataTableSelectorClient client, Display display)
	{	
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(this.getDatabasesWorker(client, display));
		executor.shutdown();
		//this.getDatabasesWorker(client, display).start();
	}
	
	public void getTables(IDataTableSelectorClient client, Display display, DatabaseDefinition database)
	{
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(this.getTablesWorker(client, display, database));
		executor.shutdown();
	}
	
	private Runnable getConnectionWorker(IDataConnectionClient client, Display display)
	{
		Runnable connector = new Runnable() {
			@Override
			public void run() {
				try
				{
					if(manager == null)
					{
						manager = new JDBCManager(connection);
					}
					manager.connect();
					//man.setImportEngine(new ImportEngineSchemaCrawler());
					manager.getImportEngine().init(connection);

					display.syncExec(new Runnable() {

						@Override
						public void run() {
							client.onConnect();
						}
					});
				}
				catch(Exception e)
				{
					display.syncExec(new Runnable() {
						
						@Override
						public void run() {
							client.onConnectError(e.getMessage());
						}
					});
				}
			}
		};
		return connector;
	}
	
	
	private Runnable getDatabasesWorker(IDataTableSelectorClient client, Display display)
	{
		return new Runnable() {
			@Override
			public void run() {
				List<DatabaseDefinition> databases = manager.getImportEngine().getDatabases();
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						client.setDatabases(databases);
					}
				});
			}
		};
	}
	
	private Runnable getTablesWorker(IDataTableSelectorClient client, Display display, DatabaseDefinition database)
	{
		return new Runnable() {
			@Override
			public void run() {
				
				List<TableDefinition> tables = manager.getImportEngine().getTables(database, true, false);
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						
						client.setTables(tables);
						
					}
				});
			}
		};
	}
	
	/*
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
