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
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.entity.ListDetail;
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
	
	protected DataConnectionInlineView(Composite parent, int style) {
		super(parent, style);
		this.init();
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
		
		/* temporary 
		this.model.setCurrentItem(new DataConnection());
		this.value.setValue(this.model.getCurrentItem());
		*/
    	
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
		lblError = new CLabel(editMaster, SWT.NONE);
		viewHelper.setViewLayoutData(lblError, 2);

		onSetupEditingContainer();
	}
	
	protected void onSetupEditingContainer()
	{
		/* to do, add a multi column comboviewer from nebula to show the existing connections */
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
        
        
        IObservableValue sidTargetObservable = WidgetProperties.text(SWT.Modify).observe(this.txtSid);
        IObservableValue sidModelObservable = BeanProperties.value("sid").observeDetail(value);
        
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
        
        IConverter portConverter = IConverter.create(Integer.class, Integer.class, x -> x == null ? 0 : x);
        new UpdateValueStrategy().setAfterConvertValidator(portValidator);
		Binding portBinding = ctx.bindValue(portTargetObservable, portModelObservable, 
        		UpdateValueStrategy.create(portConverter).setAfterConvertValidator(portValidator),
        		UpdateValueStrategy.create(portConverter));
       	
       
        Binding sidBinding = ctx.bindValue(sidTargetObservable, sidModelObservable);
        
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
	}

	@Override
	public void afterAdd() {
	}

	@Override
	public void afterSelection() {
	}
	



}
