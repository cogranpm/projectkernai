package com.glenwood.kernai.ui.view;

import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;

import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.ImportTable;
import com.glenwood.kernai.data.modelimport.DatabaseDefinition;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.abstraction.IEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.abstraction.IEntityMasterDetailListModalPresenter;
import com.glenwood.kernai.ui.abstraction.IImportWorkerClient;
import com.glenwood.kernai.ui.abstraction.IMasterDetailViewModel;
import com.glenwood.kernai.ui.presenter.ImportTableViewPresenter;
import com.glenwood.kernai.ui.view.helpers.EntityViewHelper;
import com.glenwood.kernai.ui.viewmodel.ImportTableViewModel;
import com.glenwood.kernai.ui.workers.ImportWorker;

public class ImportTableSelectionInlineView extends Composite implements IEntityMasterDetailListEditView<ImportTable, ImportDefinition>, IImportWorkerClient{

	private Composite editHeader;
	private Composite editMaster;
	private Composite editDetail;
	private Composite editContainer;
	private CLabel lblEditHeader;
	private CLabel errorLabel;
	
	private Label lblDatabase;
	private ComboViewer cboDatabase;
	private ImportWorker importWorker;
	
	private ImportDefinition parentEntity;
	private ImportTableViewModel model;
	private ImportTableViewPresenter presenter;
	
	private DataBindingContext ctx;
	private EntityViewHelper viewHelper;
	

	public ImportTableSelectionInlineView(Composite parent, int style, ImportDefinition parentEntity) {
		super(parent, style);
		this.parentEntity = parentEntity;
		this.init();
	}
	
	private void init()
	{
		this.viewHelper = new EntityViewHelper();
		this.setupModelAndPresenter(this.parentEntity);
		this.initDataBindings();
		this.setupEditingContainer();
		this.setLayout(new FillLayout());
	}
	
	
	/*
	 * called externally to start the process off
	 */
	public void setupImportBindings(ImportWorker importWorker)
	{
		this.importWorker = importWorker;
		this.importWorker.getDatabases(this, this.getDisplay());
	}
	

	private void setupModelAndPresenter(ImportDefinition parentEntity) {
		this.model = new ImportTableViewModel(parentEntity);
		this.presenter = new ImportTableViewPresenter(this, this.model);
	}
	
	private void initDataBindings()
	{
		
	}
	

	private void setupEditingContainer() {
		editContainer = new Composite(this, SWT.NONE);
		editContainer.setLayout(viewHelper.getViewLayout(1, 0));
		
		editHeader = new Composite(editContainer, SWT.NONE);
		editHeader.setLayout(viewHelper.getViewLayout(1, 0));
		lblEditHeader = new CLabel(editHeader, SWT.NONE);
		lblEditHeader.setText("");
		GridDataFactory.fillDefaults().applyTo(lblEditHeader);
		GridDataFactory.fillDefaults().applyTo(editHeader);
		
		
		editMaster = new Composite(editContainer, SWT.NONE);
		editMaster.setLayout(viewHelper.getViewLayout(2));
		GridDataFactory.fillDefaults().applyTo(editMaster);
		
		viewHelper.setViewLayoutData(editMaster, true, true);
		editDetail = new Composite(editContainer, SWT.NONE);
		editDetail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1 ));
		editDetail.setLayout(new FillLayout());
		errorLabel = new CLabel(editMaster, SWT.NONE);
		viewHelper.setViewLayoutData(errorLabel, 2);
		
		
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


	@Override
	public void delete() {
	}


	@Override
	public void add() {
	}


	@Override
	public void save() {
	}


	@Override
	public void refreshView() {
	}


	@Override
	public void afterAdd() {
	}


	@Override
	public void afterSelection() {
	}

	
}
