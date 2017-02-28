package com.glenwood.kernai.ui.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.ui.abstraction.BaseMasterDetailViewModel;

public class AttributeViewModel extends BaseMasterDetailViewModel<Attribute, Entity>{

	public AttributeViewModel(Entity parent) {
		super(parent);
		this.dataTypeLookup = new ArrayList<ListDetail>();
	}
	
	private List<ListDetail> dataTypeLookup;

	public List<ListDetail> getDataTypeLookup() {
		return dataTypeLookup;
	}

	public void setDataTypeLookup(List<ListDetail> dataTypeLookup) {
		this.dataTypeLookup = dataTypeLookup;
	}
	
	

	

}
