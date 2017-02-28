package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.Association;
import com.glenwood.kernai.data.entity.Model;
import com.glenwood.kernai.data.persistence.AssociationRepository;
import com.glenwood.kernai.data.persistence.EntityRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditPresenter;
import com.glenwood.kernai.ui.view.AssociationView;
import com.glenwood.kernai.ui.viewmodel.AssociationViewModel;

public class AssociationViewPresenter extends BaseEntityMasterDetailListEditPresenter<Association, Model> {
	
	EntityRepository entityRepository;
	
	public AssociationViewPresenter(AssociationView view, AssociationViewModel model)
	{
		super(view, model, Association.class, Association.TYPE_NAME);
		this.repository = new AssociationRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		this.entityRepository = new EntityRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		AssociationViewModel aModel = (AssociationViewModel)this.model;
		aModel.setEntityLookup(entityRepository.getAllByModel(this.model.getParent().getId()));
	}

}
