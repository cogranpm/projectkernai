package com.glenwood.kernai.ui.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.glenwood.kernai.data.entity.MSSQLDataConnection;
import com.glenwood.kernai.ui.abstraction.BaseEntityModalView;
import com.glenwood.kernai.ui.presenter.MSSQLDataConnectionViewPresenter;
import com.glenwood.kernai.ui.viewmodel.MSSQLDataConnectionViewModel;

public class MSSQLDataConnectionView extends BaseEntityModalView<MSSQLDataConnection> {

	public MSSQLDataConnectionView(Shell parentShell) {
		super(parentShell);
		
	}
	
	@Override
	protected void setupModelAndPresenter() {
		this.model = new MSSQLDataConnectionViewModel();
		this.presenter = new MSSQLDataConnectionViewPresenter(this, this.model);
	}


	

}
