package com.glenwood.kernai.ui.viewmodel;

import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.entity.Model;
import com.glenwood.kernai.ui.abstraction.BaseMasterDetailViewModel;

public class EntityViewModel extends BaseMasterDetailViewModel<Entity, Model> {

	public EntityViewModel(Model parent) {
		super(parent);
	}

}
