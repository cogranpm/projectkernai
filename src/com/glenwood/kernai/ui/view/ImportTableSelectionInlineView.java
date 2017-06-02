package com.glenwood.kernai.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.ImportTable;
import com.glenwood.kernai.data.modelimport.DatabaseDefinition;
import com.glenwood.kernai.data.modelimport.TableDefinition;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.IEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.abstraction.IImportWorkerClient;
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
	private CLabel lblTableSource;
	private CLabel lblTableSelection;
	private TableViewer listTableSource;

	private TableViewer listTableSelection;
	private Button btnAddAll;
	private Button btnAddSelected;
	private Button btnRemoveAll;
	private Button btnRemoveSelected;

	
	private WritableList<TableDefinition> tableSourceListWrapper;
	private DataBindingContext ctx;
	private List<TableDefinition> tableSourceList;
	
	private ImportWorker importWorker;
	
	private ImportDefinition parentEntity;
	private ImportTableViewModel model;
	private ImportTableViewPresenter presenter;
	

	private EntityViewHelper viewHelper;
	

	public ImportTableSelectionInlineView(Composite parent, int style, ImportDefinition parentEntity) {
		super(parent, style);
		this.parentEntity = parentEntity;
		this.ctx = new DataBindingContext();
		this.init();
	}
	
	private void init()
	{
		this.viewHelper = new EntityViewHelper();
		this.setupModelAndPresenter(this.parentEntity);
		this.setupEditingContainer();
		this.initDataBindings();
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
		/* databindings for the lists, each a list of tableDefinition objects */
		this.ctx.dispose();
		this.tableSourceList = new ArrayList<TableDefinition>();
		this.tableSourceListWrapper = new WritableList<>(this.tableSourceList, TableDefinition.class);
		ViewerSupport.bind(this.listTableSource, tableSourceListWrapper, BeanProperties.value("name"), BeanProperties.value("database.name"));
	}
	

	private void setupEditingContainer() {
		editContainer = new Composite(this, SWT.NONE);
		editContainer.setLayout(viewHelper.getViewLayout(1, 0));
		
		editHeader = new Composite(editContainer, SWT.NONE);
		editHeader.setLayout(viewHelper.getViewLayout(2, 0));
		lblEditHeader = new CLabel(editHeader, SWT.NONE);
		lblEditHeader.setText("");
		GridDataFactory.fillDefaults().span(2, 1).applyTo(lblEditHeader);
		viewHelper.setViewLayoutData(editHeader, true, false);
		/*
		editMaster = new Composite(editContainer, SWT.NONE);
		editMaster.setLayout(viewHelper.getViewLayout(2));
		viewHelper.setViewLayoutData(editMaster, true, true);
		*/
		
		editDetail = new Composite(editContainer, SWT.NONE);
		viewHelper.setViewLayoutData(editDetail, true, true);
		editDetail.setLayout(new FillLayout());
		
		//errorLabel = new CLabel(editMaster, SWT.NONE);
		//viewHelper.setViewLayoutData(errorLabel, 2);
		
		
		this.lblDatabase = viewHelper.getEditLabel(editHeader, "Database");
		this.cboDatabase = new ComboViewer(editHeader, SWT.NONE);
		this.cboDatabase.setContentProvider(ArrayContentProvider.getInstance());
		this.cboDatabase.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element)
			{
				DatabaseDefinition item = (DatabaseDefinition)element;
				return item.getName();
			}
		});
		this.cboDatabase.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if(selection != null)
				{
					DatabaseDefinition database =  (DatabaseDefinition) selection.getFirstElement();
					onSelectDatabase(database);
					
				}
				
			}
		});
		
		viewHelper.layoutEditLabel(lblDatabase);
		viewHelper.layoutComboViewer(cboDatabase);
		
		Group grp = new Group(editDetail, SWT.NONE);
		grp.setLayout(new GridLayout(3, false));
		Composite columnSource = new Composite(grp, SWT.NONE);
		Composite columnButtons = new Composite(grp, SWT.NONE);
		Composite columnSelection = new Composite(grp, SWT.NONE);
		columnSource.setLayout(new FillLayout());
		columnButtons.setLayout(new RowLayout(SWT.VERTICAL));
		columnSelection.setLayout(new FillLayout());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(columnSource);
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(columnButtons);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(columnSelection);
		
		listTableSource = new TableViewer(columnSource, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		listTableSource.getTable().setHeaderVisible(true);
		listTableSource.getTable().setLinesVisible(true);
		TableViewerColumn nameColumn = this.viewHelper.getListColumn(listTableSource, "Name");
		TableViewerColumn databaseColumn = this.viewHelper.getListColumn(listTableSource, "Database");
		TableColumnLayout tableLayout = new TableColumnLayout();
		columnSource.setLayout(tableLayout);
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(databaseColumn.getColumn(), new ColumnWeightData(100));

		btnAddAll = new Button(columnButtons, SWT.PUSH);
		btnAddAll.setImage(ApplicationData.instance().getImageRegistry().get(ApplicationData.IMAGE_GO_LAST_VIEW_SMALL));
		btnAddSelected = new Button(columnButtons, SWT.PUSH);
		btnAddSelected.setImage(ApplicationData.instance().getImageRegistry().get(ApplicationData.IMAGE_GO_NEXT_SMALL));
		btnRemoveSelected = new Button(columnButtons, SWT.PUSH);
		btnRemoveSelected.setImage(ApplicationData.instance().getImageRegistry().get(ApplicationData.IMAGE_GO_PREVIOUS_SMALL));
		btnRemoveAll = new Button(columnButtons, SWT.PUSH);
		btnRemoveAll.setImage(ApplicationData.instance().getImageRegistry().get(ApplicationData.IMAGE_GO_FIRST_VIEW_SMALL));
		
		listTableSelection  = new TableViewer(columnSelection, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		listTableSelection.getTable().setHeaderVisible(true);
		listTableSelection.getTable().setLinesVisible(true);
		TableViewerColumn nameSelectionColumn = this.viewHelper.getListColumn(listTableSelection, "Name");
		TableViewerColumn databaseSelectionColumn = this.viewHelper.getListColumn(listTableSelection, "Database");
		TableColumnLayout tableSelectionLayout = new TableColumnLayout();
		columnSelection.setLayout(tableSelectionLayout);
		tableSelectionLayout.setColumnData(nameSelectionColumn.getColumn(), new ColumnWeightData(100));
		tableSelectionLayout.setColumnData(databaseSelectionColumn.getColumn(), new ColumnWeightData(100));

		/* note, use list filters on TableDefinition selected field to handle dual list */
	}
	
	public void onSelectDatabase(DatabaseDefinition database)
	{
		this.tableSourceListWrapper.clear();
		importWorker.getTables(this, this.getDisplay(), database);
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
	public void setTables(List<TableDefinition> list) {
		this.tableSourceListWrapper.addAll(list);
	}


	@Override
	public void delete() {
	}


	@Override
	public void add() {
	}


	@Override
	public void save() {
		this.presenter.saveModel();
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
