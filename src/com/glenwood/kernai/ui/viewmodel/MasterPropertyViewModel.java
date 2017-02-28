package com.glenwood.kernai.ui.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.data.entity.MasterProperty;
import com.glenwood.kernai.data.entity.PropertyGroup;
import com.glenwood.kernai.data.entity.PropertyType;
import com.glenwood.kernai.ui.abstraction.BaseViewModel;

public class MasterPropertyViewModel extends BaseViewModel<MasterProperty> {
	
	private List<PropertyType> propertyTypeLookup;
	private List<PropertyGroup> propertyGroupLookup;
	
	

	public List<PropertyType> getPropertyTypeLookup() {
		return propertyTypeLookup;
	}



	public List<PropertyGroup> getPropertyGroupLookup() {
		return propertyGroupLookup;
	}



	public void setPropertyTypeLookup(List<PropertyType> propertyTypeLookup) {
		this.propertyTypeLookup = propertyTypeLookup;
	}



	public void setPropertyGroupLookup(List<PropertyGroup> propertyGroupLookup) {
		this.propertyGroupLookup = propertyGroupLookup;
	}

	public PropertyGroup findPropertyGroupById(String id)
	{
		for(PropertyGroup item : this.propertyGroupLookup)
		{
			if(id.equalsIgnoreCase(item.getId()))
			{
				return item;
			}
		}
		return null;
	}
	
	public PropertyType findPropertyTypeById(String id)
	{
		for(PropertyType item : this.propertyTypeLookup)
		{
			if(id.equalsIgnoreCase(item.getId()))
			{
				return item;
			}
		}
		return null;
	}

	public MasterPropertyViewModel()
	{
		super();
		this.propertyGroupLookup = new ArrayList<PropertyGroup>();
		this.propertyTypeLookup = new ArrayList<PropertyType>();
	}

}
