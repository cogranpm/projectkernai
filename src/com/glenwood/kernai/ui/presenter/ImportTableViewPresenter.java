package com.glenwood.kernai.ui.presenter;

import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.ImportTable;
import com.glenwood.kernai.data.persistence.ImportTableRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditPresenter;
import com.glenwood.kernai.ui.abstraction.IEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.abstraction.IMasterDetailViewModel;
import com.glenwood.kernai.ui.abstraction.IModelChangeListener;

public class ImportTableViewPresenter extends BaseEntityMasterDetailListEditPresenter<ImportTable, ImportDefinition> {

	private List<IModelChangeListener> changeListeners;
	
	public ImportTableViewPresenter(IEntityMasterDetailListEditView<ImportTable, ImportDefinition> view, IMasterDetailViewModel<ImportTable, ImportDefinition> model) {
		super(view, model, ImportTable.class, ImportTable.TYPE_NAME);
		this.repository = new ImportTableRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		this.changeListeners = new ArrayList<IModelChangeListener>();
	}
	
	@Override
	public void loadModels(ImportDefinition parent) {
		super.loadModels(parent);
		ImportTableRepository aRepository = (ImportTableRepository)this.repository;
		this.model.setItems(aRepository.getAllByImportDefinition(parent.getId()));
		this.view.refreshView();
	}
	
	public void addModelChangeListener(IModelChangeListener listener)
	{
		this.changeListeners.add(listener);
	}
	
	public void removeModelChangeListener(IModelChangeListener listener)
	{
		this.changeListeners.remove(listener);
	}
	
	private void broadcastModelChange()
	{
		for(IModelChangeListener listener : this.changeListeners)
		{
			listener.OnModelChanged(this.model.getCurrentItem());
		}
	}
	
	@Override
	public void modelChanged() {
		super.modelChanged();
		//broadcast this to any registered listener
		this.broadcastModelChange();
	}

}
