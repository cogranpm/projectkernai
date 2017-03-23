package com.glenwood.kernai.ui.abstraction;

import java.util.List;

import com.glenwood.kernai.data.modelimport.DatabaseDefinition;

public interface IImportWorkerClient {
	public void onConnectError();
	public void onConnect();
	public void setDatabases(List<DatabaseDefinition> list);
}
