package com.glenwood.kernai.data.entity.helper;

public class CheckedNamedItemDataObject {
	
	private String id;
	private Boolean assigned;
	private String label;
	
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
	
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public CheckedNamedItemDataObject()
	{
		
		
	}
	
	
	public CheckedNamedItemDataObject(Boolean assigned, String id, String label)
	{
		this.id = id;
		this.assigned = assigned;
		this.label = label;
	}
	

}
