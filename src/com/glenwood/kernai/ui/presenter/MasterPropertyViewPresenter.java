package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.MasterProperty;
import com.glenwood.kernai.data.entity.PropertyGroup;
import com.glenwood.kernai.data.entity.PropertyType;
import com.glenwood.kernai.data.persistence.MasterPropertyRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.data.persistence.PropertyGroupRepository;
import com.glenwood.kernai.data.persistence.PropertyTypeRepository;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityPresenter;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.abstraction.IViewModel;
import com.glenwood.kernai.ui.viewmodel.MasterPropertyViewModel;

public class MasterPropertyViewPresenter extends BaseEntityPresenter<MasterProperty> {
	
	IEntityRepository<PropertyType> propertyTypeRepository;
	IEntityRepository<PropertyGroup> propertyGroupRepository;

	public MasterPropertyViewPresenter(IEntityView view, IViewModel<MasterProperty> model) {
		super(view, model, MasterProperty.class, MasterProperty.TYPE_NAME);
		IPersistenceManager manager = PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType());
		this.repository = new MasterPropertyRepository(manager);
		this.propertyGroupRepository = new PropertyGroupRepository(manager);
		this.propertyTypeRepository = new PropertyTypeRepository(manager);
		MasterPropertyViewModel daModel = (MasterPropertyViewModel)this.model;
		daModel.setPropertyGroupLookup(this.propertyGroupRepository.getAll(PropertyGroup.TYPE_NAME, PropertyGroup.class));
		daModel.setPropertyTypeLookup(this.propertyTypeRepository.getAll(PropertyType.TYPE_NAME, PropertyType.class));
	}
	
	@Override
	public void loadModels() {
		super.loadModels();
		
		for(MasterProperty masterProperty : this.model.getItems())
		{

		//	this.repository.delete(masterProperty);
		//	System.out.println(masterProperty.getPropertyGroupId());
		//	System.out.println(masterProperty.getPropertyTypeId());
		}
		
	}


}
