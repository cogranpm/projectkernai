package com.glenwood.kernai.data.persistence;

import java.util.List;

import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.MasterCategory;
import com.glenwood.kernai.data.entity.MasterProperty;
import com.glenwood.kernai.data.entity.MasterPropertyToMasterCategory;
import com.glenwood.kernai.data.entity.PropertyGroup;
import com.glenwood.kernai.data.entity.PropertyType;
import com.glenwood.kernai.data.entity.helper.CheckedNamedItemDataObject;

public class MasterPropertyRepository extends BaseRepository<MasterProperty>  {
	
	private MasterPropertyToMasterCategoryRepository assignedCategoryRepository;
	private MasterCategoryRepository masterCategoryRepository;
	private PropertyTypeRepository propertyTypeRepository;
	private PropertyGroupRepository propertyGroupRepository;
	

	public MasterPropertyRepository(IPersistenceManager manager) {
		super(manager);
		//to do: change this to a registry of repositories to save recreating instances
		assignedCategoryRepository = new MasterPropertyToMasterCategoryRepository(manager);
		masterCategoryRepository = new MasterCategoryRepository(manager);
		propertyTypeRepository = new PropertyTypeRepository(manager);
		propertyGroupRepository = new PropertyGroupRepository(manager);
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
				CheckedNamedItemDataObject categoryItem = new CheckedNamedItemDataObject(masterCategoryIsAssigned, masterCategory.getId(), masterCategory.getName());
				masterProperty.assignMasterCategory(categoryItem);
			}
			
			/* load the dependant object instances */
			if(masterProperty.getPropertyGroupId() != null)
			{
				masterProperty.setPropertyGroup(this.propertyGroupRepository.get(masterProperty.getPropertyGroupId(), PropertyGroup.class));
			}
			
			if(masterProperty.getPropertyTypeId() != null)
			{
				masterProperty.setPropertyType(this.propertyTypeRepository.get(masterProperty.getPropertyTypeId(), PropertyType.class));
			}
		}
		return allItems;
	}

	
	@Override
	public void save(MasterProperty entity) {
		
		/* map object to foreign keys for embedded objects */
		if(entity.getPropertyGroup() != null)
		{
			entity.setPropertyGroupId(entity.getPropertyGroup().getId());
		}
		else
		{
			entity.setPropertyGroupId(null);
		}
		
		if (entity.getPropertyType() != null)
		{
			entity.setPropertyTypeId(entity.getPropertyType().getId());
		}
		else
		{
			entity.setPropertyTypeId(null);
		}
		
		super.save(entity);
		/* loop over the list of master category items and save junction entity */
		
		/* get list of junction items by masterpropertyid */
		List<MasterPropertyToMasterCategory> assignedCategories = this.getAssignedMasterCategories(entity.getId());
		
		/* is junction entity deleted? - if not checked and exists then delete */
		for(CheckedNamedItemDataObject item : entity.getMasterCategories())
		{
			if (item.getAssigned() == false)
			{
				/* if exists - then delete */
				if(this.getMasterCategoryIsAssigned(item.getId(), assignedCategories))
				{
					MasterPropertyToMasterCategory assignedItem = this.assignedCategoryRepository.get(entity.getId(), item.getId());
					this.assignedCategoryRepository.delete(assignedItem);
				}
			}
			else
			{
				MasterPropertyToMasterCategory assignedItem = this.assignedCategoryRepository.get(entity.getId(), item.getId());
				if (assignedItem == null)
				{
					MasterPropertyToMasterCategory newAssignedItem = new MasterPropertyToMasterCategory();
					newAssignedItem.setMasterCategoryId(item.getId());
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
