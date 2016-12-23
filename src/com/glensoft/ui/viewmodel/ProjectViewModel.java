package com.glensoft.ui.viewmodel;

import java.util.List;

import com.glensoft.data.entity.Project;

public class ProjectViewModel {
	private List<Project> projects;
	private Project currentProject;
	
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public Project getCurrentProject() {
		return currentProject;
	}
	public void setCurrentProject(Project currentProject) {
		this.currentProject = currentProject;
	}
	
	

}
