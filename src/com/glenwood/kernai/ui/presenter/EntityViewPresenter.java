package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.entity.Model;
import com.glenwood.kernai.data.persistence.EntityRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditPresenter;
import com.glenwood.kernai.ui.view.EntityView;
import com.glenwood.kernai.ui.viewmodel.EntityViewModel;

public class EntityViewPresenter extends BaseEntityMasterDetailListEditPresenter<Entity, Model> {
	
	public EntityViewPresenter(EntityView view, EntityViewModel model)
	{
		super(view, model, Entity.class, Entity.TYPE_NAME);
		this.repository = new EntityRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}
	
	@Override
	public void loadModels(Model parent) {
		super.loadModels(parent);
		EntityRepository aRepository = (EntityRepository)this.repository;
		this.model.setItems(aRepository.getAllByModel(parent.getId()));
	}

}
