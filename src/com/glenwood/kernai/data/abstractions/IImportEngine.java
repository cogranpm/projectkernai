package com.glenwood.kernai.data.abstractions;

import java.util.List;

import com.glenwood.kernai.data.modelimport.ColumnDefinition;
import com.glenwood.kernai.data.modelimport.DatabaseDefinition;
import com.glenwood.kernai.data.modelimport.TableDefinition;

public interface IImportEngine {
	public void init(IConnection connection);
	public List<DatabaseDefinition> getDatabases();
	public List<TableDefinition> getTables(DatabaseDefinition database, Boolean getTables, Boolean getViews);
	public List<ColumnDefinition> getColumns(DatabaseDefinition database, TableDefinition table);


}
