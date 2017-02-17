package com.glenwood.kernai.ui.view;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.data.entity.MasterProperty;
import com.glenwood.kernai.ui.abstraction.BaseEntityView;
import com.glenwood.kernai.ui.presenter.MasterPropertyViewPresenter;
import com.glenwood.kernai.ui.viewmodel.MasterPropertyViewModel;

public class MasterPropertyView extends BaseEntityView<MasterProperty> {
	
	Label lblName;
	Text txtName;

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
		TableViewerColumn nameColumn = new TableViewerColumn(listViewer, SWT.LEFT);
		nameColumn.getColumn().setText("Name");
		nameColumn.getColumn().setResizable(false);
		nameColumn.getColumn().setMoveable(false);
//		nameColumn.getColumn().addSelectionListener(this.getSelectionAdapter(nameColumn.getColumn(), 0));
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));
	}
	
	@Override
	protected void setupEditingContainer() {
		super.setupEditingContainer();
		lblName = new Label(editMaster, SWT.NONE);
		lblName.setText("Name");
		txtName = viewHelper.getTextEditor(editMaster);
		viewHelper.layoutEditLabel(lblName);
		viewHelper.layoutEditEditor(txtName);
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
