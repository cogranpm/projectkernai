package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.Model;
import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.data.persistence.ModelRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditPresenter;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.view.ModelView;
import com.glenwood.kernai.ui.viewmodel.ModelViewModel;

public class ModelViewPresenter extends BaseEntityMasterDetailListEditPresenter<Model, Project> {

	public ModelViewPresenter(ModelView view, ModelViewModel model) {
		super(view, model, Model.class, Model.TYPE_NAME);
		this.repository = new ModelRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}
	
	@Override
	public void loadModels(Project parentEntity) {
		super.loadModels(parentEntity);
		ModelRepository aRepository = (ModelRepository)this.repository;
		this.model.setItems(aRepository.getAllByProject(this.model.getParent().getId()));
		((IEntityView)this.view).refreshView();
	}
	

}
