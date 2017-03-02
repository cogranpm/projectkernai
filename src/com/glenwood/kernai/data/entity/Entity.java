package com.glenwood.kernai.data.entity;

import java.util.Objects;

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

	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id:");
		sb.append(this.id);
		sb.append("\n");
		sb.append("name:");
		sb.append(this.name);
		sb.append("\n");
		sb.append("modelId:");
		sb.append(this.modelId);
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.type, this.modelId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
                
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!(this.id == other.id))
			return false;

		return true;
	}
}
