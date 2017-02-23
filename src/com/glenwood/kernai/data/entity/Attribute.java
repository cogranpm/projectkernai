package com.glenwood.kernai.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Attribute extends BaseEntity {
	


	public static final String TYPE_NAME = "ATTRIBUTE";
	
	public Attribute()
	{
		this(null, null, null, true);
	}
	
	public Attribute(String name, String dataType, Long length, Boolean allowNull)
	{
		//this.entity = entity;
		this.name = name;
		this.dataType = dataType;
		this.length = length;
		this.allowNull = allowNull;
		this.type = TYPE_NAME;
	}
	
	
	@JsonProperty
	private String name;
	@JsonProperty
	private String dataType;
	@JsonProperty
	private Long length;
	@JsonProperty
	private Boolean allowNull;
	
	/* foreign keys */
	@JsonProperty
	private String entityId;
	
	//private Entity entity;

	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		this.length = length;
	}
	public Boolean getAllowNull() {
		return allowNull;
	}
	public void setAllowNull(Boolean allowNull) {
		this.allowNull = allowNull;
	}
	
	/*
	public String getEntityId()
	{
		return this.entityId;
	}
	
	public void setEntityId(String entityId)
	{
		this.entityId = entityId;
	}
	*/
	
	/*
	public Entity getEntity()
	{
		return this.entity;
	}

	public void setEntity(Entity entity)
	{
		if (entity != null)
		{
			this.entity = entity;
			this.entityId = entity.getId();
		}
		else
		{
			this.entity = null;
			this.entityId = null;
		}
	}
	*/


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


