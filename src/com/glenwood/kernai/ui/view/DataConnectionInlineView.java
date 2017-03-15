/* a connection view that is designed to be placed inline within another view
 * has a drop down list with all existing connections
 * try the multi column combo from nebula
 * a drop down for vendor
 * the view should dynamically show items that are specific to a vendor
 * eg: is express checkbox for mssql, sid for oracle 
 */

package com.glenwood.kernai.ui.view;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.ui.abstraction.IViewModel;
import com.glenwood.kernai.ui.presenter.DataConnectionViewPresenter;
import com.glenwood.kernai.ui.view.helpers.EntityViewHelper;
import com.glenwood.kernai.ui.viewmodel.DataConnectionViewModel;



public class DataConnectionInlineView extends Composite  {

	
	protected DataConnectionViewPresenter presenter;
	protected IViewModel<DataConnection> model;
	protected DataBindingContext ctx;
	
	protected EntityViewHelper viewHelper;
	private Composite editContainer;
	private Composite editMaster;
	private Composite editDetail;
	
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
		//value = new WritableValue<T>();
		//presenter.loaditems
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
		
		lblServerName = viewHelper.getEditLabel(editMaster, "Server Name");
		txtServerName = viewHelper.getTextEditor(editMaster);
		lblUserName = viewHelper.getEditLabel(editMaster, "User Name");
		txtUserName = viewHelper.getTextEditor(editMaster);
		lblPassword = viewHelper.getEditLabel(editMaster, "Password");
		txtPassword = viewHelper.getTextEditor(editMaster);
		lblPort = viewHelper.getEditLabel(editMaster, "Port");
		spPort = new Spinner(editMaster, SWT.NONE);
		
		this.viewHelper.layoutEditLabel(lblServerName);
		this.viewHelper.layoutEditEditor(txtServerName);
		this.viewHelper.layoutEditLabel(lblUserName);
		this.viewHelper.layoutEditEditor(txtUserName);
		this.viewHelper.layoutEditLabel(lblPassword);
		this.viewHelper.layoutEditEditor(txtPassword);
		this.viewHelper.layoutEditLabel(lblPort);
		this.viewHelper.layoutSpinner(spPort);

	}
	
	protected final void initDataBindings()
	{
		ctx.dispose();
        onInitDataBindings();
	}
	
	protected void onInitDataBindings()
	{
		
	}
	
	protected void setupListColumns() {

	}
	



}
