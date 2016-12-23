package com.glensoft.ui.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.glensoft.data.entity.Attribute;

public class AttributeViewModel {
	
	private List<Attribute> attributes;
	private Attribute currentAttribute;
	
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	public Attribute getCurrentAttribute() {
		return currentAttribute;
	}
	public void setCurrentAttribute(Attribute currentAttribute) {
		this.currentAttribute = currentAttribute;
	}
	
	public AttributeViewModel()
	{
		this.attributes = new ArrayList<Attribute>();
	}
	

}
