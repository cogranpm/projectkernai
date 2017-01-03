package com.glenwood.kernai.data.entity;

import com.glenwood.kernai.data.abstractions.BaseEntity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class MasterCategory extends BaseEntity {

	public static final String TYPE_NAME = "MASTERCATEGORY";

	public MasterCategory()
	{
	       this.type = TYPE_NAME;
	}

	private String name;
	
	@JsonProperty
	public final String getName() {
		return this.name;
	}
	
	public final void setName(String value) {
		String oldName = this.name;
		this.name= value;
		firePropertyChange("name", oldName, this.name);
	}
	

	@Override
	public String toString() {
		return String.format("MasterCategory[ id=%d,Name=%s", this.id, this.name);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.name);
		/*
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
		*/
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MasterCategory other = (MasterCategory) obj;
                
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!(this.id == other.id))
			return false;

		return true;
	}

	
}
