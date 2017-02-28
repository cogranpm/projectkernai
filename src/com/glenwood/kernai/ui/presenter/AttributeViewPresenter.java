package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.persistence.AttributeRepository;
import com.glenwood.kernai.data.persistence.ListHeaderRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditPresenter;
import com.glenwood.kernai.ui.view.AttributeView;
import com.glenwood.kernai.ui.viewmodel.AttributeViewModel;

public class AttributeViewPresenter extends BaseEntityMasterDetailListEditPresenter<Attribute, Entity> {
	
	ListHeaderRepository listHeaderRepository;

	public AttributeViewPresenter(AttributeView view, AttributeViewModel model)
	{
		super(view, model, Attribute.class, Attribute.TYPE_NAME);
		this.repository = new AttributeRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		this.listHeaderRepository = new ListHeaderRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		AttributeViewModel aModel = (AttributeViewModel)this.model;
		aModel.setDataTypeLookup(this.listHeaderRepository.getListItemsByName(ApplicationData.LIST_DATATYPE_NAME));
	}
	
	@Override
	public void loadModels(Entity parent) {
		super.loadModels(parent);
		AttributeRepository aRepository = (AttributeRepository)this.repository;
		this.model.setItems(aRepository.getAllByEntity(parent.getId()));
		for(Attribute attribute : this.model.getItems())
		{
			System.out.println(attribute.getName());
		}
	}
	
	@Override
	public void saveModel() {
		System.out.println(this.model.getParent().getId());
		super.saveModel();
	}
	
	
}
