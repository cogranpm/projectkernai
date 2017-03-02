package com.glenwood.kernai.ui.viewmodel;

import java.util.List;

import com.glenwood.kernai.data.entity.Association;
import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.Model;
import com.glenwood.kernai.ui.abstraction.BaseMasterDetailViewModel;

public class AssociationViewModel extends BaseMasterDetailViewModel<Association, Model> {

	private List<Entity> entityLookup;
	private List<ListDetail> associationTypeLoookup;
	//private List<Entity> ownedEntityLookup;
	
	public AssociationViewModel(Model parent) {
		super(parent);
		
	}

	public List<Entity> getEntityLookup() {
		return entityLookup;
	}

	public void setEntityLookup(List<Entity> entityLookup) {
		List<Entity> oldValue = this.entityLookup;
		this.entityLookup = entityLookup;
		firePropertyChange("entityLookup", oldValue, this.entityLookup);
	}
	
	
/*
	public List<Entity> getOwnedEntityLookup() {
		return ownedEntityLookup;
	}

	public void setOwnedEntityLookup(List<Entity> ownedEntityLookup) {
		this.ownedEntityLookup = ownedEntityLookup;
	}
*/

	public List<ListDetail> getAssociationTypeLoookup() {
		return associationTypeLoookup;
	}

	public void setAssociationTypeLoookup(List<ListDetail> associationTypeLoookup) {
		this.associationTypeLoookup = associationTypeLoookup;
	}
	
	

	
}
