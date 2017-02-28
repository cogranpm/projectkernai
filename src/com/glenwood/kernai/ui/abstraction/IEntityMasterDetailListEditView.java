package com.glenwood.kernai.ui.abstraction;

import com.glenwood.kernai.data.abstractions.BaseEntity;

public interface IEntityMasterDetailListEditView <T extends BaseEntity, P extends BaseEntity> {
	public void delete();
	public void add();
	public void save();
	public void refreshView();
	public void afterAdd();
	public void afterSelection();
}
