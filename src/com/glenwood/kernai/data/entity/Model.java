package com.glenwood.kernai.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Model extends BaseEntity {
	
	public static final String TYPE_NAME = "MODEL";
	
	
	private String name;
	private String modelType;
	
	/* foreign keys */
	private String projectId;
	
	
	//private Project project;

	@JsonProperty
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		firePropertyChange("name", oldName, this.name);
	}
	
	@JsonProperty
	public String getModelType() {
		return modelType;
	}
	
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	
	@JsonProperty
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	

	
	public Model()
	{
		this.type = TYPE_NAME;
	}
	
	public Model(Project parent)
	{
		this();
		this.projectId = parent.getId();
		
	}
	
	

}
