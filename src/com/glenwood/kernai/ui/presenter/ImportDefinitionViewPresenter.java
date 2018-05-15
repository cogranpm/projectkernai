package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.data.persistence.ImportDefinitionRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditPresenter;
import com.glenwood.kernai.ui.abstraction.IEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.abstraction.IMasterDetailViewModel;
import com.glenwood.kernai.ui.view.DataConnectionInlineView;
import com.glenwood.kernai.ui.view.ImportDefinitionView;
import com.glenwood.kernai.ui.view.ImportTableSelectionInlineView;

public class ImportDefinitionViewPresenter extends BaseEntityMasterDetailListEditPresenter<ImportDefinition, Project> {

	
	private DataConnectionInlineView dataConnectionView;
	private ImportTableSelectionInlineView importTableSelectionView;
	
	public DataConnectionInlineView getDataConnectionView() {
		return dataConnectionView;
	}

	public void setDataConnectionView(DataConnectionInlineView dataConnectionView) {
		this.dataConnectionView = dataConnectionView;
		DataConnectionViewPresenter connectionViewPresenter = (DataConnectionViewPresenter)this.dataConnectionView.getPresenter();
		if (connectionViewPresenter != null)
		{
			connectionViewPresenter.addConnectionContainer((ImportDefinitionView)this.view);
		}		
	}
	
	public String getDataConnectionViewID()
	{
		if(this.dataConnectionView != null)
		{
			return this.dataConnectionView.getClass().getName();
		}
		else
		{
			return "";
		}
	}
	
	public String getImportTableSelectionViewID()
	{
		if (this.importTableSelectionView != null)
		{
			return this.importTableSelectionView.getClass().getName();
		}
		else
		{
			return "";
		}
	}

	public ImportTableSelectionInlineView getImportTableSelectionView() {
		return importTableSelectionView;
	}

	public void setImportTableSelectionView(ImportTableSelectionInlineView importTableSelectionView) {
		this.importTableSelectionView = importTableSelectionView;
	}

	public ImportDefinitionViewPresenter(IEntityMasterDetailListEditView<ImportDefinition, Project> view,
			IMasterDetailViewModel<ImportDefinition, Project> model) {
		super(view, model, ImportDefinition.class, ImportDefinition.TYPE_NAME);
		this.repository = new ImportDefinitionRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		this.dataConnectionView = null;
		this.importTableSelectionView = null;
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
