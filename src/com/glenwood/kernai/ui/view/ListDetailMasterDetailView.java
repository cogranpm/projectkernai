package com.glenwood.kernai.ui.view;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.IEntityMasterDetailView;
import com.glenwood.kernai.ui.presenter.ListDetailPresenter;
import com.glenwood.kernai.ui.viewmodel.ListDetailViewModel;

public class ListDetailMasterDetailView extends Composite implements IEntityMasterDetailView {
	
	private ListDetailPresenter presenter;
	Map<String, IAction> actionMap = new HashMap<String, IAction>();
	
	private static final String NEW_ACTION_KEY = "new";
	
	
	public ListDetailPresenter getPresenter() {
		return presenter;
	}

	private ListDetailViewModel model;
	
	private TableViewer listViewer;
	private Table listTable;
	private WritableList<ListDetail> input;
	
	public ListDetailMasterDetailView(Composite parent, int style, ListHeader listHeader)
	{
		super(parent, SWT.BORDER);
		this.model = new ListDetailViewModel(listHeader);
		this.presenter = new ListDetailPresenter(this, model);
		this.createActions();
		
		Composite headerContainer = new Composite(this, SWT.NONE);
		headerContainer.setLayout(new GridLayout(1, false));
		Label headerLabel = new Label(headerContainer, SWT.NONE);
		ToolBar actionsBar = new ToolBar(headerContainer, SWT.NONE);
		ToolBarManager toolBarManager = new ToolBarManager(actionsBar);
		ActionContributionItem newAction = new ActionContributionItem(this.actionMap.get(NEW_ACTION_KEY));
		toolBarManager.add(newAction);
		toolBarManager.update(true);
		
		headerLabel.setText("List Items");
		actionsBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1 ));
		headerLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1 ));
		
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
		
		listViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				System.out.println("Double clicked me");
				
			}
		});
		
		initDataBindings();
		this.presenter.loadItems();
		GridLayout mainLayout = new GridLayout(1, false);
		mainLayout.verticalSpacing = SWT.FILL;
		mainLayout.horizontalSpacing = SWT.FILL;
		headerContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1 ));
		listContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1 ));
		this.setLayout(mainLayout);
		
	}
	
	private void createActions()
	{
		 IAction newAction = new Action() {
			@Override 
			public void run() {
				presenter.addModel();
			}
		 };
		 newAction.setEnabled(true);
		 newAction.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_ADD_SMALL));
		 this.actionMap.put(NEW_ACTION_KEY, newAction);
	}
	
	
	
	private  ListDetailMasterDetailView(Composite parent, int style) {
		this(parent, style, null);

	}
	
	public void showAddEdit()
	{
		ListDetailModalView modalView = new ListDetailModalView(getShell());
		modalView.setModel(model);
		if(modalView.open() == Window.OK)
		{
			this.presenter.saveModel();
			this.input.add(this.model.getCurrentItem());
			//save it, update the list binding
			//System.out.println(model.getCurrentItem().getKey() + " " + model.getCurrentItem().getLabel());
			//presenter.
		}
	}
	
	public void refreshView()
	{
		input = new WritableList(model.getItems(), ListDetail.class);
		listViewer.setInput(input);	
	}
	
	private void initDataBindings()
	{
        ObservableListContentProvider detailContentProvider = new ObservableListContentProvider();
        listViewer.setContentProvider(detailContentProvider);
        
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

}
