/* a connection view that is designed to be placed inline within another view
 * has a drop down list with all existing connections
 * try the multi column combo from nebula
 * a drop down for vendor
 * the view should dynamically show items that are specific to a vendor
 * eg: is express checkbox for mssql, sid for oracle 
 */

package com.glenwood.kernai.ui.view;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.widgets.Composite;

import com.glenwood.kernai.data.abstractions.BaseEntity;
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

	
	protected DataConnectionInlineView(Composite parent, int style) {
		super(parent, style);
		this.init();
	}
	
	private final void init()
	{
		this.viewHelper = new EntityViewHelper();
		this.setupModelAndPresenter();
		this.onInit();
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

		onSetupEditingContainer();
	}
	
	protected void onSetupEditingContainer()
	{
		
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
