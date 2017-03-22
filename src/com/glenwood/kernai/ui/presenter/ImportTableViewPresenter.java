package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.ImportTable;
import com.glenwood.kernai.data.persistence.ImportTableRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditPresenter;
import com.glenwood.kernai.ui.abstraction.IEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.abstraction.IMasterDetailViewModel;

public class ImportTableViewPresenter extends BaseEntityMasterDetailListEditPresenter<ImportTable, ImportDefinition> {

	public ImportTableViewPresenter(IEntityMasterDetailListEditView<ImportTable, ImportDefinition> view, IMasterDetailViewModel<ImportTable, ImportDefinition> model) {
		super(view, model, ImportTable.class, ImportTable.TYPE_NAME);
		this.repository = new ImportTableRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}
	
	@Override
	public void loadModels(ImportDefinition parent) {
		super.loadModels(parent);
		ImportTableRepository aRepository = (ImportTableRepository)this.repository;
		this.model.setItems(aRepository.getAllByImportDefinition(parent.getId()));
		this.view.refreshView();
	}

}
