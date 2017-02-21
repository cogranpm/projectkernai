package com.glenwood.kernai.data.entity.helper;

public class CheckedNamedItemDataObject {
	
	private String id;
	private Boolean assigned;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getAssigned() {
		return assigned;
	}
	public void setAssigned(Boolean assigned) {
		this.assigned = assigned;
	}
	
	public CheckedNamedItemDataObject()
	{
		
		
	}
	
	
	public CheckedNamedItemDataObject(Boolean assigned, String id)
	{
		this.id = id;
		this.assigned = assigned;
		
	}
	

}
