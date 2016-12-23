package com.glensoft.ui.presenter;

import java.util.ArrayList;
import java.util.List;

import com.glensoft.data.entity.Project;
import com.glensoft.ui.abstraction.IProjectView;
import com.glensoft.ui.viewmodel.ProjectViewModel;

public class ProjectViewPresenter {
	
	IProjectView view;
	ProjectViewModel model;
	
	public ProjectViewModel getModel()
	{
		return this.model;
	}
	
	private ProjectViewPresenter(){};
	
	public ProjectViewPresenter(IProjectView view, ProjectViewModel model)
	{
		this.view = view;
		this.model = model;
	}
	
	public void loadProjects()
	{
		this.model.setProjects(new ArrayList<Project>());
		for (int x = 0; x < 10; x++)
		{
			Project testProject = new Project();
			testProject.setName(String.format("Project %d", x));
			testProject.setId(String.format("%d", x));
			this.model.getProjects().add(testProject);
		}
		view.renderProjectList();
	}

}
