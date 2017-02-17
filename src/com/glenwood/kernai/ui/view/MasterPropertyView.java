package com.glenwood.kernai.ui.view;

import org.eclipse.swt.widgets.Composite;

import com.glenwood.kernai.data.entity.MasterProperty;
import com.glenwood.kernai.ui.abstraction.BaseEntityView;
import com.glenwood.kernai.ui.presenter.MasterPropertyViewPresenter;
import com.glenwood.kernai.ui.viewmodel.MasterPropertyViewModel;

public class MasterPropertyView extends BaseEntityView<MasterProperty> {

	public MasterPropertyView(Composite parent, int style) {
		super(parent, style);

	}
	
	@Override
	protected void setupModelAndPresenter() {
		this.model = new MasterPropertyViewModel();
		this.presenter = new MasterPropertyViewPresenter(this, this.model);
	}
	
	@Override
	protected void setupListColumns() {
		super.setupListColumns();
	}
	
	@Override
	protected void setupEditingContainer() {
		super.setupEditingContainer();
	}
	
	@Override
	protected void initDataBindings() {
		super.initDataBindings();
	}
	
	@Override
	public void add() {
		super.add();
	}

}
