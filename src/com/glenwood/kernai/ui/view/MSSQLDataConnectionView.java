package com.glenwood.kernai.ui.view;

import org.eclipse.swt.widgets.Composite;

import com.glenwood.kernai.data.entity.MSSQLDataConnection;
import com.glenwood.kernai.ui.presenter.DataConnectionViewPresenter;
import com.glenwood.kernai.ui.viewmodel.MSSQLDataConnectionViewModel;

public class MSSQLDataConnectionView extends DataConnectionView<MSSQLDataConnection> {

	public MSSQLDataConnectionView(Composite parent, int style) {
		super(parent, style);
		
	}
	

	@Override
	protected void setupModelAndPresenter() {
		this.model = new MSSQLDataConnectionViewModel();
		this.presenter = new DataConnectionViewPresenter<MSSQLDataConnection>(this, this.model);
	}
	

}
