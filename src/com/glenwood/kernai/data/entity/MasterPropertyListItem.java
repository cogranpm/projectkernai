package com.glenwood.kernai.data.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MasterPropertyListItem extends BaseEntity{

	public static final String TYPE_NAME = "MASTERPROPERTYLISTITEM";
	
	private String masterPropertyId;
	
	private String key;
	
	private String label;
	
	
	
	public String getMasterPropertyId() {
		return masterPropertyId;
	}

	public void setMasterPropertyId(String masterPropertyId) {
		this.masterPropertyId = masterPropertyId;
	}

	@JsonProperty
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@JsonProperty
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public MasterPropertyListItem()
	{
		this(null);
	}
	
	public MasterPropertyListItem(MasterProperty masterProperty)
	{
		this.type = TYPE_NAME;		
		if(masterProperty != null)
		{
			this.setMasterPropertyId(masterProperty.getId());
		}
	}
	
	
	@Override
	public String toString() {
		return String.format("MasterPropertyListItem[ id=%d,Key=%s,Label=%s", this.id, this.key, this.label);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.key, this.label, this.key);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MasterPropertyListItem other = (MasterPropertyListItem) obj;
                
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!(this.id == other.id))
			return false;

		return true;
	}
}
