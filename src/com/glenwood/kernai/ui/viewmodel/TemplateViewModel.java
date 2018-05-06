package com.glenwood.kernai.ui.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.Template;
import com.glenwood.kernai.ui.abstraction.BaseViewModel;

public class TemplateViewModel extends BaseViewModel<Template> {

	public TemplateViewModel()
	{
		super();
		this.engineLookup = new ArrayList<ListDetail>();
	}
	
	private List<ListDetail> engineLookup;

	public List<ListDetail> getEngineLookup() {
		return engineLookup;
	}

	public void setDataTypeLookup(List<ListDetail> engineLookup) {
		this.engineLookup = engineLookup;
	}
	
}
