package com.glenwood.kernai.data.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ListDetail extends BaseEntity {
	
	public static final String TYPE_NAME = "LISTDETAIL";

	private String listHeaderId;
	
	private String key;
	
	private String label;

	@JsonProperty
	public String getListHeaderId() {
		return listHeaderId;
	}

	public void setListHeaderId(String listHeaderId) {
		this.listHeaderId = listHeaderId;
	}

	@JsonProperty
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		String oldValue = this.key;
		this.key = key;
		firePropertyChange("key", oldValue, this.key);
	}

	@JsonProperty
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		String oldValue = this.label;
		this.label = label;
		this.firePropertyChange("label", oldValue, this.label);
	}
	
	public ListDetail()
	{
		this(null);
	}
	
	public ListDetail(ListHeader listHeader)
	{
		this.type = TYPE_NAME;		
		if(listHeader != null)
		{
			this.setListHeaderId(listHeader.getId());
		}
	}
	
	
	@Override
	public String toString() {
		return String.format("ListDetail[ id=%d,Key=%s,Label=%s", this.id, this.key, this.label);
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
		ListDetail other = (ListDetail) obj;
                
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!(this.id == other.id))
			return false;

		return true;
	}

}
