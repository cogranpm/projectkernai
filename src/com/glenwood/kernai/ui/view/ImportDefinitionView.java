package com.glenwood.kernai.ui.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import org.eclipse.swt.widgets.Control;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.data.modelimport.DatabaseDefinition;
import com.glenwood.kernai.data.modelimport.TableDefinition;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.abstraction.IModelChangeListener;
import com.glenwood.kernai.ui.abstraction.IDataConnectionClient;
import com.glenwood.kernai.ui.presenter.DataConnectionViewPresenter;
import com.glenwood.kernai.ui.presenter.ImportDefinitionViewPresenter;
import com.glenwood.kernai.ui.viewmodel.ImportDefinitionViewModel;
import com.glenwood.kernai.ui.workers.ImportWorker;

public class ImportDefinitionView extends BaseEntityMasterDetailListEditView<ImportDefinition, Project>
	implements IDataConnectionClient, IModelChangeListener{
	
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
		this.connectionContainer = new Composite(wizardContainer, SWT.NONE);
		this.connectionContainer.setLayout(viewHelper.getViewLayout(1));

		connectionView = new DataConnectionInlineView(connectionContainer, SWT.NONE);
		DataConnectionViewPresenter connectionViewPresenter = (DataConnectionViewPresenter)connectionView.getPresenter();
		if (connectionViewPresenter != null)
		{
			connectionViewPresenter.addConnectionContainer(this);
		}
		
		GridDataFactory.fillDefaults().grab(true, true).indent(0, 0).applyTo(connectionView);
		
		this.tableSelectionContainer = new Composite(wizardContainer, SWT.NONE);
		this.tableSelectionContainer.setLayout(viewHelper.getViewLayout(1));
		this.viewHelper.setViewLayoutData(this.tableSelectionContainer, true, true);
		
		wizardLayout.topControl = connectionContainer;
		wizardLayout.topControl.setData("id", this.connectionView.getClass().getName());
		
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
		
		
		//editMaster.setBackground(this.getDisplay().getSystemColor(SWT.COLOR_DARK_MAGENTA));
	}
	
	@Override
	public void save() {
		/* which child view is active */
		if (this.wizardLayout.topControl != null)
		{
			String topControlData = this.wizardLayout.topControl.getData("id").toString();
			if(this.connectionView.getClass().getName().equalsIgnoreCase(topControlData))
			{
				if(this.connectionView.isValid())
				{
					this.connectionView.save();
					this.model.getCurrentItem().setDataConnection(this.connectionView.model.getCurrentItem());
					super.save();
				}
			}
			else if(this.tableSelectionView.getClass().getName().equals(topControlData))
			{
				if(this.tableSelectionView.isValid())
				{
					this.model.getCurrentItem().setLastSavedDatabaseName(this.tableSelectionView.getSelectedDatabaseName());
					super.save();
				}
			}
		}
	}
	
	@Override
	protected void onAdd() {
		super.onAdd();
		if(connectionView != null)
		{
			this.connectionView.presenter.addModel();
			this.connectionView.getModel().getCurrentItem().addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					presenter.modelChanged();
				}
			});
		}
	}
	
	@Override
	protected void onListSelectionChangedHandler(ImportDefinition entity) {
		if(!this.wizardLayout.topControl.getData("id").toString().equalsIgnoreCase(this.connectionView.getClass().getName()))
		{
			this.wizardLayout.topControl = this.connectionContainer;
			this.wizardContainer.layout();
		}

		super.onListSelectionChangedHandler(entity);
		if(entity != null)
		{
			DataConnection dc = entity.getDataConnection();
			if(dc != null)
			{
				/* depends on which wizard view is active what we do here */
				this.connectionView.getPresenter().loadModel(dc);
			}
		}
	}
	
	public DataConnectionInlineView getConnectionView()
	{
		return this.connectionView;
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
		
		/* if the data connection has changed or is no longer open then a new connection must be opened */
		boolean reconnectRequired = false;
		boolean closeRequired = false;
		if(this.importWorker == null || this.importWorker.getConnection() == null)
		{

			reconnectRequired = true;
		}
		else
		{
			if (!this.importWorker.getConnection().isValid())
			{
				reconnectRequired = true;
				closeRequired = false;
			}
			else
			{
				DataConnection existingDataConnection = this.importWorker.getConnection().getDataConnection();
				if(connection.compare(existingDataConnection))
				{
					reconnectRequired = false;
					closeRequired = false;
				}
				else
				{
					reconnectRequired = true;
					closeRequired = true;
				}
			}
		}
		
		if(closeRequired)
		{
			this.importWorker.closeConnection();
		}
		if(reconnectRequired)
		{
			this.importWorker = new ImportWorker(connection);
			this.importWorker.openConnection(this, this.getDisplay());
		}
		else
		{
			this.loadTableSelectionView(false);
		}
	}
	
	private void onGoSelectModel()
	{
		if(this.model.getDirty())
		{
			this.save();
		}
	}
	
	public void loadTableSelectionView(boolean reconnecting)
	{
		if (this.tableSelectionView == null)
		{
			this.tableSelectionView = new ImportTableSelectionInlineView(tableSelectionContainer, SWT.NONE, this.model.getCurrentItem());
			this.tableSelectionView.getPresenter().addModelChangeListener(this);
			GridDataFactory.fillDefaults().grab(true, true).indent(0, 0).applyTo(this.tableSelectionView);
			btnGoConnection= new Button(tableSelectionContainer, SWT.PUSH);
			btnGoConnection.setText("Back");
			btnGoSelectModel = new Button(tableSelectionContainer, SWT.PUSH);
			btnGoSelectModel.setText("Next");
			GridDataFactory.fillDefaults().align(SWT.RIGHT, SWT.CENTER).grab(false, false).applyTo(btnGoConnection);
			GridDataFactory.fillDefaults().align(SWT.RIGHT, SWT.CENTER).grab(false, false).applyTo(btnGoSelectModel);
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
			
			this.btnGoSelectModel.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					onGoSelectModel();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
			this.tableSelectionView.setupImportBindings(this.importWorker);
			this.tableSelectionView.getDatabases();
		}
		else
		{
			if(reconnecting)
			{
				this.tableSelectionView.setupImportBindings(this.importWorker);
				this.tableSelectionView.getDatabases();
			}
		}
		this.wizardLayout.topControl = this.tableSelectionContainer;
		this.wizardLayout.topControl.setData("id", tableSelectionView.getClass().getName());
		this.tableSelectionContainer.layout();
		this.wizardContainer.layout();
		
		/* temp */
		this.btnGoSelectTable.setEnabled(true);
	}

	@Override
	public void onConnectError(String message) {
		this.btnGoSelectTable.setEnabled(true);
		MessageDialog.openError(this.getShell(), "Connection Error", message);
	}
	
	
	

	@Override
	public void onConnect() {
		/* callback from thread that opens connection */
		this.loadTableSelectionView(true);
	}

	@Override
	public void OnModelChanged(BaseEntity entity) {
		this.model.setDirty(true);
		
	}
	
	
	
}
