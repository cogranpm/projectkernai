package com.glenwood.kernai.data.persistence;

import java.util.List;

import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.MasterCategory;
import com.glenwood.kernai.data.entity.MasterProperty;
import com.glenwood.kernai.data.entity.MasterPropertyToMasterCategory;
import com.glenwood.kernai.data.entity.helper.MasterPropertyToMasterCategoryDataObject;

public class MasterPropertyRepository extends BaseRepository<MasterProperty> {
	
	MasterPropertyToMasterCategoryRepository assignedCategoryRepository;
	MasterCategoryRepository masterCategoryRepository;

	public MasterPropertyRepository(IPersistenceManager manager) {
		super(manager);
		//to do: change this to a registry of repositories to save recreating instances
		assignedCategoryRepository = new MasterPropertyToMasterCategoryRepository(manager);
		masterCategoryRepository = new MasterCategoryRepository(manager);
	}
	
	@Override
	public List<MasterProperty> getAll(String type, Class<MasterProperty> aClass) {

		List<MasterProperty> allItems = super.getAll(type, aClass);
		List<MasterCategory> allCategories = this.getAllMasterCategories();
		for(MasterProperty masterProperty : allItems)
		{
			//query all the master categories
			Boolean masterCategoryIsAssigned = false;
			List<MasterPropertyToMasterCategory> assignedCategories = this.getAssignedMasterCategories(masterProperty.getId());
			for(MasterCategory masterCategory : allCategories)
			{
				masterCategoryIsAssigned = this.getMasterCategoryIsAssigned(masterCategory.getId(), assignedCategories);
				MasterPropertyToMasterCategoryDataObject categoryItem = new MasterPropertyToMasterCategoryDataObject(masterCategoryIsAssigned, masterCategory.getId());
				masterProperty.assignMasterCategory(categoryItem);
			}
		}
		return allItems;
	}

	
	@Override
	public void save(MasterProperty entity) {
		super.save(entity);
		/* loop over the list of master category items and save junction entity */
		
		/* get list of junction items by masterpropertyid */
		List<MasterPropertyToMasterCategory> assignedCategories = this.getAssignedMasterCategories(entity.getId());
		
		/* is junction entity deleted? - if not checked and exists then delete */
		for(MasterPropertyToMasterCategoryDataObject item : entity.getMasterCategories())
		{
			if (item.getAssigned() == false)
			{
				/* if exists - then delete */
				if(this.getMasterCategoryIsAssigned(item.getMasterCategoryId(), assignedCategories))
				{
					MasterPropertyToMasterCategory assignedItem = this.assignedCategoryRepository.get(entity.getId(), item.getMasterCategoryId());
					this.assignedCategoryRepository.delete(assignedItem);
				}
			}
			else
			{
				MasterPropertyToMasterCategory assignedItem = this.assignedCategoryRepository.get(entity.getId(), item.getMasterCategoryId());
				if (assignedItem == null)
				{
					MasterPropertyToMasterCategory newAssignedItem = new MasterPropertyToMasterCategory();
					newAssignedItem.setMasterCategoryId(item.getMasterCategoryId());
					newAssignedItem.setMasterPropertyId(entity.getId());
					this.assignedCategoryRepository.save(newAssignedItem);
				}
			}
		}
	}
	
	private List<MasterPropertyToMasterCategory> getAssignedMasterCategories(String masterPropertyId)
	{
		return this.assignedCategoryRepository.getAllByMasterProperty(masterPropertyId);
	}
	
	private List<MasterCategory> getAllMasterCategories()
	{
		return this.masterCategoryRepository.getAll(MasterCategory.TYPE_NAME, MasterCategory.class);
	}
	
	private Boolean getMasterCategoryIsAssigned(String masterCategoryId, List<MasterPropertyToMasterCategory> assignedMasterCategories)
	{
		for(MasterPropertyToMasterCategory assignedItem : assignedMasterCategories)
		{
			if (assignedItem.getMasterCategoryId().equalsIgnoreCase(masterCategoryId))
			{
				return true;
			}
		}
		return false;
	}
}
