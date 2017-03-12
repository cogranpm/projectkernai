package com.glenwood.kernai.ui.view;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewerColumn;
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


	@Override
	protected void setupListColumns() {
		TableViewerColumn serverNameColumn = this.viewHelper.getListColumn(listViewer, "Server Name");
		TableViewerColumn userNameColumn = this.viewHelper.getListColumn(listViewer, "User");
		TableViewerColumn passwordColumn = this.viewHelper.getListColumn(listViewer, "Password");
		TableViewerColumn isExpressColumn = this.viewHelper.getListColumn(listViewer, "Is Express");
		TableViewerColumn portColumn = this.viewHelper.getListColumn(listViewer, "Port");
		
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(serverNameColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(userNameColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(passwordColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(isExpressColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(portColumn.getColumn(), new ColumnWeightData(100));
	}
	
	@Override
	protected void onSetupEditingContainer() {
	
	}
	

}
