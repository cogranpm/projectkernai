package com.glenwood.kernai.ui.viewmodel;

import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.ImportTable;
import com.glenwood.kernai.data.modelimport.DatabaseDefinition;
import com.glenwood.kernai.ui.abstraction.BaseMasterDetailViewModel;

public class ImportTableViewModel extends BaseMasterDetailViewModel<ImportTable, ImportDefinition> {
	
	private DatabaseDefinition selectedDatabase;
	
	

	public DatabaseDefinition getSelectedDatabase(){
		return selectedDatabase;
	} 



	public void setSelectedDatabase(DatabaseDefinition selectedDatabase) {
		DatabaseDefinition oldValue = this.selectedDatabase;
		this.selectedDatabase= selectedDatabase;
		this.firePropertyChange("selectedDatabase", oldValue, this.selectedDatabase);
	}



	public ImportTableViewModel(ImportDefinition parent) {
		super(parent);
		
	}

}
