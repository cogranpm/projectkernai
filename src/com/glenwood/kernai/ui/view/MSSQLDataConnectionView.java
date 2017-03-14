package com.glenwood.kernai.ui.view;

import org.eclipse.swt.widgets.Composite;

import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.ui.presenter.DataConnectionViewPresenter;
import com.glenwood.kernai.ui.viewmodel.DataConnectionViewModel;

public class MSSQLDataConnectionView extends DataConnectionView<DataConnection> {

	public MSSQLDataConnectionView(Composite parent, int style) {
		super(parent, style);
		
	}
	

	@Override
	protected void setupModelAndPresenter() {
		this.model = new DataConnectionViewModel();
		this.presenter = new DataConnectionViewPresenter<DataConnection>(this, this.model);
	}
	

}
