package com.glenwood.kernai.data.abstractions;

import java.util.List;

import com.glenwood.kernai.data.modelimport.ColumnDefinition;

public interface IImportEngine {
	public void init(IConnection connection);
	public List<String> getDatabases();
	public List<String> getTables(String databaseName);
	public List<ColumnDefinition> getColumns(String databaseName, String tableName);

}
