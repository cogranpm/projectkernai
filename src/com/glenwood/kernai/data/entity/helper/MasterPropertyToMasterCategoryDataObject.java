package com.glenwood.kernai.data.entity.helper;

public class MasterPropertyToMasterCategoryDataObject {
	
	private String masterCategoryId;
	private Boolean assigned;
	
	public String getMasterCategoryId() {
		return masterCategoryId;
	}
	public void setMasterCategoryId(String masterCategoryId) {
		this.masterCategoryId = masterCategoryId;
	}
	public Boolean getAssigned() {
		return assigned;
	}
	public void setAssigned(Boolean assigned) {
		this.assigned = assigned;
	}
	
	public MasterPropertyToMasterCategoryDataObject()
	{
		
		
	}
	
	
	public MasterPropertyToMasterCategoryDataObject(Boolean assigned, String masterCategoryId)
	{
		this.masterCategoryId = masterCategoryId;
		this.assigned = assigned;
		
	}
	

}
