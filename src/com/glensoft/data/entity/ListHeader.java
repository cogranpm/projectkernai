package com.glensoft.data.entity;

import com.glensoft.data.abstractions.BaseEntity;

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
