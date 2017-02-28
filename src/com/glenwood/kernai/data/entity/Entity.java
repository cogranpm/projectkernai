package com.glenwood.kernai.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;


@JsonIgnoreProperties(ignoreUnknown=true)
public class Entity extends BaseEntity {
	
	public static final String TYPE_NAME = "ENTITY";
	
	private String name;
	private String modelId;
	
	@JsonProperty
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		String oldValue = this.name;
		this.name = name;
		firePropertyChange("name", oldValue, this.name);
	}
	
	
	
	@JsonProperty
	public String getModelId() {
		return modelId;
	}

	
	public void setModelId(String modelId) {
		String oldValue = this.modelId;
		this.modelId = modelId;
		firePropertyChange("modelId", oldValue, this.modelId);
	}

	public Entity()
	{
		this.type = TYPE_NAME;
	}
	
	
	public Entity(Model parent)
	{
		this();
		this.modelId = parent.getId();
	}

}
