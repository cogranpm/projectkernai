package com.glenwood.kernai.ui.view;

import java.util.List;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.data.modelimport.DatabaseDefinition;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.abstraction.IImportWorkerClient;
import com.glenwood.kernai.ui.presenter.ImportDefinitionViewPresenter;
import com.glenwood.kernai.ui.viewmodel.ImportDefinitionViewModel;
import com.glenwood.kernai.ui.workers.ImportWorker;

public class ImportDefinitionView extends BaseEntityMasterDetailListEditView<ImportDefinition, Project>
	implements IImportWorkerClient{
	
	private DataConnectionInlineView connectionView;
	private ImportTableSelectionInlineView tableSelectionView;
	private Composite connectionContainer;
	private Composite tableSelectionContainer;
	private Composite wizardContainer;
	private StackLayout wizardLayout;
	private Button btnGoSelectTable;
	private Button btnGoConnection;
	private Button btnGoSelectModel;
	private ImportWorker importWorker;

	public ImportDefinitionView(Composite parent, int style, Project parentEntity) {
		super(parent, style, parentEntity);
		this.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (importWorker != null)
				{
					importWorker.closeConnection();
				}
			}
		});
	}
	
	@Override
	protected void setupModelAndPresenter(Project parentEntity) {
		super.setupModelAndPresenter(parentEntity);
		this.model = new ImportDefinitionViewModel(parentEntity);
		this.presenter = new ImportDefinitionViewPresenter(this, this.model);
	}
	
	@Override
	protected void onInit() {
		super.onInit();
		/* lets go into new mode immediately - is a no no, because when list changes
		 * a whole heap of null ones get added */
		//this.add();
	}
	
	@Override
	protected void setupListColumns() {
		super.setupListColumns();
		TableViewerColumn vendorColumn = this.viewHelper.getListColumn(listViewer, "Vendor");
		TableViewerColumn hostnameColumn = this.viewHelper.getListColumn(listViewer, "Hostname");
		TableViewerColumn userNameColumn = this.viewHelper.getListColumn(listViewer, "UserName");
		TableViewerColumn lastRunColumn = this.viewHelper.getListColumn(listViewer, "Last Run");
		//connectionVendorColumn.getColumn().addSelectionListener(this.getSelectionAdapter(connectionVendorColumn.getColumn(), 0));
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(vendorColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(hostnameColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(userNameColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(lastRunColumn.getColumn(), new ColumnWeightData(100));
		
	}

	
	@Override
	protected void onInitDataBindings() {
		super.onInitDataBindings();
        List<ImportDefinition> el = model.getItems();
        input = new WritableList(el, ImportDefinition.class);


        final IObservableMap connections = BeanProperties.value(ImportDefinition.class, "dataConnection").observeDetail(knownElements);
        IObservableMap[] labelMaps = {connections};
        ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
                	ImportDefinition mc = (ImportDefinition)element;
                	DataConnection dc = mc.getDataConnection();
                	switch(columnIndex)
                	{
                	case 0:
                    	if(dc != null)
                    	{
                    		return dc.getVendorNameLookup().getLabel();
                    	}
                    	else
                    	{
                    		return "";
                    	}
                	case 1:
                		if (dc != null)
                		{
                			return dc.getServerName();
                		}
                		else
                		{
                			return "";
                		}
                	case 2:
                		return (dc == null) ? "" : dc.getUserName();
                	case 3:
                		return (mc.getLastRun() == null) ? "" : mc.getLastRun().toString();
                    default:
                    	return "";
                	}
                }
        };
        listViewer.setLabelProvider(labelProvider);
        listViewer.setInput(input);
	}
	
	@Override
	protected void onSetupEditingContainer() {
		super.onSetupEditingContainer();
		wizardContainer = new Composite(editMaster, SWT.NONE);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, true).indent(0, 0).applyTo(wizardContainer);
		wizardLayout = new StackLayout();
		wizardContainer.setLayout(wizardLayout);
		connectionContainer = new Composite(wizardContainer, SWT.NONE);
		connectionContainer.setLayout(viewHelper.getViewLayout(1));
		connectionView = new DataConnectionInlineView(connectionContainer, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).indent(0, 0).applyTo(connectionView);
		
		this.tableSelectionContainer = new Composite(wizardContainer, SWT.NONE);
		this.tableSelectionContainer.setLayout(viewHelper.getViewLayout(1));
		
		wizardLayout.topControl = connectionContainer;
		
		btnGoSelectTable = new Button(connectionContainer, SWT.PUSH);
		btnGoSelectTable.setText("Next");
		btnGoSelectTable.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				onGoSelectTable();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		GridDataFactory.fillDefaults().span(2,1).align(SWT.RIGHT, SWT.CENTER).grab(false, false).applyTo(btnGoSelectTable);
		
		
		
	}
	
	@Override
	public void save() {
		if(this.connectionView.isValid())
		{
			this.connectionView.save();
			this.model.getCurrentItem().setDataConnection(this.connectionView.model.getCurrentItem());
			super.save();
		}
		
	}
	
	@Override
	protected void onAdd() {
		super.onAdd();
		if(connectionView != null)
		{
			this.connectionView.presenter.addModel();
		}
	}
	
	@Override
	protected void onListSelectionChangedHandler(ImportDefinition entity) {
		super.onListSelectionChangedHandler(entity);
		if(entity != null)
		{
			DataConnection dc = entity.getDataConnection();
			if(dc != null)
			{
				this.connectionView.getPresenter().loadModel(dc);
			}
		}
	}
	
	private void onGoSelectTable()
	{
		/* if the connection works, save it */
		this.btnGoSelectTable.setEnabled(false);
		if(!this.connectionView.isValid())
		{
			this.btnGoSelectTable.setEnabled(true);
			return;
		}
		if((this.model.getDirty() || this.connectionView.getModel().getDirty()))
		{
			this.save();
		}
		
		/* connect first, showing a progress, must be asynchronous */
		DataConnection connection = connectionView.getModel().getCurrentItem();
		if(connection == null)
		{
			return;
		}
		importWorker = new ImportWorker(connection);
		importWorker.openConnection(this, this.getDisplay());
		
	}

	@Override
	public void onConnectError() {
		this.btnGoSelectTable.setEnabled(true);
	}

	@Override
	public void onConnect() {
		//MessageDialog.openInformation(getShell(), "Complete", "Connection Complete");
		//importWorker.closeConnection();
		
		if (this.tableSelectionView == null)
		{
			this.tableSelectionView = new ImportTableSelectionInlineView(tableSelectionContainer, SWT.NONE, this.model.getCurrentItem());
			GridDataFactory.fillDefaults().grab(true, true).indent(0, 0).applyTo(this.tableSelectionView);
			btnGoConnection= new Button(tableSelectionContainer, SWT.PUSH);
			btnGoConnection.setText("Back");
			GridDataFactory.fillDefaults().align(SWT.RIGHT, SWT.CENTER).grab(false, false).applyTo(btnGoConnection);
			this.btnGoConnection.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					wizardLayout.topControl = connectionContainer;
					wizardContainer.layout();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}
		else
		{
			//if view is already loaded, change the model
		}
		this.wizardLayout.topControl = this.tableSelectionContainer;
		this.tableSelectionContainer.layout();
		this.wizardContainer.layout();
		this.tableSelectionView.setupImportBindings(this.importWorker);
		
		/* temp */
		this.btnGoSelectTable.setEnabled(true);
	}

	@Override
	public void setDatabases(List<DatabaseDefinition> list) {
	}
	
	
	
}
