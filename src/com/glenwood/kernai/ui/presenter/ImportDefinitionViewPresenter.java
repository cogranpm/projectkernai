package com.glenwood.kernai.ui.presenter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.data.persistence.ImportDefinitionRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditPresenter;
import com.glenwood.kernai.ui.abstraction.IEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.abstraction.IMasterDetailViewModel;
import com.glenwood.kernai.ui.view.ImportDefinitionView;

public class ImportDefinitionViewPresenter extends BaseEntityMasterDetailListEditPresenter<ImportDefinition, Project> {

	public ImportDefinitionViewPresenter(IEntityMasterDetailListEditView<ImportDefinition, Project> view,
			IMasterDetailViewModel<ImportDefinition, Project> model) {
		super(view, model, ImportDefinition.class, ImportDefinition.TYPE_NAME);
		this.repository = new ImportDefinitionRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}
	
	@Override
	public void loadModels(Project parent) {
		super.loadModels(parent);
		ImportDefinitionRepository aRepository = (ImportDefinitionRepository)this.repository;
		this.model.setItems(aRepository.getAllByProject(parent.getId()));
		this.view.refreshView();
	}
	
	@Override
	public void loadModel(ImportDefinition entity) {
		super.loadModel(entity);
		
		/*
		ImportDefinitionView aView = (ImportDefinitionView)this.view;
		if(aView.getConnectionView() != null && aView.getConnectionView().getModel() != null && aView.getConnectionView().getModel().getCurrentItem() != null)
		{
			DataConnection currentConnection = aView.getConnectionView().getModel().getCurrentItem();
			currentConnection.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					modelChanged();
				}
			});
		}
		*/
	}

}
