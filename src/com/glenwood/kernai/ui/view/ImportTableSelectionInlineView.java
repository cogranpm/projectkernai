package com.glenwood.kernai.ui.view;

import org.eclipse.swt.widgets.Composite;

import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.ImportTable;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditView;

public class ImportTableSelectionInlineView extends BaseEntityMasterDetailListEditView<ImportTable, ImportDefinition> {

	
	public ImportTableSelectionInlineView(Composite parent, int style, ImportDefinition parentEntity) {
		super(parent, style, parentEntity);
		
	}

	@Override
	protected void setupModelAndPresenter(ImportDefinition parentEntity) {
		super.setupModelAndPresenter(parentEntity);
	}
	


	
}
