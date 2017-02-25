package com.glenwood.kernai.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Project extends BaseEntity {

	public static final String TYPE_NAME = "PROJECT";
	
	@JsonProperty
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		firePropertyChange("name", oldName, this.name);
	}

	public Project()
	{
		this.type = TYPE_NAME;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Project [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
