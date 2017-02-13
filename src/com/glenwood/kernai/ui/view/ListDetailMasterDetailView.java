package com.glenwood.kernai.ui.view;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailView;
import com.glenwood.kernai.ui.presenter.ListDetailViewPresenter;
import com.glenwood.kernai.ui.viewmodel.ListDetailViewModel;

public class ListDetailMasterDetailView extends BaseEntityMasterDetailView<ListDetail> {
	
	private ListDetailViewPresenter presenter;
	private ListDetailViewModel model;

	private WritableList<ListDetail> input;

	
	
	public ListDetailViewPresenter getPresenter() {
		return presenter;
	}

	
	public ListDetailMasterDetailView(Composite parent, int style, ListHeader listHeader)
	{
		super(parent, SWT.BORDER);
		this.model = new ListDetailViewModel(listHeader);
		this.presenter = new ListDetailViewPresenter(this, model);
		this.createActions();
		
		Composite headerContainer = new Composite(this, SWT.NONE);
		GridLayout headerLayout = new GridLayout(1, false);
		headerLayout.marginHeight = 0;
		headerLayout.marginWidth = 0;
		headerContainer.setLayout(headerLayout);
		
		//Label headerLabel = new Label(headerContainer, SWT.NONE);
		ToolBar actionsBar = new ToolBar(headerContainer, SWT.NONE);
		ToolBarManager toolBarManager = new ToolBarManager(actionsBar);
		ActionContributionItem newAction = new ActionContributionItem(this.actionMap.get(NEW_ACTION_KEY));
//		newAction.getAction().setAccelerator(SWT.ALT | 'j');
		
		toolBarManager.add(newAction);
		newAction = new ActionContributionItem(this.actionMap.get(DELETE_ACTION_KEY));
		toolBarManager.add(newAction);
		newAction = new ActionContributionItem(this.actionMap.get(EDIT_ACTION_KEY));
		toolBarManager.add(newAction);
		toolBarManager.update(true);
		

		ToolBar toolbar = toolBarManager.getControl();
		toolbar.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("farook");
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("farook");				
			}
		});
		
		if (toolbar != null )
		{
			for(ToolItem toolItem : toolbar.getItems())
			{
				ActionContributionItem actionItem = (ActionContributionItem)toolItem.getData();
				toolItemMap.put(actionItem.getAction().getActionDefinitionId(), toolItem);
			}
		}
		
	//	headerLabel.setText("List Items");
		actionsBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1 ));
		//headerLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1 ));
		
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
				ListDetail selectedItem = getSelection();
				if(selectedItem != null)
				{
					presenter.editModel(selectedItem);
				}
				
			}
		});
		
		this.ctx = new DataBindingContext();
		initDataBindings();
		this.presenter.loadItems();
		GridLayout mainLayout = new GridLayout(1, false);
		mainLayout.verticalSpacing = SWT.FILL;
		mainLayout.horizontalSpacing = SWT.FILL;
		mainLayout.marginHeight = 0;
		mainLayout.marginWidth = 0;
		headerContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1 ));
		listContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1 ));
		this.setLayout(mainLayout);
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("hi");
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private ListDetail getSelection()
	{
		ListDetail item = null;
		IStructuredSelection selection = (IStructuredSelection)listViewer.getSelection();
		if(selection != null)
		{
			item = (ListDetail)selection.getFirstElement();
		}
		return item;
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
		 newAction.setAccelerator(SWT.ALT | 'j');
		 newAction.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_ADD_SMALL));
		 newAction.setActionDefinitionId(ID_PREFIX + NEW_ACTION_KEY);
		 this.actionMap.put(NEW_ACTION_KEY, newAction);
		 
		 IAction deleteAction = new Action(){
			 @Override
			 public void run(){
				 ListDetail selectedItem = getSelection();
				 if (selectedItem != null)
				 {
					 if(ApplicationData.instance().confirmDelete(getShell()) == true)
					 {
						 presenter.deleteModel(selectedItem);
					 }
				 }
			 }
		 };
		 deleteAction.setEnabled(false);
		 deleteAction.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_CANCEL_SMALL));
		 deleteAction.setDisabledImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_CANCEL_DISABLED_SMALL));
		 deleteAction.setActionDefinitionId(ID_PREFIX + DELETE_ACTION_KEY);
		 this.actionMap.put(DELETE_ACTION_KEY, deleteAction);
		 
		 IAction editAction = new Action(){
			 @Override
			 public void run(){
				 ListDetail selectedItem = getSelection();
				 if (selectedItem != null)
				 {
					 presenter.editModel(selectedItem);
				 }
			 }
		 };
		 editAction.setEnabled(false);
		 editAction.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_EDIT_SMALL));
		 editAction.setDisabledImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_EDIT_DISABLED_SMALL));
		 editAction.setActionDefinitionId(ID_PREFIX + EDIT_ACTION_KEY);
		 this.actionMap.put(EDIT_ACTION_KEY, editAction);
	}
	
	
	
	private  ListDetailMasterDetailView(Composite parent, int style) {
		this(parent, style, null);

	}
	
	public void showAddEdit(Boolean adding)
	{
		ListDetailModalView modalView = new ListDetailModalView(getShell());
		modalView.setModel(model);
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
        
        /* set the enabled of the toolbar items */
        IObservableValue listViewerSelectionForDelete = ViewersObservables.observeSingleSelection(listViewer);
        IObservableValue listViewerSelectionForEdit = ViewersObservables.observeSingleSelection(listViewer);
        IObservableValue<ToolItem> deleteItemTarget = WidgetProperties.enabled().observe(this.toolItemMap.get(ID_PREFIX + DELETE_ACTION_KEY));
        IObservableValue<ToolItem> editItemTarget = WidgetProperties.enabled().observe(this.toolItemMap.get(ID_PREFIX + EDIT_ACTION_KEY));
        UpdateValueStrategy convertSelectedToBoolean = new UpdateValueStrategy(){
        	@Override
        	protected IStatus doSet(IObservableValue observableValue, Object value) 
        	{
        		return super.doSet(observableValue, value == null ? Boolean.FALSE : Boolean.TRUE);
        	};
        };
        Binding deleteBinding = ctx.bindValue(deleteItemTarget, listViewerSelectionForDelete,  new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), convertSelectedToBoolean);
        Binding editBinding = ctx.bindValue(editItemTarget, listViewerSelectionForEdit, new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), convertSelectedToBoolean);
       
        //listener required to enable/disable action associated with toolItem, can't databind an action
        editBinding.getTarget().addChangeListener(new IChangeListener() {
			
			@Override
			public void handleChange(ChangeEvent event) {
				IAction editAction = actionMap.get(EDIT_ACTION_KEY);
				ToolItem editToolItem = toolItemMap.get(ID_PREFIX + EDIT_ACTION_KEY);
				editAction.setEnabled(editToolItem.getEnabled());
			}
		});
        
        deleteBinding.getTarget().addChangeListener(new IChangeListener() {
			
			@Override
			public void handleChange(ChangeEvent event) {
				IAction deleteAction = actionMap.get(DELETE_ACTION_KEY);
				ToolItem deleteToolItem = toolItemMap.get(ID_PREFIX + DELETE_ACTION_KEY);
				deleteAction.setEnabled(deleteToolItem.getEnabled());				
			}
		});
        
     // ISideEffect sideEffect =ISideEffect.create( () -> { editAction.setEnabled(editItemTarget.getValue().isEnabled());});
      
      //ISideEffectFactory sfactory = WidgetSideEffects.createFactory(this.toolItemMap.get(ID_PREFIX + EDIT_ACTION_KEY));
      //sfactory.create(this.toolItemMap.get(ID_PREFIX + EDIT_ACTION_KEY)::getEnabled, this.actionMap.get(EDIT_ACTION_KEY)::setEnabled);
      //ISideEffect sideEffect = ISideEffect.create(() -> {return deleteItemTarget.getValue()}, this.actionMap.get(DELETE_ACTION_KEY)::setEnabled);
     // sfactory.create(this.toolItemMap.get(ID_PREFIX + EDIT_ACTION_KEY)::toString, dummyLabel::setText);
   

	}

}
