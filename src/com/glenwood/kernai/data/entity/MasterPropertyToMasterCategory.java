/* 
 * junction entity, recording the many to many relationship of master property to category
 */

package com.glenwood.kernai.data.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MasterPropertyToMasterCategory extends BaseEntity {
	
	public static final String TYPE_NAME = "MASTERPROPERTYTOMASTERCATEGORY";
	
	private String masterPropertyId;
	private String masterCategoryId;
	
	
	@JsonProperty
	public String getMasterPropertyId() {
		return masterPropertyId;
	}



	public void setMasterPropertyId(String masterPropertyId) {
		String oldMasterPropertyId = this.masterPropertyId;
		this.masterPropertyId = masterPropertyId;
		firePropertyChange("masterPropertyId", oldMasterPropertyId, this.masterPropertyId);
	}


	@JsonProperty
	public String getMasterCategoryId() {
		return masterCategoryId;
	}



	public void setMasterCategoryId(String masterCategoryId) {
		String oldMasterCategoryId = this.masterCategoryId;
		this.masterCategoryId = masterCategoryId;
		firePropertyChange("masterCategoryId", oldMasterCategoryId, this.masterCategoryId);
	}



	public MasterPropertyToMasterCategory()
	{
		this.type = TYPE_NAME;
	}

	@Override
	public String toString() {
		return String.format("MasterPropertyToMasterCategory[id=%s,MasterPropertyId=%s]", this.id, this.masterPropertyId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.masterPropertyId, this.masterCategoryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MasterPropertyToMasterCategory other = (MasterPropertyToMasterCategory) obj;
                
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!(this.id == other.id))
			return false;

		return true;
	}
}
