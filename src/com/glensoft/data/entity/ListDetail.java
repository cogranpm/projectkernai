package com.glensoft.data.entity;

import com.glensoft.data.abstractions.BaseEntity;

public class ListDetail extends BaseEntity {
	
	public static final String TYPE_NAME = "LISTDETAIL";

	private String listHeaderId;
	
	private String key;
	
	private String label;

	public String getListHeaderId() {
		return listHeaderId;
	}

	public void setListHeaderId(String listHeaderId) {
		this.listHeaderId = listHeaderId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public ListDetail()
	{
		this.type = TYPE_NAME;
	}
}
