package com.glenwood.kernai.ui.abstraction;

import com.glenwood.kernai.data.abstractions.BaseEntity;

public interface IEntityMasterDetailView <T extends BaseEntity, P extends BaseEntity> {
	/*
	public void delete();
	public void add();
	public void save();
	*/
	public void refreshView();
	public void showAddEdit(Boolean adding);
	public void setToolbarEnabled(Boolean enable);
}
