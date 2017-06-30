package com.glenwood.kernai.ui.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.ImportTable;
import com.glenwood.kernai.data.modelimport.DatabaseDefinition;
import com.glenwood.kernai.data.modelimport.TableDefinition;
import com.glenwood.kernai.ui.abstraction.BaseMasterDetailViewModel;

public class ImportTableViewModel extends BaseMasterDetailViewModel<ImportTable, ImportDefinition> {
	
	private DatabaseDefinition selectedDatabase;
	private List<TableDefinition> tableSourceList;
	

	public DatabaseDefinition getSelectedDatabase(){
		return selectedDatabase;
	} 



	public void setSelectedDatabase(DatabaseDefinition selectedDatabase) {
		DatabaseDefinition oldValue = this.selectedDatabase;
		this.selectedDatabase= selectedDatabase;
		this.firePropertyChange("selectedDatabase", oldValue, this.selectedDatabase);
	}
	

	public List<TableDefinition> getTableSourceList() {
		return tableSourceList;
	}

	public void setTableSourceList(List<TableDefinition> tableSourceList) {
		List<TableDefinition> oldValue = this.tableSourceList;
		this.tableSourceList = tableSourceList;
		this.firePropertyChange("tableSourceList", oldValue, this.tableSourceList);
	}



	public ImportTableViewModel(ImportDefinition parent) {
		super(parent);
		this.tableSourceList = new ArrayList<TableDefinition>();
	}

}
