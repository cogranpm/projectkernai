package com.glenwood.kernai.ui.view;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.ImportTable;
import com.glenwood.kernai.data.modelimport.DatabaseDefinition;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.abstraction.IImportWorkerClient;
import com.glenwood.kernai.ui.presenter.ImportTableViewPresenter;
import com.glenwood.kernai.ui.viewmodel.ImportTableViewModel;
import com.glenwood.kernai.ui.workers.ImportWorker;

public class ImportTableSelectionInlineView extends BaseEntityMasterDetailListEditView<ImportTable, ImportDefinition> 
	implements IImportWorkerClient{

	private Label lblDatabase;
	private ComboViewer cboDatabase;
	private ImportWorker importWorker;
	
	public ImportTableSelectionInlineView(Composite parent, int style, ImportDefinition parentEntity) {
		super(parent, style, parentEntity);
	}
	
	
	@Override
	protected void onInit() {
		super.onInit();
		this.enableEditControls();
	}
	
	
	public void setupImportBindings(ImportWorker importWorker)
	{
		this.importWorker = importWorker;
		this.importWorker.getDatabases(this, this.getDisplay());
	}
	
	@Override
	protected void setupModelAndPresenter(ImportDefinition parentEntity) {
		super.setupModelAndPresenter(parentEntity);
		this.model = new ImportTableViewModel(parentEntity);
		this.presenter = new ImportTableViewPresenter(this, this.model);
	}
	

	@Override
	protected void onInitDataBindings() {
		super.onInitDataBindings();
	}
	
	@Override
	protected void onSetupEditingContainer() {
		super.onSetupEditingContainer();
		this.lblDatabase = viewHelper.getEditLabel(editMaster, "Database");
		this.cboDatabase = new ComboViewer(editMaster, SWT.NONE);
		this.cboDatabase.setContentProvider(ArrayContentProvider.getInstance());
		this.cboDatabase.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element)
			{
				DatabaseDefinition item = (DatabaseDefinition)element;
				return item.getName();
			}
		});

		//cboDataType.setInput(aModel.getDataTypeLookup());
		
		viewHelper.layoutEditLabel(lblDatabase);
		viewHelper.layoutComboViewer(cboDatabase);
		
		
	}

	@Override
	public void onConnectError() {
	}

	@Override
	public void onConnect() {
	}

	@Override
	public void setDatabases(List<DatabaseDefinition> list) {
		this.cboDatabase.setInput(list);
	}

	
}
