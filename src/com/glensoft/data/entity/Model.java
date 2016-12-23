package com.glensoft.data.entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glensoft.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Model extends BaseEntity {
	
	public static final String TYPE_NAME = "MODEL";
	
	
	@JsonProperty
	private String name;
	@JsonProperty
	private String modelType;
	
	/* foreign keys */
	@JsonProperty
	private String projectId;
	private Project project;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		firePropertyChange("name", oldName, this.name);
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		Project oldProject = this.project;
		this.project = project;
		firePropertyChange("project", oldProject, this.project);
	}
	
	public Model()
	{
		this.type = TYPE_NAME;
	}
	
	

}
