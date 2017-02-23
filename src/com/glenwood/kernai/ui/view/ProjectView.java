package com.glenwood.kernai.ui.view;

import org.eclipse.swt.widgets.Composite;

import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.ui.abstraction.BaseEntityView;
import com.glenwood.kernai.ui.presenter.ProjectViewPresenter;
import com.glenwood.kernai.ui.viewmodel.ProjectViewModel;

public class ProjectView extends BaseEntityView<Project>{
	
	public ProjectView(Composite parent, int style) {
		super(parent, style);
	}

	
	@Override
	protected void setupModelAndPresenter() {
		super.setupModelAndPresenter();
		this.model = new ProjectViewModel();
		this.presenter = new ProjectViewPresenter(this, this.model);
	}

	@Override
	protected void setupListColumns() {
		super.setupListColumns();
	}

	
	@Override
	protected void setupEditingContainer() {
		super.setupEditingContainer();
	}


	
	
}
