package com.glenwood.kernai.data.modelimport;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.data.abstractions.IConnection;
import com.glenwood.kernai.data.abstractions.IImportEngine;
import com.glenwood.kernai.ui.ApplicationData;

public class ImportEngineBase implements IImportEngine{
	
	private IConnection connection;
	private DatabaseMetaData metaData;
	
	private static final String[] DB_TABLE_TYPES = { "TABLE" };
	private static final String[] DB_VIEW_TYPES = { "VIEW" };
	private static final String[] DB_MIXED_TYPES = { "TABLE", "VIEW" };
	private static final String COLUMN_NAME_TABLE_NAME = "TABLE_NAME";
	private static final String COLUMN_NAME_COLUMN_NAME = "COLUMN_NAME";
	private static final String COLUMN_NAME_DATA_TYPE = "DATA_TYPE";
	private static final String COLUMN_NAME_SOURCE_DATA_TYPE = "SOURCE_DATA_TYPE";
 	private static final String COLUMN_NAME_TYPE_NAME = "TYPE_NAME";
 	private static final String COLUMN_NAME_COLUMN_SIZE = "COLUMN_SIZE";
 	private static final String COLUMN_NAME_NULLABLE = "NULLABLE";
 	private static final String COLUMN_NAME_ORDINAL_POSITION = "ORDINAL_POSITION";
 	private static final String COLUMN_NAME_REMARKS = "REMARKS";
 	private static final String COLUMN_NAME_DEFAULT = "COLUMN_DEF";
 	private static final String COLUMN_NAME_IS_NULLABLE = "IS_NULLABLE";
 	private static final String COLUMN_NAME_IS_AUTOINCREMENT = "IS_AUTOINCREMENT";
 	private static final String COLUMN_NAME_IS_GENERATED = "IS_GENERATEDCOLUMN";
 	private static final String COLUMN_NAME_KEY_SEQ_NAME = "KEY_SEQ";
 	private static final String COLUMN_NAME_KEY_NAME = "PK_NAME";
 	private static final String COLUMN_NAME_FOREIGN_TABLE_NAME = "FKTABLE_NAME";
 	private static final String COLUMN_NAME_FOREIGN_COLUMN_NAME = "FKCOLUMN_NAME";
 	private static final String COLUMN_NAME_CLASS_NAME = "CLASS_NAME";
 	private static final String COLUMN_NAME_BASE_TYPE = "BASE_TYPE";
 	
 	private static final String ORACLE_VIEWS =
 			"select object_name from ALL_OBJECTS  where object_type = 'TABLE' and owner = ? order by object_name";
 	private static final String ORACLE_TABLES =
 			"select object_name from ALL_OBJECTS  where object_type = 'TABLE' and owner = ? order by object_name";
 	private static final String ORACLE_TABLES_AND_VIEWS =
 			"select object_name from ALL_OBJECTS  where (object_type = 'TABLE' or object_type = 'VIEW' ) and owner = ? order by object_name";
	
	@Override
	public synchronized void init(IConnection connection)
	{
		this.connection = connection;
		try {
			this.metaData = connection.getConnection().getMetaData();
			int minorVersion = metaData.getDatabaseMinorVersion();
//			System.out.println(String.format("MinorVersion %d", minorVersion));
//			System.out.println(String.format("Product Name %s", metaData.getDatabaseProductName()));
//			System.out.println(String.format("Product Version %s", metaData.getDatabaseProductVersion()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized List<DatabaseDefinition> getDatabases()
	{
		List<DatabaseDefinition> list = new ArrayList<DatabaseDefinition>();
		ResultSet catalogs = null;
		try {

			if(this.connection.getVendorName().equals(ApplicationData.CONNECTION_VENDOR_NAME_ORACLE))
			{
				catalogs = this.metaData.getSchemas();
			}
			else
			{
				catalogs = this.metaData.getCatalogs();
			}
			while(catalogs.next())
			{
				String catalog = catalogs.getString(1);
				if(catalog != null)
				{
					DatabaseDefinition database = new DatabaseDefinition(catalog);
					this.getUserDefinedTypes(database);
					list.add(database);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			this.closeResultSet(catalogs);
		}
		return list;
	}
	
	
	@Override
	public synchronized List<TableDefinition> getTables(DatabaseDefinition database, Boolean getTables, Boolean getViews) {
		if(ApplicationData.CONNECTION_VENDOR_NAME_ORACLE.equalsIgnoreCase(this.connection.getVendorName()))
		{
			return this.getOracleTables(database, getTables, getViews);
		}
		else
		{
			return this.getTablesFromStandard(database, getTables, getViews);
		}
	}
	
	private synchronized List<TableDefinition> getTablesFromStandard(DatabaseDefinition database, Boolean getTables, Boolean getViews) {
		ResultSet results = null;
		try {
			String[] types = null;
			if(getTables && getViews)
			{
				types = DB_MIXED_TYPES;
			}
			else if(getTables)
			{
				types = DB_TABLE_TYPES;
			}
			else if(getViews)
			{
				types = DB_VIEW_TYPES;
			}
			else
			{
				return database.getTables();
			}
			results = metaData.getTables(database.getName(), null, null, DB_TABLE_TYPES);
			while(results.next())
			{
				String name = this.getTrimmedColumn(results, COLUMN_NAME_TABLE_NAME);
				if(name != null)
				{
					TableDefinition table = new TableDefinition(name, database, false);
					database.getTables().add(table);
					this.getPrimaryKeys(table);
					this.getForeignKeys(table);
				}
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			this.closeResultSet(results);
		}
		return database.getTables();
	}
	
	
	private synchronized List<TableDefinition> getOracleTables(DatabaseDefinition database, Boolean getTables, Boolean getViews) {
		ResultSet results = null;
		PreparedStatement stmt = null;
		try {
			String statementSql = ORACLE_TABLES;
			if(getTables && getViews)
			{
				statementSql = ORACLE_TABLES_AND_VIEWS;
			}
			else if(getTables)
			{
				statementSql = ORACLE_TABLES;
			}
			else if(getViews)
			{
				statementSql = ORACLE_VIEWS;
			}
			else
			{
				return database.getTables();
			}
			stmt = this.connection.getConnection().prepareStatement(statementSql);
			stmt.setString(1, database.getName());
			results = stmt.executeQuery();
			if(results != null)
			{
				while(results.next())
				{
					String name = this.getTrimmedColumn(results, "object_name");
					if(name != null)
					{
						TableDefinition table = new TableDefinition(name, database, false);
						database.getTables().add(table);
						this.getPrimaryKeys(table);
						this.getForeignKeys(table);
					}
					
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			this.closeResultSet(results);
			this.closeStatement(stmt);
		}
		return database.getTables();
	}
	
	
	
	@Override
	public synchronized List<ColumnDefinition> getColumns(DatabaseDefinition database, TableDefinition table) {
		ResultSet results = null;
		try {

			results = metaData.getColumns(database.getName(), null, table.getName().toUpperCase(), null );
			while(results.next())
			{
				String name = this.getTrimmedColumn(results, COLUMN_NAME_COLUMN_NAME);
				if(name != null && name != "")
				{
					/* this value represents the java.sql.Type constant
					 * https://docs.oracle.com/javase/6/docs/api/constant-values.html#java.sql.Types
					 * note that it is accurate (tested sql server) for user defined types also
					 */
					int dataType = results.getInt(COLUMN_NAME_DATA_TYPE);
					String dbTypeName = this.getTrimmedColumn(results, COLUMN_NAME_TYPE_NAME);
					int size = results.getInt(COLUMN_NAME_COLUMN_SIZE);
					int nullable = results.getInt(COLUMN_NAME_NULLABLE);
					String remarks = this.getTrimmedColumn(results, COLUMN_NAME_REMARKS);
					String defaultValue = this.getTrimmedColumn(results, COLUMN_NAME_DEFAULT);
					String isNullable = this.getTrimmedColumn(results, COLUMN_NAME_IS_NULLABLE);
					String isAutoIncrement = this.getTrimmedColumn(results, COLUMN_NAME_IS_AUTOINCREMENT);
					short sourceDataType = 0;
					if(!this.connection.getVendorName().equalsIgnoreCase(ApplicationData.CONNECTION_VENDOR_NAME_MSSQL))
					{
						sourceDataType = results.getShort(COLUMN_NAME_SOURCE_DATA_TYPE);
					}
					//String isGenerated = this.getTrimmedColumn(results, COLUMN_NAME_IS_GENERATED);
					ColumnDefinition column = new ColumnDefinition(name, 
							dataType, 
							dbTypeName, 
							sourceDataType,
							size, 
							nullable, 
							remarks, 
							defaultValue, 
							isNullable, 
							isAutoIncrement, 
							table);
					table.getColumns().add(column);
				}
				
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			this.closeResultSet(results);
		}
		return table.getColumns();
	}

	private synchronized void getPrimaryKeys(TableDefinition table)
	{
		ResultSet primaryKeyResults = null;
		try
		{
			primaryKeyResults = metaData.getPrimaryKeys(table.getDatabase().getName(), null, table.getName().toUpperCase());
			while(primaryKeyResults.next())
			{
				String columnName = this.getTrimmedColumn(primaryKeyResults, COLUMN_NAME_COLUMN_NAME);
				short keySequence = primaryKeyResults.getShort(COLUMN_NAME_KEY_SEQ_NAME);
				String keyName = this.getTrimmedColumn(primaryKeyResults, COLUMN_NAME_KEY_NAME);
				PrimaryKeyDefinition key = new PrimaryKeyDefinition(columnName, keyName, keySequence, table);
				table.getPrimaryKeys().add(key);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.closeResultSet(primaryKeyResults);
		}
	}
	
	
	private synchronized void getForeignKeys(TableDefinition table)
	{
		ResultSet keyResults = null;
		try
		{
			keyResults = metaData.getExportedKeys(table.getDatabase().getName(), null, table.getName().toUpperCase());
			while(keyResults.next())
			{
				String tableName = this.getTrimmedColumn(keyResults, COLUMN_NAME_FOREIGN_TABLE_NAME);
				short keySequence = keyResults.getShort(COLUMN_NAME_KEY_SEQ_NAME);
				String columnName = this.getTrimmedColumn(keyResults, COLUMN_NAME_FOREIGN_COLUMN_NAME);
				ForeignKeyDefinition key = new ForeignKeyDefinition(tableName, columnName, keySequence, table);
				table.getForeignKeys().add(key);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.closeResultSet(keyResults);
		}
	}

	private synchronized void getUserDefinedTypes(DatabaseDefinition database)
	{
		ResultSet results = null;
		try
		{
			results = metaData.getUDTs(null, "", null, null);
			while(results.next())
			{
				String typeName = this.getTrimmedColumn(results, COLUMN_NAME_TYPE_NAME);
				String className = this.getTrimmedColumn(results, COLUMN_NAME_CLASS_NAME);
				int dataType = results.getInt(COLUMN_NAME_DATA_TYPE);
				short baseType = results.getShort(COLUMN_NAME_BASE_TYPE);
				UserDefinedTypeDefinition userDef = new UserDefinedTypeDefinition(typeName, className, dataType, baseType, database);
				database.getUserDefinedTypes().add(userDef);

			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.closeResultSet(results);
		}
	}
	
	private synchronized String getTrimmedColumn(ResultSet result, String columnName)
	{
		String value = null;
		try {
			value = result.getString(columnName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(value != null)
		{
			value = value.trim();
		}
		return value;
	}
	
	private synchronized void closeResultSet(ResultSet item)
	{
		if(item != null)
		{
			try {
				item.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private synchronized void closeStatement(Statement item)
	{
		if(item != null)
		{
			try {
				item.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
