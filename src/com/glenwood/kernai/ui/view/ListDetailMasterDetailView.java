package com.glenwood.kernai.ui.view;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.ui.abstraction.IEntityMasterDetailView;
import com.glenwood.kernai.ui.presenter.ListDetailPresenter;
import com.glenwood.kernai.ui.viewmodel.ListDetailViewModel;

public class ListDetailMasterDetailView extends Composite implements IEntityMasterDetailView {
	
	private ListDetailPresenter presenter;
	private ListDetailViewModel model;
	
	private TableViewer listViewer;
	private Table listTable;
	private WritableList<ListDetail> input;
	
	public ListDetailMasterDetailView(Composite parent, int style, ListHeader listHeader)
	{
		super(parent, style);
		this.model = new ListDetailViewModel(listHeader);
		this.presenter = new ListDetailPresenter(this, model);
		Composite listContainer = new Composite(this, SWT.NONE);
		
		
		listViewer = new TableViewer(listContainer, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		listTable = listViewer.getTable();
		listTable.setHeaderVisible(true);
		listTable.setLinesVisible(true);
		TableViewerColumn keyColumn = new TableViewerColumn(listViewer, SWT.LEFT);
		keyColumn.getColumn().setText("Key");
		keyColumn.getColumn().setResizable(false);
		keyColumn.getColumn().setMoveable(false);
		TableViewerColumn labelColumn = new TableViewerColumn(listViewer, SWT.LEFT);
		labelColumn.getColumn().setText("Label");
		labelColumn.getColumn().setResizable(false);
		labelColumn.getColumn().setMoveable(false);
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(keyColumn.getColumn(), new ColumnWeightData(50));
		tableLayout.setColumnData(labelColumn.getColumn(), new ColumnWeightData(50));
		
		this.setLayout(new FillLayout());
		
	}
	
	private  ListDetailMasterDetailView(Composite parent, int style) {
		this(parent, style, null);

	}

}
