package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.PropertyGroup;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.data.persistence.PropertyGroupRepository;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityPresenter;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.abstraction.IViewModel;

public class PropertyGroupViewPresenter extends BaseEntityPresenter<PropertyGroup>{

	public PropertyGroupViewPresenter(IEntityView view, IViewModel<PropertyGroup> model) {
		super(view, model, PropertyGroup.class, PropertyGroup.TYPE_NAME);
		this.repository = new PropertyGroupRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}
	
/*
	@Override
	public void loadModels()
	{
		this.model.setItems(this.repository.getAll(PropertyGroup.TYPE_NAME, PropertyGroup.class));
		if (this.model.getItems() != null && this.model.getItems().size() > 0)
		{
			this.model.setCurrentItem(this.model.getItems().get(0));
		}

	}
	
	@Override
	public void addModel()
	{
		PropertyGroup newEntity = new PropertyGroup();
		model.setCurrentItem(newEntity);
	}
	*/

}
