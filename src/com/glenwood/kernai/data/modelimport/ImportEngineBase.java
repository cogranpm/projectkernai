package com.glenwood.kernai.data.modelimport;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.data.abstractions.IConnection;
import com.glenwood.kernai.data.abstractions.IImportEngine;

public class ImportEngineBase implements IImportEngine{
	
	private IConnection connection;
	private DatabaseMetaData metaData;
	
	private static final String[] DB_TABLE_TYPES = { "TABLE" };
	private static final String[] DB_VIEW_TYPES = { "VIEW" };
	private static final String[] DB_MIXED_TYPES = { "TABLE", "VIEW" };
	private static final String COLUMN_NAME_TABLE_NAME = "TABLE_NAME";
	private static final String COLUMN_NAME_COLUMN_NAME = "COLUMN_NAME";
	private static final String COLUMN_NAME_DATA_TYPE = "DATA_TYPE";
 	private static final String COLUMN_NAME_TYPE_NAME = "TYPE_NAME";
 	private static final String COLUMN_NAME_COLUMN_SIZE = "COLUMN_SIZE";
 	private static final String COLUMN_NAME_NULLABLE = "NULLABLE";
 	private static final String COLUMN_NAME_ORDINAL_POSITION = "ORDINAL_POSITION";
 	private static final String COLUMN_NAME_REMARKS = "REMARKS";
 	private static final String COLUMN_NAME_DEFAULT = "COLUMN_DEF";
 	private static final String COLUMN_NAME_IS_NULLABLE = "IS_NULLABLE";
 	private static final String COLUMN_NAME_IS_AUTOINCREMENT = "IS_AUTOINCREMENT";
 	private static final String COLUMN_NAME_IS_GENERATED = "IS_GENERATEDCOLUMN";

	
	@Override
	public void init(IConnection connection)
	{
		this.connection = connection;
		try {
			this.metaData = connection.getConnection().getMetaData();
			int minorVersion = metaData.getDatabaseMinorVersion();
			System.out.println(String.format("MinorVersion %d", minorVersion));
			System.out.println(String.format("Product Name %s", metaData.getDatabaseProductName()));
			System.out.println(String.format("Product Version %s", metaData.getDatabaseProductVersion()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public List<String> getDatabases()
	{
		List<String> list = new ArrayList<String>();
		ResultSet catalogs = null;
		try {

			catalogs = this.metaData.getCatalogs();
			while(catalogs.next())
			{
				String catalog = catalogs.getString(1);
				if(catalog != null)
				{
					list.add(catalog);
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
	public List<String> getTables(String databaseName) {
		List<String> list = new ArrayList<String>();
		ResultSet results = null;
		try {

			results = metaData.getTables(databaseName, null, null, DB_TABLE_TYPES);
			while(results.next())
			{
				String name = this.getTrimmedColumn(results, COLUMN_NAME_TABLE_NAME);
				if(name != null)
				{
					list.add(name);
				}
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			this.closeResultSet(results);
		}
		return list;
	}
	
	@Override
	public List<ColumnDefinition> getColumns(String databaseName, String tableName) {
		List<ColumnDefinition> list = new ArrayList<ColumnDefinition>();
		ResultSet results = null;
		try {

			results = metaData.getColumns(databaseName, null, tableName.toUpperCase(), null );
			while(results.next())
			{
				String name = this.getTrimmedColumn(results, COLUMN_NAME_COLUMN_NAME);
				if(name != null && name != "")
				{
					int dataType = results.getInt(COLUMN_NAME_DATA_TYPE);
					String dbTypeName = this.getTrimmedColumn(results, COLUMN_NAME_TYPE_NAME);
					int size = results.getInt(COLUMN_NAME_COLUMN_SIZE);
					int nullable = results.getInt(COLUMN_NAME_NULLABLE);
					String remarks = this.getTrimmedColumn(results, COLUMN_NAME_REMARKS);
					String defaultValue = this.getTrimmedColumn(results, COLUMN_NAME_DEFAULT);
					String isNullable = this.getTrimmedColumn(results, COLUMN_NAME_IS_NULLABLE);
					String isAutoIncrement = this.getTrimmedColumn(results, COLUMN_NAME_IS_AUTOINCREMENT);
					//String isGenerated = this.getTrimmedColumn(results, COLUMN_NAME_IS_GENERATED);
					String isGenerated = "poo";
					ColumnDefinition column = new ColumnDefinition(name, 
							dataType, 
							dbTypeName, 
							size, 
							nullable, 
							remarks, 
							defaultValue, 
							isNullable, 
							isAutoIncrement, 
							isGenerated);
					list.add(column);
				}
				
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			this.closeResultSet(results);
		}
		return list;
	}
	
	
	private String getTrimmedColumn(ResultSet result, String columnName)
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
	
	private void closeResultSet(ResultSet item)
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
