package com.glenwood.kernai.ui.abstraction;

import java.util.List;

import com.glenwood.kernai.data.modelimport.DatabaseDefinition;
import com.glenwood.kernai.data.modelimport.TableDefinition;

public interface IImportWorkerClient {
	public void onConnectError(String message);
	public void onConnect();
	public void setDatabases(List<DatabaseDefinition> list);
	public void setTables(List<TableDefinition> list);
}
