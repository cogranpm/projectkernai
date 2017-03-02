package com.glenwood.kernai.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Association extends BaseEntity {

	public static final String TYPE_NAME = "ASSOCIATION";
	
	private String name;
	
	private String modelId;
	
	private String ownerEntityId;
	
	private String ownedEntityId;
	
	private String associationType;
	
	private Entity ownerEntity;
	
	private Entity ownedEntity;
	
	private ListDetail associationTypeLookup;
	
	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		firePropertyChange("name", oldValue, this.name);
	}
	
	
	

	public String getModelId() {
		return modelId;
	}

	@JsonProperty
	public void setModelId(String modelId) {
		this.modelId = modelId;
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
	
	@JsonIgnore
	public ListDetail getAssociationTypeLookup() {
		return associationTypeLookup;
	}

	public void setAssociationTypeLookup(ListDetail associationTypeLookup) {
		ListDetail oldValue = this.associationTypeLookup;
		this.associationTypeLookup = associationTypeLookup;
		this.firePropertyChange("associationTypeLookup", oldValue, this.associationTypeLookup);
	}

	
	
	@JsonIgnore
	public Entity getOwnerEntity() {
		return ownerEntity;
	}

	public void setOwnerEntity(Entity ownerEntity) {
		Entity oldValue = this.ownerEntity;
		this.ownerEntity = ownerEntity;
		this.firePropertyChange("ownerEntity", oldValue, this.ownerEntity);
	}

	@JsonIgnore
	public Entity getOwnedEntity() {
		return ownedEntity;
	}

	public void setOwnedEntity(Entity ownedEntity) {
		Entity oldValue = this.ownedEntity;
		this.ownedEntity = ownedEntity;
		firePropertyChange("ownedEntity", oldValue, this.ownedEntity);
	}
	
	
	

	public Association()
	{
		this.type = Association.TYPE_NAME;
	}
	
	public Association(Model model)
	{
		this();
		this.setModelId(model.getId());
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id:");
		sb.append(this.id);
		sb.append("\n");
		sb.append("name:");
		sb.append(this.name);
		sb.append("\n");
		sb.append("type:");
		sb.append(this.associationType);
		sb.append("\n");
		sb.append("Owner Entity:");
		sb.append(this.ownerEntityId);
		sb.append("\n");
		sb.append("Owned Entity:");
		sb.append(this.ownedEntityId);
		sb.append("\n");

		return sb.toString();
	}
}
