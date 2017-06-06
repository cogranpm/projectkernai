package com.glenwood.kernai.ui.abstraction;

import java.util.List;

import com.glenwood.kernai.data.modelimport.DatabaseDefinition;
import com.glenwood.kernai.data.modelimport.TableDefinition;

public interface IDataTableSelectorClient {
	public void setDatabases(List<DatabaseDefinition> list);
	public void setTables(List<TableDefinition> list);
}
