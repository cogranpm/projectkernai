package com.glenwood.kernai.ui.viewmodel;

import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.ImportTable;
import com.glenwood.kernai.ui.abstraction.BaseMasterDetailViewModel;

public class ImportTableViewModel extends BaseMasterDetailViewModel<ImportTable, ImportDefinition> {
	
	private String selectedDatabaseName;
	
	

	public String getSelectedDatabaseName() {
		return selectedDatabaseName;
	}



	public void setSelectedDatabaseName(String selectedDatabaseName) {
		String oldValue = this.selectedDatabaseName;
		this.selectedDatabaseName = selectedDatabaseName;
		this.firePropertyChange("selectedDatabaseName", oldValue, this.selectedDatabaseName);
	}



	public ImportTableViewModel(ImportDefinition parent) {
		super(parent);
		
	}

}
