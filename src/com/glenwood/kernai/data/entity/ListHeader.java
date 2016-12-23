package com.glenwood.kernai.data.entity;

import com.glenwood.kernai.data.abstractions.BaseEntity;

public class ListHeader extends BaseEntity {
	
	public static final String TYPE_NAME = "LISTHEADER";
	
	private String name;
	
	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		this.firePropertyChange("Name", oldName, this.name);
	}



	public ListHeader()
	{
		this.type = TYPE_NAME;
	}
	

}
