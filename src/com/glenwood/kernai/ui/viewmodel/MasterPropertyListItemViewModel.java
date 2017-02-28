package com.glenwood.kernai.ui.viewmodel;

import com.glenwood.kernai.data.entity.MasterProperty;
import com.glenwood.kernai.data.entity.MasterPropertyListItem;
import com.glenwood.kernai.ui.abstraction.BaseMasterDetailViewModel;

public class MasterPropertyListItemViewModel extends BaseMasterDetailViewModel<MasterPropertyListItem, MasterProperty> {

	public MasterPropertyListItemViewModel(MasterProperty parent) {
		super(parent);

	}

}
