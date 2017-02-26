package com.glenwood.kernai.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Attribute extends BaseEntity {

	public static final String TYPE_NAME = "ATTRIBUTE";
	
	public Attribute()
	{
		this.type = TYPE_NAME;
	}
	
	public Attribute(Entity parent)
	{
		this();
		this.entityId = parent.getId();
	}
	
	/*
	public Attribute(String name, String dataType, Long length, Boolean allowNull)
	{
		//this.entity = entity;
		this.name = name;
		this.dataType = dataType;
		this.length = length;
		this.allowNull = allowNull;
		this.type = TYPE_NAME;
	}
	*/
	
	private String name;

	private String dataType;

	private Long length;

	private Boolean allowNull;
	
	private String entityId;
	

	
	@JsonProperty
	public String getName() {
		return name;
	}
	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		firePropertyChange("name", oldValue, this.name);
	}
	
	@JsonProperty
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		String oldValue = this.dataType;
		this.dataType = dataType;
		firePropertyChange("dataType", oldValue, this.dataType);
	}
	
	@JsonProperty
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		Long oldValue = this.length;
		this.length = length;
		firePropertyChange("length", oldValue, this.length);
	}
	
	@JsonProperty
	public Boolean getAllowNull() {
		return allowNull;
	}
	public void setAllowNull(Boolean allowNull) {
		Boolean oldValue = this.allowNull;
		this.allowNull = allowNull;
		firePropertyChange("allowNull", oldValue, this.allowNull);
	}
	
	
	private ListDetail dataTypeLookup;
	
	@JsonIgnore
	public ListDetail getDataTypeLookup() {
		return dataTypeLookup;
	}

	public void setDataTypeLookup(ListDetail dataTypeLookup) {
		ListDetail oldValue = this.dataTypeLookup;
		this.dataTypeLookup = dataTypeLookup;
		firePropertyChange("dataTypeLookup", oldValue, this.dataTypeLookup);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Attribute [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", dataType=");
		builder.append(dataType);
		builder.append(", length=");
		builder.append(length);
		builder.append(", allowNull=");
		builder.append(allowNull);
		builder.append(", type=");
		builder.append(type);
		builder.append(", Entity=");
		builder.append(entityId);
		builder.append("]");
		return builder.toString();
	}


}


