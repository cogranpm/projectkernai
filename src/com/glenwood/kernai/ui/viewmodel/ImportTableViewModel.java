package com.glenwood.kernai.ui.viewmodel;

import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.ImportTable;
import com.glenwood.kernai.ui.abstraction.BaseMasterDetailViewModel;

public class ImportTableViewModel extends BaseMasterDetailViewModel<ImportTable, ImportDefinition> {

	public ImportTableViewModel(ImportDefinition parent) {
		super(parent);
		
	}

}
