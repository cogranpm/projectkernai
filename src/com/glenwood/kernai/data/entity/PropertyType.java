package com.glenwood.kernai.data.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PropertyType extends BaseEntity {

	public static final String TYPE_NAME = "PROPERTYTYPE";
	public static final String LIST_ITEM_NAME = "List";
	
	private String name;
	
	
	@JsonProperty
	public String getName() {
		return name;
	}



	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		this.firePropertyChange("name", oldName, this.name);
	}

	
	private String notes;
	

	public String getNotes() {
		return notes;
	}



	public void setNotes(String notes) {
		String oldNotes = this.notes;
		this.notes = notes;
		this.firePropertyChange("notes", oldNotes, this.notes);
	}



	public PropertyType()
	{
		this.type = TYPE_NAME;
	}
	

	@Override
	public String toString() {
		return String.format("PropertyType[id=%s,Name=%s,Notes=%s]", this.id, this.name, this.notes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.notes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyType other = (PropertyType) obj;
                
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!(this.id == other.id))
			return false;

		return true;
	}

}
