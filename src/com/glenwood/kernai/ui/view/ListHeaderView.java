package com.glenwood.kernai.ui.view;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.presenter.ListHeaderPresenter;
import com.glenwood.kernai.ui.viewmodel.ListHeaderViewModel;

public class ListHeaderView extends Composite implements IEntityView {

	private ListHeaderViewModel model;
	private ListHeaderPresenter presenter;
	
	public ListHeaderView(Composite parent, int style) {
		super(parent, style);
		this.model = new ListHeaderViewModel();
		this.setLayout(new FillLayout());
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void add() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

}
