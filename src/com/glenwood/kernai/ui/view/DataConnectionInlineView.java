/* a connection view that is designed to be placed inline within another view
 * has a drop down list with all existing connections
 * try the multi column combo from nebula
 * a drop down for vendor
 * the view should dynamically show items that are specific to a vendor
 * eg: is express checkbox for mssql, sid for oracle 
 */

package com.glenwood.kernai.ui.view;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.nebula.jface.tablecomboviewer.TableComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.IEntityPresenter;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.abstraction.IViewModel;
import com.glenwood.kernai.ui.presenter.DataConnectionViewPresenter;
import com.glenwood.kernai.ui.view.helpers.EntityViewHelper;
import com.glenwood.kernai.ui.viewmodel.DataConnectionViewModel;



public class DataConnectionInlineView extends Composite implements IEntityView  {

	
	protected DataConnectionViewPresenter presenter;
	protected IViewModel<DataConnection> model;
	protected DataBindingContext ctx;
	protected WritableValue<DataConnection> value;
	protected ObservableListContentProvider contentProvider;
	protected IObservableSet<DataConnection> knownElements;
	
	protected EntityViewHelper viewHelper;
	private Composite editContainer;
	private Composite editMaster;
	//private Composite editDetail;
	
	private CLabel lblError;
	private Label lblServerName;
	private Text txtServerName;
	private Label lblUserName;
	private Text txtUserName;
	private Label lblPassword;
	private Text txtPassword;
	private Label lblPort;
	private Spinner spPort;
	private Label lblSid;
	private Text txtSid;
	private Label lblIsExpress;
	private Button chkIsExpress;
	
	/* combo viewer for the vendor name */
	private Label lblVendorName;
	private ComboViewer cboVendorName;
	
	/* multi column list for connections */
	private Label lblConnections;
	private TableComboViewer list;
	
	private Binding sidBinding;
    private IObservableValue sidTargetObservable;
    private IObservableValue sidModelObservable;
    ControlDecorationSupport sidNameDecorator;
    
	protected DataConnectionInlineView(Composite parent, int style) {
		super(parent, style);
		this.init();
	}
	
	/* expose so clients embedding this view have access to behaviour and data */
	public IEntityPresenter<DataConnection> getPresenter()
	{
		return this.presenter;
	}
	
	public IViewModel<DataConnection> getModel()
	{
		return this.model;
	}
	
	private final void init()
	{
		this.viewHelper = new EntityViewHelper();
		this.setupModelAndPresenter();
		editContainer = new Composite(this, SWT.NONE);
		editContainer.setLayout(viewHelper.getViewLayout(1, 0));
		this.setupListColumns();
		this.setupEditingContainer();
		
		ctx = new DataBindingContext();
		value = new WritableValue<DataConnection>();
		presenter.loadModels();


    	
		this.initDataBindings();
		this.onInit();
		this.setLayout(new FillLayout());
	}
	
	protected void onInit()
	{
		
	}
	
	protected void setupModelAndPresenter()
	{
		this.model = new DataConnectionViewModel();
		this.presenter = new DataConnectionViewPresenter(this, (DataConnectionViewModel)model);
		

		
		
	}
	
	protected final void setupEditingContainer()
	{
		editMaster = new Composite(editContainer, SWT.NONE);
		editMaster.setLayout(viewHelper.getViewLayout(2));
		viewHelper.setViewLayoutData(editMaster, true, true);
		/*
		editDetail = new Composite(editContainer, SWT.NONE);
		editDetail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1 ));
		editDetail.setLayout(new FillLayout());
		*/
		lblError = new CLabel(editMaster, SWT.MULTI);
		viewHelper.setViewLayoutData(lblError, 2);

		onSetupEditingContainer();
	}
	
	protected void onSetupEditingContainer()
	{
		/* to do, add a multi column comboviewer from nebula to show the existing connections */
		lblConnections = this.viewHelper.getEditLabel(editMaster, "Connections");
		list = new TableComboViewer(editMaster, SWT.READ_ONLY | SWT.BORDER);
		list.setContentProvider(ArrayContentProvider.getInstance());
		list.setLabelProvider(new DataConnectionLabelProvider());
		//list.getTableCombo().defineColumns(2);
		list.getTableCombo().setTableWidthPercentage(100);
		list.getTableCombo().setShowTableHeader(true);
		list.getTableCombo().defineColumns(new String[] { "Vendor", "Hostname", "User", "Password", "Port"});
		list.getTableCombo().setDisplayColumnIndex(1);
		list.setInput(this.model.getItems());
		list.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = list.getStructuredSelection();
				if(selection != null)
				{
					DataConnection connection = (DataConnection)selection.getFirstElement();
					if(connection != null)
					{
						presenter.loadModel(connection);
					}
				}
				
			}
		});
		
		lblVendorName = viewHelper.getEditLabel(editMaster, "Vendor");
		cboVendorName = new ComboViewer(editMaster);
		cboVendorName.setContentProvider(ArrayContentProvider.getInstance());
		cboVendorName.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element)
			{
				ListDetail item = (ListDetail)element;
				return item.getLabel();
			}
		});
		cboVendorName.setInput(((DataConnectionViewModel)this.model).getVendorNameLookup());
		cboVendorName.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				processVendorNameSelectionChange();
			}
		});
		
		
		lblServerName = viewHelper.getEditLabel(editMaster, "Hostname");
		txtServerName = viewHelper.getTextEditor(editMaster);
		lblUserName = viewHelper.getEditLabel(editMaster, "User Name");
		txtUserName = viewHelper.getTextEditor(editMaster);
		lblPassword = viewHelper.getEditLabel(editMaster, "Password");
		txtPassword = viewHelper.getTextEditor(editMaster);
		lblPort = viewHelper.getEditLabel(editMaster, "Port");
		spPort = new Spinner(editMaster, SWT.NONE);
		spPort.setMaximum(65535);
		spPort.setMinimum(1);
		spPort.setSelection(1);
		spPort.setIncrement(1);
		/* optional based on vendor type */
		lblSid = viewHelper.getEditLabel(editMaster, "SID");
		txtSid = viewHelper.getTextEditor(editMaster);
		
		lblIsExpress = viewHelper.getEditLabel(editMaster, "Is Express");
		chkIsExpress = new Button(editMaster, SWT.CHECK);
		
		this.viewHelper.layoutEditLabel(lblConnections);
		GridDataFactory.fillDefaults().applyTo(list.getControl());
		this.viewHelper.layoutEditLabel(lblVendorName);
		this.viewHelper.layoutComboViewer(cboVendorName);
		this.viewHelper.layoutEditLabel(lblServerName);
		this.viewHelper.layoutEditEditor(txtServerName);
		this.viewHelper.layoutEditLabel(lblUserName);
		this.viewHelper.layoutEditEditor(txtUserName);
		this.viewHelper.layoutEditLabel(lblPassword);
		this.viewHelper.layoutEditEditor(txtPassword);
		this.viewHelper.layoutEditLabel(lblPort);
		this.viewHelper.layoutSpinner(spPort);

		this.viewHelper.layoutEditLabel(lblSid);
		this.viewHelper.layoutEditEditor(txtSid);
		this.viewHelper.layoutEditLabel(lblIsExpress);
		this.viewHelper.layoutEditEditor(chkIsExpress);
		
		Button btnCrap = new Button(editMaster, SWT.NONE);
		btnCrap.setText("Save");
		btnCrap.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.saveModel();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		GridDataFactory.fillDefaults().applyTo(btnCrap);
	}
	
	protected final void initDataBindings()
	{
		ctx.dispose();
		contentProvider = new ObservableListContentProvider();
        knownElements = contentProvider.getKnownElements();
        onInitDataBindings();
	}
	
	protected void onInitDataBindings()
	{
		  
		  /* binding for the edit screen on name field */
        IObservableValue serverNameTargetObservable = WidgetProperties.text(SWT.Modify).observe(this.txtServerName);
        IObservableValue serverNameModelObservable = BeanProperties.value("serverName").observeDetail(value);
		
        IObservableValue vendorNameTargetObservable = ViewerProperties.singleSelection().observe(cboVendorName);
        IObservableValue vendorNameModelObservable = BeanProperties.value("vendorNameLookup").observeDetail(value);
        
        IObservableValue isExpressTargetObservable = WidgetProperties.selection().observe(this.chkIsExpress);
        IObservableValue isExpressModelObservable = BeanProperties.value("isExpress").observeDetail(value);

        IObservableValue userNameTargetObservable = WidgetProperties.text(SWT.Modify).observe(this.txtUserName);
        IObservableValue userNameModelObservable = BeanProperties.value("userName").observeDetail(value);
        
        IObservableValue passwordTargetObservable = WidgetProperties.text(SWT.Modify).observe(this.txtPassword);
        IObservableValue passwordModelObservable = BeanProperties.value("password").observeDetail(value);
        
        
        //IObservableValue portTargetObservable = ViewerProperties.singleSelection().observe(spPort);  // WidgetProperties.text(SWT.Modify).observe(this.spPort.get);
        IObservableValue portTargetObservable = WidgetProperties.selection().observe(spPort);
        IObservableValue portModelObservable = BeanProperties.value("port").observeDetail(value);
        
        
        sidTargetObservable = WidgetProperties.text(SWT.Modify).observe(this.txtSid);
        sidModelObservable = BeanProperties.value("sid").observeDetail(value);
        
        IValidator serverNameValidator = this.viewHelper.getRequiredStringValidator("Host Name must be entered");
        IValidator userNameValidator = this.viewHelper.getRequiredStringValidator("User name must be entered");
        IValidator passwordValidator = this.viewHelper.getRequiredStringValidator("Password must be entered");
        IValidator vendorNameValidator = this.viewHelper.getRequiredStringValidator("Vendor must be entered");
        IValidator portValidator = this.viewHelper.getRequiredStringValidator("Port must be entered");
        
        Binding serverNameBinding = ctx.bindValue(serverNameTargetObservable, serverNameModelObservable, 
        		new UpdateValueStrategy().setAfterConvertValidator(serverNameValidator), null);
        
        Binding vendorNameBinding = ctx.bindValue(vendorNameTargetObservable, vendorNameModelObservable, 
        		new UpdateValueStrategy().setAfterConvertValidator(vendorNameValidator), null);
        
        Binding userNameBinding = ctx.bindValue(userNameTargetObservable, userNameModelObservable, 
        		new UpdateValueStrategy().setAfterConvertValidator(userNameValidator), null);
        
        Binding passwordBinding = ctx.bindValue(passwordTargetObservable, passwordModelObservable, 
        		new UpdateValueStrategy().setAfterConvertValidator(passwordValidator), null);
        
        IConverter portConverter = IConverter.create(Integer.class, Integer.class, x -> (x == null) ? 0 : x);
       // new UpdateValueStrategy().setAfterConvertValidator(portValidator);
		Binding portBinding = ctx.bindValue(portTargetObservable, portModelObservable, 
        		//UpdateValueStrategy.create(portConverter).setAfterConvertValidator(portValidator),
				new UpdateValueStrategy().setAfterConvertValidator(portValidator),
        		UpdateValueStrategy.create(portConverter));
       	
       
        sidBinding = ctx.bindValue(sidTargetObservable, sidModelObservable);
        
        Binding isExpressBinding = ctx.bindValue(isExpressTargetObservable, isExpressModelObservable);
        
        ControlDecorationSupport serverNameDecorator = ControlDecorationSupport.create(serverNameBinding, SWT.TOP | SWT.LEFT);
        ControlDecorationSupport vendorNameDecorator = ControlDecorationSupport.create(vendorNameBinding, SWT.TOP | SWT.LEFT);
        ControlDecorationSupport userNameDecorator = ControlDecorationSupport.create(userNameBinding, SWT.TOP | SWT.LEFT);
        ControlDecorationSupport passwordDecorator = ControlDecorationSupport.create(passwordBinding, SWT.TOP | SWT.LEFT);
        ControlDecorationSupport portDecorator = ControlDecorationSupport.create(portBinding, SWT.TOP | SWT.LEFT);
        
        
        final IObservableValue errorObservable = WidgetProperties.text().observe(lblError);
        Binding allValidationBinding = ctx.bindValue(errorObservable, new AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null);
        IObservableList bindings = ctx.getValidationStatusProviders();
	}
	
	private void initOracleDataBindings(Boolean enableValidation)
	{
		ctx.removeBinding(sidBinding);
		if(enableValidation)
		{
			IValidator sidValidator = this.viewHelper.getRequiredStringValidator("sid must be entered");
			sidBinding.dispose();
			sidBinding = null;
			sidBinding = ctx.bindValue(sidTargetObservable, sidModelObservable, new UpdateValueStrategy().setAfterConvertValidator(sidValidator), null);
			sidNameDecorator = ControlDecorationSupport.create(sidBinding, SWT.TOP | SWT.LEFT);
		}
		else
		{
			sidBinding = ctx.bindValue(sidTargetObservable, sidModelObservable);
			if(sidNameDecorator != null)
			{
				sidNameDecorator.dispose();
			}
		}
	}
	

	protected void setupListColumns() {

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
		value.setValue(model.getCurrentItem());
		processVendorNameSelectionChange();
	}

	@Override
	public void afterAdd() {
		value.setValue(this.model.getCurrentItem());
		list.setInput(this.model.getItems());
		list.refresh();
		this.cboVendorName.getCombo().setFocus();
	}

	@Override
	public void afterSelection() {
	}
	

	private void processVendorNameSelectionChange()
	{
		if (model.getCurrentItem() == null)
		{
			return;
		}
		String key = null;
		IStructuredSelection selected = (IStructuredSelection) cboVendorName.getSelection();
		if(selected != null)
		{
			ListDetail item = (ListDetail)selected.getFirstElement();
			if(item != null)
			{
				key = item.getKey();
			}
		}
		if(ApplicationData.CONNECTION_VENDOR_NAME_MSSQL.equalsIgnoreCase(key))
		{
			this.model.getCurrentItem().setPort(DataConnection.PORT_SQLSERVER);
			this.chkIsExpress.setEnabled(true);
			this.txtSid.setEnabled(false);
			this.initOracleDataBindings(false);
		}
		else if(ApplicationData.CONNECTION_VENDOR_NAME_MYSQL.equalsIgnoreCase(key))
		{
			this.model.getCurrentItem().setPort(DataConnection.PORT_MYSQL);
			this.chkIsExpress.setEnabled(false);
			this.txtSid.setEnabled(false);
			this.initOracleDataBindings(false);
		}
		else if(ApplicationData.CONNECTION_VENDOR_NAME_ORACLE.equalsIgnoreCase(key))
		{
			this.model.getCurrentItem().setPort(DataConnection.PORT_ORACLE);
			this.chkIsExpress.setEnabled(false);
			this.txtSid.setEnabled(true);
			this.initOracleDataBindings(true);
		}
	}

	private static class DataConnectionLabelProvider extends LabelProvider implements ITableLabelProvider {
		/**
		 * We return null, because we don't support images yet.
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
		 */
		public Image getColumnImage (Object element, int columnIndex) {
			return null;
		}

		/**
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
		 */
		public String getColumnText (Object element, int columnIndex) {
			
			DataConnection item = (DataConnection)element;
			if(item == null){return "";}
			
			switch (columnIndex) {
				case 0: return item.getVendorNameLookup().getLabel();
				case 1: return item.getServerName();
				case 2: return item.getUserName();
				case 3: return item.getPassword();
				case 4: return item.getPort().toString();
			}
			return "";
		}
	}
	
	private static class DataConnectionLabelProviderA extends LabelProvider {
		@Override
		public String getText(Object element) {
			return "howdy";
		}
		
		@Override
		public Image getImage(Object element) {
		
			return null;
		}
	}
}
