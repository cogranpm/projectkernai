package com.glenwood.kernai.ui.viewmodel;

import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.ui.abstraction.BaseMasterDetailViewModel;

public class ImportDefinitionViewModel extends BaseMasterDetailViewModel<ImportDefinition, Project> {
	public ImportDefinitionViewModel(Project parent)
	{
		super(parent);
	}
}
