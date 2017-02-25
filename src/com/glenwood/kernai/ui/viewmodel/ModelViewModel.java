package com.glenwood.kernai.ui.viewmodel;

import com.glenwood.kernai.data.entity.Model;
import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.ui.abstraction.BaseMasterDetailViewModel;

public class ModelViewModel extends BaseMasterDetailViewModel<Model, Project> {

	public ModelViewModel(Project parent) {
		super(parent);
	}


}
