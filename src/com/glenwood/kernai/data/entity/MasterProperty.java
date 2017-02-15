package com.glenwood.kernai.data.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.entity.helper.MasterPropertyToMasterCategoryDataObject;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MasterProperty extends BaseEntity {
	public static final String TYPE_NAME = "MASTERPROPERTY";
	
	private String name;
	private String label;
	private String defaultValue;
	private String notes;
	private String propertyGroupId;
	private String propertyTypeId;

	private List<MasterPropertyToMasterCategoryDataObject> masterCategories;
	
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
	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		String oldLabel = this.label;
		this.label = label;
		this.firePropertyChange("label", oldLabel, this.label);
	}


	@JsonProperty
	public String getDefaultValue() {
		return defaultValue;
	}



	public void setDefaultValue(String defaultValue) {
		String oldDefaultValue = this.defaultValue;
		this.defaultValue = defaultValue;
		this.firePropertyChange("defaultValue", oldDefaultValue, this.defaultValue);
	}


	@JsonProperty
	public String getNotes() {
		return notes;
	}



	public void setNotes(String notes) {
		String oldNotes = this.notes;
		this.notes = notes;
		this.firePropertyChange("notes", oldNotes, this.notes);
	}


	@JsonProperty
	public String getPropertyGroupId() {
		return propertyGroupId;
	}



	public void setPropertyGroupId(String propertyGroupId) {
		String oldPropertyGroupId = this.propertyGroupId;
		this.propertyGroupId = propertyGroupId;
		this.firePropertyChange("propertyGroupId", oldPropertyGroupId, this.propertyGroupId);
	}


	@JsonProperty
	public String getPropertyTypeId() {
		return propertyTypeId;
	}



	public void setPropertyTypeId(String propertyTypeId) {
		String oldPropertyTypeId = this.propertyTypeId;
		this.propertyTypeId = propertyTypeId;
		this.firePropertyChange("propertyTypeId", oldPropertyTypeId, this.propertyTypeId);
	}

	public List<MasterPropertyToMasterCategoryDataObject> getMasterCategories()
	{
		return this.masterCategories;
	}
	
	public void assignMasterCategory(MasterPropertyToMasterCategoryDataObject entity)
	{
		this.masterCategories.add(entity);
	}
	
	public void removeMasterCategory(MasterPropertyToMasterCategoryDataObject entity)
	{
		this.masterCategories.remove(entity);
	}


	public MasterProperty()
	{
		this.type = TYPE_NAME;
		this.masterCategories = new ArrayList<MasterPropertyToMasterCategoryDataObject>();
	}
	

	@Override
	public String toString() {
		return String.format("MasterProperty[id=%s,Name=%s]", this.id, this.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.notes, this.label, this.propertyGroupId, this.propertyTypeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MasterProperty other = (MasterProperty) obj;
                
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!(this.id == other.id))
			return false;

		return true;
	}
}
