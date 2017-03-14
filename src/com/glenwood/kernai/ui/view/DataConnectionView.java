package com.glenwood.kernai.ui.view;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.widgets.Composite;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.ui.abstraction.IViewModel;
import com.glenwood.kernai.ui.presenter.DataConnectionViewPresenter;
import com.glenwood.kernai.ui.view.helpers.EntityViewHelper;

public abstract class DataConnectionView<T extends BaseEntity> extends Composite  {

	
	protected DataConnectionViewPresenter<T> presenter;
	protected IViewModel<T> model;
	protected DataBindingContext ctx;
	
	protected EntityViewHelper viewHelper;

	
	protected DataConnectionView(Composite parent, int style) {
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
