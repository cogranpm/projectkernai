package com.glenwood.kernai.data.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ListHeaderMapping extends BaseEntity {
	
	public static final String TYPE_NAME = "LISTHEADERMAPPING";
	
	private ListHeader fromHeader;
	private String fromHeaderId;
	private ListHeader toHeader;
	private String toHeaderId;
	
	
	@JsonIgnore
	public ListHeader getFromHeader() {
		return fromHeader;
	}



	public void setFromHeader(ListHeader fromHeader) {
		ListHeader oldValue = this.fromHeader;
		this.fromHeader = fromHeader;
		this.firePropertyChange("fromHeader", oldValue, this.fromHeader);
	}


	@JsonProperty
	public String getFromHeaderId() {
		return fromHeaderId;
	}



	public void setFromHeaderId(String fromHeaderId) {
		String oldValue = this.fromHeaderId;
		this.fromHeaderId = fromHeaderId;
		firePropertyChange("fromHeaderId", oldValue, this.fromHeaderId);
	}


	@JsonIgnore
	public ListHeader getToHeader() {
		return toHeader;
	}



	public void setToHeader(ListHeader toHeader) {
		ListHeader oldValue = this.toHeader;
		this.toHeader = toHeader;
		firePropertyChange("toHeader", oldValue, this.toHeader);
	}


	@JsonProperty
	public String getToHeaderId() {
		return toHeaderId;
	}



	public void setToHeaderId(String toHeaderId) {
		String oldValue = this.toHeaderId;
		this.toHeaderId = toHeaderId;
		this.firePropertyChange("toHeaderId", oldValue, this.toHeaderId);
	}



	public ListHeaderMapping()
	{
		super();
		this.type = TYPE_NAME;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.fromHeaderId, this.toHeaderId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListHeaderMapping other = (ListHeaderMapping) obj;
                
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!(this.id == other.id))
			return false;

		return true;
	}

}
