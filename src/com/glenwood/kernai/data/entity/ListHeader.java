package com.glenwood.kernai.data.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ListHeader extends BaseEntity {
	
	public static final String TYPE_NAME = "LISTHEADER";
	
	private String name;
	private Boolean isSystem;
	
	
	@JsonProperty
	public String getName() {
		return name;
	}



	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		this.firePropertyChange("name", oldName, this.name);
	}
	
	
	

	@JsonProperty
	public Boolean getIsSystem() {
		return isSystem;
	}



	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}



	public ListHeader()
	{
		super();
		this.type = TYPE_NAME;
	}
	
	public ListHeader(String name, Boolean isSystem)
	{
		this();
		this.name = name;
		this.isSystem = isSystem;
	}

	@Override
	public String toString() {
		return String.format("ListHeader[id=%s,Name=%s]", this.id, this.name);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListHeader other = (ListHeader) obj;
                
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!(this.id == other.id))
			return false;

		return true;
	}

}
