package com.glenwood.kernai.ui.abstraction;

import com.glenwood.kernai.data.abstractions.BaseEntity;

public interface IEntityMasterDetailListModalView <T extends BaseEntity, P extends BaseEntity> {

	public void refreshView();
	public void showAddEdit(Boolean adding);
	public void setToolbarEnabled(Boolean enable);
}
