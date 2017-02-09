package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.PropertyType;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.data.persistence.PropertyTypeRepository;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityPresenter;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.abstraction.IViewModel;

public class PropertyTypeViewPresenter extends BaseEntityPresenter<PropertyType> {

	public PropertyTypeViewPresenter(IEntityView view, IViewModel<PropertyType> model) {
		super(view, model);
		this.repository = new PropertyTypeRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}

	
	@Override
	public void loadModels()
	{
		this.model.setItems(this.repository.getAll(PropertyType.TYPE_NAME, PropertyType.class));
		if (this.model.getItems() != null && this.model.getItems().size() > 0)
		{
			this.model.setCurrentItem(this.model.getItems().get(0));
		}

	}
	
	@Override
	public void addModel() {
		super.addModel();
		PropertyType newEntity = new PropertyType();
		this.model.setCurrentItem(newEntity);
	}
}
