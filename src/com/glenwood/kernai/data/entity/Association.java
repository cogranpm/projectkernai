package com.glenwood.kernai.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Association extends BaseEntity {

	public static final String TYPE_NAME = "ASSOCIATION";
	
	private String name;
	
	private String ownerEntityId;
	
	private String ownedEntityId;
	
	private String associationType;
	
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
	public String getOwnerEntityId() {
		return ownerEntityId;
	}

	public void setOwnerEntityId(String ownerEntityId) {
		String oldValue = this.ownedEntityId;
		this.ownerEntityId = ownerEntityId;
		firePropertyChange("ownerEntityId", oldValue, this.ownerEntityId);
	}

	@JsonProperty
	public String getOwnedEntityId() {
		return ownedEntityId;
	}

	public void setOwnedEntityId(String ownedEntityId) {
		String oldValue = this.ownedEntityId;
		this.ownedEntityId = ownedEntityId;
		firePropertyChange("ownedEntityId", oldValue, this.ownedEntityId);
	}

	@JsonProperty
	public String getAssociationType() {
		return associationType;
	}

	public void setAssociationType(String associationType) {
		String oldValue = this.associationType;
		this.associationType = associationType;
		firePropertyChange("associationType", oldValue, this.associationType);
	}
	
	
	public Association()
	{
		super();
		this.type = Association.TYPE_NAME;
	}
	
}
