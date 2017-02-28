package com.glenwood.kernai.ui.view;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListModalView;
import com.glenwood.kernai.ui.abstraction.MasterDetailListViewEngine;
import com.glenwood.kernai.ui.presenter.ListDetailViewPresenter;
import com.glenwood.kernai.ui.view.helpers.ListSorterHelper;
import com.glenwood.kernai.ui.viewmodel.ListDetailViewModel;

public final class ListDetailMasterDetailView extends BaseEntityMasterDetailListModalView<ListDetail, ListHeader> {
	
	@SuppressWarnings("unused")
	private  ListDetailMasterDetailView(Composite parent, int style) {
		this(parent, style, null);
	}
	
	
	public ListDetailMasterDetailView(Composite parent, int style, ListHeader listHeader)
	{
		super(parent, SWT.BORDER, listHeader, new MasterDetailListViewEngine());
	}
	
	@Override
	protected void setupModelAndPresenter(ListHeader parent)
	{
		this.model = new ListDetailViewModel(parent);
		this.presenter = new ListDetailViewPresenter(this, (ListDetailViewModel) model);
	}


	@Override
	protected void setupListColumns() {
		super.setupListColumns();
		this.listViewer.setComparator(new ViewerComparator());
		TableViewerColumn keyColumn = this.viewHelper.getListColumn(listViewer, "Key");
		keyColumn.getColumn().addSelectionListener(this.getSelectionAdapter(keyColumn.getColumn(), 0));

		TableViewerColumn labelColumn = this.viewHelper.getListColumn(listViewer, "Label");
		labelColumn.getColumn().addSelectionListener(this.getSelectionAdapter(labelColumn.getColumn(), 1));
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(keyColumn.getColumn(), new ColumnWeightData(50));
		tableLayout.setColumnData(labelColumn.getColumn(), new ColumnWeightData(50));
	}

	
	@Override
	public void showAddEdit(Boolean adding)
	{
		ListDetailModalView modalView = new ListDetailModalView(getShell());
		modalView.setModel((ListDetailViewModel) model);
		if(modalView.open() == Window.OK)
		{
			this.presenter.saveModel();
			if(adding)
			{
				this.input.add(this.model.getCurrentItem());
			}
			else
			{
				this.listViewer.refresh();
			}
		}
	}
	
	@Override
	public void refreshView()
	{
		input = new WritableList(model.getItems(), ListDetail.class);
		listViewer.setInput(input);	
	}
	
	@Override
	protected void initDataBindings()
	{
		super.initDataBindings();
        
        IObservableSet<ListDetail> detailElements = detailContentProvider.getKnownElements();
        final IObservableMap keys = BeanProperties.value(ListDetail.class, "key").observeDetail(detailElements);
        final IObservableMap<String, String> labels = BeanProperties.value(ListDetail.class, "label").observeDetail(detailElements);
        IObservableMap[] detailLabelMaps = {keys, labels};
        ILabelProvider detailLabelProvider = new ObservableMapLabelProvider(detailLabelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
                	ListDetail mc = (ListDetail)element;
                	switch(columnIndex)
                	{
                	case 0:
                		return mc.getKey();
                	case 1:
                		return mc.getLabel();
                	default:
                		return null;
                	}
                }
        };
        listViewer.setLabelProvider(detailLabelProvider);

	}
	
	private class ViewerComparator extends ListSorterHelper
	{

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 == null || e2 == null)
			{
				return 0;
			}
			ListDetail p1 = (ListDetail)e1;
			ListDetail p2 = (ListDetail)e2;
			int rc = 0;
			switch(this.propertyIndex)
			{
			case 0:
				rc = this.compareString(p1.getKey(), p2.getKey());
				break;
			case 1:
				rc = this.compareString(p1.getLabel(), p2.getLabel());
				break;
			default:
				rc = 0;
			}
			
			if (this.direction == DESCENDING)
			{
				rc = -rc;
			}
			return rc;
		}
		
		private int compareString(String s1, String s2)
		{
			if (s1 == null || s2 == null)
			{
				return 0;
			}
			String stringOne = "";
			String stringTwo = "";
			stringOne = s1 == null ? "" : s1;
			stringTwo = s2 == null ? "" : s2;
			return stringOne.compareTo(stringTwo);
		}
	}


}
